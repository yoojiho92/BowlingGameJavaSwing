package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JFrame {
    private static boolean DEBUG = false;
    private JPanel powerPanel;
    private JPanel bottomPanel;
    private ScorePanel scorePanel;
    private ScorePanel comScorePanel;
    private LinePanel linePanel;
    private ArrowPanel arrowPanel;


    public GameBoard()
    {
        super("Bowling Game");
        powerPanel = new JPanel();
        bottomPanel = new JPanel();
        scorePanel = new ScorePanel();
        comScorePanel = new ScorePanel();
        arrowPanel = new ArrowPanel();


        powerPanel.setBorder(new TitledBorder("Please \"Space Key\" Push!!!"));
        scorePanel.setBorder(new TitledBorder("PlayerScore"));
        comScorePanel.setBorder(new TitledBorder("ComputerScore"));

        linePanel = new LinePanel(arrowPanel,scorePanel,comScorePanel);

        JProgressBar jProgressBar = new JProgressBar();
        jProgressBar.setMaximum(1000);
        jProgressBar.setMinimum(0);
        jProgressBar.setValue(10);

        powerPanel.add(jProgressBar);

        bottomPanel.setLayout(new GridLayout(2,1));
        bottomPanel.add(comScorePanel);
        bottomPanel.add(powerPanel);

        add(arrowPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(linePanel, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.NORTH);

        setLocation(150,200);
        setSize(600,550);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        PowerThread th = new PowerThread(jProgressBar);
        th.start();
        addKeyListener(new KeyAdapter() {
            boolean flag = false;

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyChar() == ' '){
                    if(flag == false){
                        linePanel.initBall(arrowPanel.getArrow_x(),arrowPanel.getArrow_y());
                        flag = true;
                        th.ClearProgress();
                        th.setFlag(true);
                    }
                }else if(e.getKeyCode() == KeyEvent.VK_UP){
                    arrowPanel.arrowUp();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    arrowPanel.arrowDown();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyChar() == ' '){
                    th.setFlag(false);
                    flag = false;
                    linePanel.shootBall(jProgressBar.getValue(),arrowPanel.getArrow_x(),arrowPanel.getArrow_y());
                    if(DEBUG){
                        System.out.println("ProcessBar Value : "+jProgressBar.getValue());
                    }

                }
            }
        });
    }


    class PowerThread extends java.lang.Thread{
        JProgressBar jProgressBar;
        boolean flag = false;

        public PowerThread(JProgressBar jProgressBar){
            this.jProgressBar = jProgressBar;
        }


        @Override
        public void run() {
            super.run();
            while (1 == 1){
                while (flag){
                    jProgressBar.setValue(jProgressBar.getValue()+1);
                    try{
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

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void  ClearProgress(){
            jProgressBar.setValue(0);
        }
    }
    public void setScore(int round, int frame, int value){
        if(DEBUG){
            System.out.println("-------------------");
            System.out.println(round + "  " + frame + "  " + value);
            System.out.println("-------- -----------");
        }
        scorePanel.setScore(round,frame,String.valueOf(value));
        scorePanel.setScore(1,1,"1");
    }
}
