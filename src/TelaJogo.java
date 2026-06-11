import javax.swing.*;
import java.awt.*;

public class TelaJogo extends JFrame {
    JButton[][] botoes;
    public TelaJogo() {
        setTitle("Campo Minado");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 9));
        botoes = new   JButton[9][9];
        for (int lin = 0; lin < 9; lin++) {
            for (int col = 0; col < 9; col++) {
                this.botoes[lin][col] = new JButton();
                add(botoes[lin][col]);
            }
        }
        setVisible(true);
    }
}
