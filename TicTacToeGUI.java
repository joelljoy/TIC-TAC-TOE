import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean xTurn = true;
    private int moveCount = 0;

    private String playerX, playerO;
    private int scoreX = 0, scoreO = 0;

    private JLabel statusLabel;
    private JLabel scoreLabel;

    public TicTacToeGUI() {
        // Ask player names
        playerX = JOptionPane.showInputDialog(this, "Enter name for Player X:");
        if (playerX == null || playerX.trim().isEmpty()) playerX = "Player X";

        playerO = JOptionPane.showInputDialog(this, "Enter name for Player O:");
        if (playerO == null || playerO.trim().isEmpty()) playerO = "Player O";

        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        statusLabel = new JLabel(playerX + "'s turn (X)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel = new JLabel(scoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(statusLabel);
        topPanel.add(scoreLabel);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Box already selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        clicked.setText(xTurn ? "X" : "O");
        moveCount++;

        if (checkForWin()) {
            if (xTurn) scoreX++;
            else scoreO++;

            JOptionPane.showMessageDialog(this, (xTurn ? playerX : playerO) + " wins!");
            updateScoreboard();
            resetBoard();
        } else if (moveCount == 9) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetBoard();
        } else {
            xTurn = !xTurn;
            statusLabel.setText((xTurn ? playerX : playerO) + "'s turn (" + (xTurn ? "X" : "O") + ")");
        }
    }

    private boolean checkForWin() {
        String p = xTurn ? "X" : "O";
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(p) &&
                buttons[i][1].getText().equals(p) &&
                buttons[i][2].getText().equals(p)) return true;

            if (buttons[0][i].getText().equals(p) &&
                buttons[1][i].getText().equals(p) &&
                buttons[2][i].getText().equals(p)) return true;
        }

        return (buttons[0][0].getText().equals(p) &&
                buttons[1][1].getText().equals(p) &&
                buttons[2][2].getText().equals(p)) ||

               (buttons[0][2].getText().equals(p) &&
                buttons[1][1].getText().equals(p) &&
                buttons[2][0].getText().equals(p));
    }

    private void resetBoard() {
        moveCount = 0;
        xTurn = true;
        statusLabel.setText(playerX + "'s turn (X)");

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText("");
    }

    private void updateScoreboard() {
        scoreLabel.setText(scoreText());
    }

    private String scoreText() {
        return playerX + " (X): " + scoreX + "  |  " + playerO + " (O): " + scoreO;
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}

