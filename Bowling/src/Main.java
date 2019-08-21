import GUI.GameBoard;

import javax.swing.*;
import java.awt.*;


public class Main extends JPanel {

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
