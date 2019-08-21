package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ArrowPanel extends JPanel {
    private int arrow_x = 100;
    private int arrow_y = 100;

    private Graphics graphics;

    private JPanel gameState;
    private JLabel playLabel;
    private JLabel roundLabel;

    private int round = 1;

    public ArrowPanel(){
        setPreferredSize(new Dimension(200,200));

        gameState = new JPanel();
        playLabel = new JLabel("player1");
        roundLabel = new JLabel(round + "Round");

        gameState.setLayout(new FlowLayout());
        playLabel.setBorder(new TitledBorder("Play"));
        roundLabel.setBorder(new TitledBorder("Round"));

        graphics = getGraphics();

        gameState.add(playLabel);
        gameState.add(roundLabel);
        add(gameState,BorderLayout.WEST);

    }

    public void setLableText(String text){
        playLabel.setText(text);
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void arrowUp(){
        if(arrow_y < 100){
            arrow_x -= 1;
        }else {
            arrow_x += 1;
        }
        arrow_y -= 1;
        repaint();
    }

    public void arrowDown(){
        if(arrow_y > 100){
            arrow_x -= 1;
        }else {
            arrow_x += 1;
        }
        arrow_y += 1;
        repaint();
    }

    public int getArrow_x() {
        return arrow_x;
    }

    public int getArrow_y() {
        return arrow_y;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(100,100, 150, 100);
        g.drawLine(150, 100, 150, arrow_y);
        g.drawLine(150, arrow_y, 200, arrow_y);
        g.drawLine(200,arrow_y,200-10,arrow_y-10);
        g.drawLine(200,arrow_y,200-10,arrow_y+10);
    }

    public JLabel getPlayLabel() {
        return playLabel;
    }

    public JLabel getRoundLabel() {
        return roundLabel;
    }
}
