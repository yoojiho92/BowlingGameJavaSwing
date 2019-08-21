package GUI;

import Element.Pin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LinePanel extends JPanel {
    private static boolean DEBUG = false;
    private static int PANEL_WITHD = 300;
    private static int PANEL_HEIGHT = 220;

    private static int RANGE = 26;
    private static int PIN_WIDTH = 10;
    private static int PIN_HEIGHT = 20;

    private static int BALL_WIDTH = 70;
    private static int BALL_HEIGHT = 70;

    private JPanel jPanel = this;
    private Graphics graphics;
    private ArrayList<Pin> pinList;

    private boolean ballFlag = true;
    private int ballX = 15;
    private int ballY = 75;

    private MoveThread moveThread;
    private ArrowPanel arrowPanel;
    private ScorePanel scorePanel1;
    private ScorePanel scorePanel2;

    public LinePanel(ArrowPanel arrowPanel, ScorePanel scorePanel, ScorePanel scorePanel2){
        setSize(PANEL_WITHD, PANEL_HEIGHT);
        graphics = getGraphics();
        pinList = new ArrayList<Pin>();
        initPinList();
        moveThread = new MoveThread();
        moveThread.start();

        this.arrowPanel = arrowPanel;
        this.scorePanel1 = scorePanel;
        this.scorePanel2 = scorePanel2;
    }

    public void initPinList(){
        pinList.clear();
        int pinNum = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j <= i; j++){
                Pin pin = new Pin();
                int x = (PANEL_WITHD-(RANGE*4)) + i * RANGE;
                int y = 0;
                if((i+1) % 2 == 1){
                    int half = (i+1)/2;
                    y = PANEL_HEIGHT/2 + ( j * RANGE ) - ( half * RANGE );
                }else if((i+1) % 2 == 0){
                    int half = (i+1)/2;
                    y = PANEL_HEIGHT/2 + ( j * RANGE ) - ( half * RANGE ) + RANGE/2;
                }

                pin.setX(x);
                pin.setY(y);
                pin.setPinNum(pinNum);
                if(DEBUG){
                    System.out.println("Width : " + this.getWidth() + "Height : " + this.getHeight());
                    System.out.println("i : "+ i + "  j :" + j + "x_point : " + x + " y_point : " + y);
                }

                pin.setHeight(PIN_HEIGHT);
                pin.setWidth(PIN_WIDTH);
                pinList.add(pin);
                pinNum += 1;
            }
        }
    }

    public void initBall(int x, int y){
        ballX = x;
        ballY = y;
        setBallCenter();
    }

    public void drawPin(Graphics g){
        for(int i = 0; i < pinList.size(); i ++){
            Pin pin = pinList.get(i);
            if(DEBUG){
                g.drawLine(pin.getX(),pin.getY(),pin.getX()+pin.getWidth(),pin.getY());
                g.drawLine(pin.getX(),pin.getY(),pin.getX(),pin.getY() + pin.getHeight());
                g.drawLine(pin.getX() + pin.getWidth(),pin.getY(),pin.getX() + pin.getWidth(),pin.getY() + pin.getHeight());
                g.drawLine(pin.getX(),pin.getY(),pin.getX() + pin.getWidth(),pin.getY() + pin.getHeight());
            }

            g.fillOval(pin.getX(),pin.getY(),pin.getWidth(),pin.getHeight());
            repaint();
        }
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }

    public void setBallFlag(boolean ballFlag) {
        this.ballFlag = ballFlag;
    }

    public void setBallCenter(){
        ballX -= BALL_HEIGHT ;
        ballY -= BALL_HEIGHT /2;
    }

    public void shootBall(int power, int positionX, int positionY){
        ballFlag = true;
        moveThread.setPower(power);
        this.setBallX(positionX);
        this.setBallY(positionY);
        setBallCenter();
        moveThread.setFlag(true);
    }
    class CrashThread extends Thread{
        private Pin pin;

        @Override
        public void run() {
            super.run();
            int after_Width = pin.getHeight();
            int after_Height = pin.getWidth();
            int after_Y = pin.getY() + (pin.getHeight() - pin.getWidth());

            boolean flagA = false;
            boolean flagB = false;
            boolean flagC = false;
            pin.setState(false);


            while (!(flagA && flagB && flagC)){
                if(pin.getWidth() == after_Width)
                    flagA = true;
                else
                    pin.setWidth(pin.getWidth() + 1);

                if(pin.getHeight() == after_Height)
                    flagB = true;
                else
                    pin.setHeight(pin.getHeight() - 1);

                if(pin.getY() == after_Y)
                    flagC = true;
                else if(pin.getY() < after_Y)
                    pin.setY(pin.getY() + 1);


                try {
                    sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                repaint();
            }
        }

        public void setPin(Pin pin) {
            this.pin = pin;
        }
    }

    class MoveThread extends Thread{
        boolean flag = false;
        boolean initPinFlag = false;
        int power = 1000;
        private int round = 0;
        private int frame = 0;
        private int score = 0;
        private int tempScore = 0;
        @Override
        public void run() {
            super.run();
            while (true){
                tempScore = score;
                score = 0;
                if(initPinFlag){
                    initPinList();
                    initBall(15,75);
                    setBallCenter();
                    repaint();
                    initPinFlag = false;
                }
                while (flag){
                    ballX +=1;
                    power -= 3;

                    for (int i = 0; i < pinList.size(); i++){
                        if (ballX < pinList.get(i).getX() && pinList.get(i).getX() < ballX + BALL_WIDTH){
                            if (ballY < pinList.get(i).getY() && pinList.get(i).getY() < ballY + BALL_HEIGHT){
                                if(DEBUG){
                                    System.out.println("BallX : " + ballX + "\tBallX+Width : " + (ballX + BALL_WIDTH));
                                    System.out.println("BallY : " + ballY + "\tBallYX+Height : " + (ballY + BALL_HEIGHT));
                                    System.out.println("pinX : " + pinList.get(i).getX() + "\tpinY : " + pinList.get(i).getY());
                                }

                                CrashThread crashThread = new CrashThread();
                                crashThread.setPin(pinList.get(i));
                                crashThread.start();
                               /*pinList.remove(i);
                               i--;*/
                               score += 1;
                            }
                        }
                    }
                    repaint();
                    if(power < 0){
                        flag = false;

                        if(round != 10){
                            if(frame == 1){
                                if(arrowPanel.getPlayLabel().getText().equals("computer")){
                                    round += 1;
                                    frame = 0;
                                    tempScore = 0;
                                    initPinFlag = true;
                                    arrowPanel.getRoundLabel().setText((round+1)+" round");
                                    arrowPanel.getPlayLabel().setText("player1");
                                }else if(arrowPanel.getPlayLabel().getText().equals("player1")){
                                    frame = 0;
                                    initPinFlag = true;
                                    arrowPanel.getPlayLabel().setText("computer");
                                }
                            }else {
                                if(score == 10){
                                    if(arrowPanel.getPlayLabel().getText().equals("computer")){
                                        round += 1;
                                        frame = 0;
                                        initPinFlag = true;
                                        arrowPanel.getRoundLabel().setText((round+1)+" round");
                                        arrowPanel.getPlayLabel().setText("player1");
                                    }else if(arrowPanel.getPlayLabel().getText().equals("player1")){
                                        frame = 0;
                                        initPinFlag = true;
                                        arrowPanel.getPlayLabel().setText("computer");
                                    }
                                }else{
                                    frame += 1;
                                }
                            }
                        }else if(round >= 10){
                            arrowPanel.getRoundLabel().setText("10 round");
                        }
                        if(arrowPanel.getPlayLabel().getText().equals("computer")){
                            if(score == 0){
                                scorePanel2.setScore(round,frame,"-");
                            }else{
                                scorePanel2.setScore(round,frame,String.valueOf(score));
                            }

                        }else if(arrowPanel.getPlayLabel().getText().equals("player1")){
                            if(score == 0){
                                scorePanel1.setScore(round,frame,"-");
                            }else{
                                scorePanel1.setScore(round,frame,String.valueOf(score));
                            }
                        }
                    }
                    try {
                        sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                try {
                    sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        public void setFlag(boolean flag){
            this.flag = flag;
        }

        public boolean getFlag(){
            return this.flag;
        }

        public void setPower(int power) {
            this.power = power;
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.drawLine(0,(PANEL_HEIGHT-1),getWidth(),(PANEL_HEIGHT-1));
        g.drawLine(0,(PANEL_HEIGHT-20),getWidth(),(PANEL_HEIGHT-20));
        g.drawLine(0,20,getWidth(),20);
        g.drawLine(0,1,getWidth(),1);
        g.setColor(Color.BLACK);
        drawPin(g);
        if(ballFlag){
            if(DEBUG) {
                g.drawLine(ballX, ballY, ballX + BALL_WIDTH, ballY);
                g.drawLine(ballX, ballY, ballX, ballY + BALL_HEIGHT);
                g.drawLine(ballX + BALL_WIDTH, ballY, ballX + BALL_WIDTH, ballY + BALL_HEIGHT);
                g.drawLine(ballX, ballY + BALL_HEIGHT, ballX + BALL_WIDTH, ballY + BALL_HEIGHT);
            }
            g.fillOval(ballX,ballY,BALL_WIDTH,BALL_HEIGHT);
        }
    }
}
