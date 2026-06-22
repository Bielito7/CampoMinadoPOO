import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class TelaJogo extends JFrame {

    private JButton[][] botoes;
    private CampoMinado jogo;

    private JPanel painelJogo;

    private JButton reset;
    private JLabel lblMinas;

    private CampoMinado.Nivel nivelAtual =
            CampoMinado.Nivel.FACIL;

    public TelaJogo() {

        setTitle("Campo Minado");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        criarMenu();

        jogo = new CampoMinado(nivelAtual);

        painelJogo = new JPanel();

        reset = new JButton("\uD83D\uDE42"); // emoji carinha feliz
        reset.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        reset.setPreferredSize(new Dimension(48, 36));
        reset.setBackground(new Color(192, 192, 192));
        reset.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        reset.setFocusPainted(false);

        lblMinas = new JLabel(
                String.format("%03d", jogo.getBandeirasRestantes())
        );
        lblMinas.setFont(new Font("Courier New", Font.BOLD, 22));
        lblMinas.setForeground(Color.RED);
        lblMinas.setBackground(Color.BLACK);
        lblMinas.setOpaque(true);
        lblMinas.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

        reset.addActionListener(
                e -> reiniciarJogo()
        );

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(new Color(192, 192, 192));
        painelSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(6, 6, 4, 6),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)
        ));

        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
        centro.setBackground(new Color(192, 192, 192));
        centro.add(reset);

        painelSuperior.add(lblMinas, BorderLayout.WEST);
        painelSuperior.add(centro,   BorderLayout.CENTER);

        add(painelSuperior, BorderLayout.NORTH);
        add(painelJogo,BorderLayout.CENTER);

        criarTabuleiro();

        setVisible(true);
    }

    private void criarMenu() {

        JMenuBar barraMenu = new JMenuBar();

        JMenu menuJogo = new JMenu("Jogo");

        JMenuItem facil = new JMenuItem("Fácil");

        JMenuItem medio = new JMenuItem("Médio");

        JMenuItem dificil = new JMenuItem("Difícil");

        facil.addActionListener(e -> mudarNivel(CampoMinado.Nivel.FACIL));

        medio.addActionListener(e -> mudarNivel(CampoMinado.Nivel.MEDIO));

        dificil.addActionListener(e -> mudarNivel(CampoMinado.Nivel.DIFICIL));

        menuJogo.add(facil);
        menuJogo.add(medio);
        menuJogo.add(dificil);

        barraMenu.add(menuJogo);
        setJMenuBar(barraMenu);
    }

    private void criarTabuleiro() {

        painelJogo.removeAll();

        painelJogo.setLayout(new GridLayout(jogo.getLinhas(), jogo.getColunas()));

        botoes = new JButton[jogo.getLinhas()][jogo.getColunas()];

        for (int lin = 0; lin < jogo.getLinhas(); lin++) {

            for (int col = 0; col < jogo.getColunas(); col++) {

                JButton botao = new JButton();

                botao.setFont(new Font("Arial", Font.BOLD, 16));

                // borda 3D clássica do Campo Minado
                botao.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                botao.setBackground(new Color(192, 192, 192));
                botao.setFocusPainted(false);

                botoes[lin][col] = botao;

                final int linha = lin;
                final int coluna = col;

                botao.addMouseListener(new java.awt.event.MouseAdapter() {

                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent e) {

                                if (jogo.isGameOver()) {
                                    return;
                                }

                                Celula celula = jogo.getCelula(linha, coluna);

                                // DIREITO
                                if (SwingUtilities.isRightMouseButton(e)) {

                                    if (!celula.isAberta()) {

                                        if (!celula.isMarcada()) {

                                            if (!jogo.podeMarcar()) {
                                                return;
                                            }

                                            celula.setMarcada(true);

                                            jogo.adicionarBandeira();

                                            botao.setText("\uD83D\uDEA9"); // bandeira
                                            botao.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
                                            botao.setForeground(Color.BLACK);

                                        } else {

                                            celula.setMarcada(false);

                                            jogo.removerBandeira();

                                            botao.setText("");
                                            botao.setFont(new Font("Arial", Font.BOLD, 16));
                                        }

                                        lblMinas.setText(String.format("%03d", jogo.getBandeirasRestantes()));

                                        if (jogo.venceu()) {

                                            reset.setText("\uD83D\uDE0E"); // emoji com oculos (vitoria)

                                            jogo.revelarResultado(botoes);

                                            JOptionPane.showMessageDialog(TelaJogo.this,
                                                    "Você venceu!"
                                            );
                                        }
                                    }
                                }

                                // ESQUERDO
                                if (SwingUtilities.isLeftMouseButton(e)) {

                                    if (celula.isMarcada() || celula.isAberta()) {
                                        return;
                                    }

                                    if (celula.isMina()) {
                                        reset.setText("\uD83D\uDE35"); // emoji X nos olhos (perdeu)

                                        celula.setAberta(true);
                                        jogo.perdeu();

                                        jogo.revelarResultado(botoes);

                                        JOptionPane.showMessageDialog(TelaJogo.this,
                                                "Game Over!"
                                        );

                                        return;
                                    }

                                    abrirCelula(linha, coluna);
                                }
                            }
                        }
                );

                painelJogo.add(botao);
            }
        }

        painelJogo.revalidate();
        painelJogo.repaint();
    }


    private void abrirCelula(int linha, int coluna) {

        // sai se estiver fora do tabuleiro
        if (linha < 0 || linha >= jogo.getLinhas() || coluna < 0 || coluna >= jogo.getColunas()) {
            return;
        }

        Celula celula = jogo.getCelula(linha, coluna);

        // sai se já aberta, marcada ou mina
        if (celula.isAberta() || celula.isMarcada() || celula.isMina()) {
            return;
        }

        celula.setAberta(true);

        JButton botao = botoes[linha][coluna];

        botao.setOpaque(true);

        botao.setBackground(new Color(160, 160, 160));
        botao.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120)));

        int valor = celula.getValor();

        if (valor == 0) {
            // célula vazia fica sem texto e abre as 8 vizinhas
            botao.setText("");

            for (int dl = -1; dl <= 1; dl++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dl == 0 && dc == 0) continue;
                    abrirCelula(linha + dl, coluna + dc);
                }
            }
        } else {
            botao.setText(String.valueOf(valor));
            botao.setForeground(corNumero(valor));
        }
    }

    private Color corNumero(int n) {
        return Color.BLACK;
    }

    private void reiniciarJogo() {

        reset.setText("\uD83D\uDE42"); // emoji feliz

        jogo = new CampoMinado(nivelAtual);

        lblMinas.setText(String.format("%03d", jogo.getBandeirasRestantes()));

        criarTabuleiro();
    }

    private void mudarNivel(CampoMinado.Nivel nivel) {

        nivelAtual = nivel;

        reiniciarJogo();
    }
}