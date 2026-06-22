import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CampoMinado {
    private int linhas;
    private int colunas;
    private int minas;
    private Celula[][] campo;
    private boolean gameOver = false;
    private int bandeirasRestantes;

    public enum Nivel {
        FACIL, MEDIO, DIFICIL
    }

    public CampoMinado(Nivel nivel) {

        if (nivel == Nivel.FACIL) {
            linhas = 9;
            colunas = 9;
            minas = 10;
        } else if (nivel == Nivel.MEDIO) {
            linhas = 16;
            colunas = 16;
            minas = 40;
        } else {
            linhas = 30;
            colunas = 16;
            minas = 99;
        }

        campo = new Celula[linhas][colunas];
        bandeirasRestantes = minas;

        inicializarCampo();         // Construtors pois quero que os metodos sejam executados quando o objeto for criado
        colocarMinas();
        calcularNumeros();
    }

    public int getBandeirasRestantes() {
        return bandeirasRestantes;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Celula getCelula(int linha, int coluna) {
        return campo [linha][coluna];
    }

    public void perdeu() {
        gameOver = true;
    }

    public void removerBandeira() {
        bandeirasRestantes++;
    }

    public void adicionarBandeira() {
        bandeirasRestantes--;
    }

    public boolean podeMarcar() {
        return bandeirasRestantes > 0;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean venceu() {

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {

                Celula c = campo[i][j];

                // se tem mina e não está marcada ainda não ganhou
                if (c.isMina() && !c.isMarcada()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void inicializarCampo() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                campo[i][j] = new Celula();
            }
        }
    }

    private void colocarMinas() {

        List<Posicao> posicoes = new ArrayList<>();
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    posicoes.add(new Posicao(i, j));          // aqui criamos todas as posições do campo
                }
            }


        Collections.shuffle(posicoes);      // Misturar as posições

        for (int i = 0; i < minas; i++) {
            Posicao p = posicoes.get(i);             // escolher posição embaralhada
            campo[p.linha][p.coluna].setMina(true);        // marcar na posição que tem mina
        }
    }
    public void revelarResultado(JButton[][] botoes) {

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {

                Celula c = campo[i][j];
                JButton b = botoes[i][j];

                b.setEnabled(false);
                b.setOpaque(true);
                b.setBorderPainted(false);

                if (c.isMina() && c.isMarcada()) {
                    b.setText("🚩");
                    b.setBackground(java.awt.Color.GREEN);

                }

                else if (c.isMina() && !c.isMarcada()) {
                    b.setText("💣");
                    b.setBackground(java.awt.Color.RED);

                }

                else if (!c.isMina() && c.isMarcada()) {
                    b.setText("🚩");
                    b.setBackground(java.awt.Color.ORANGE);

                }

                else {
                    b.setText(String.valueOf(c.getValor()));
                    b.setBackground(java.awt.Color.LIGHT_GRAY);
                }
            }
        }
    }
    private void calcularNumeros() {

        // percorre todas as linhas do tabuleiro
        for (int linha = 0; linha < linhas; linha++) {

            // percorre todas as colunas do tabuleiro
            for (int coluna = 0; coluna < colunas; coluna++) {

                // se a célula já for uma mina, não calcula número
                if (campo[linha][coluna].isMina()) {
                    continue;
                }

                int minasAoRedor = 0;

                // percorre os vizinhos (casas ao redor da célula)
                for (int deslocamentoLinha = -1; deslocamentoLinha <= 1; deslocamentoLinha++) {
                    for (int deslocamentoColuna = -1; deslocamentoColuna <= 1; deslocamentoColuna++) {

                        int linhaVizinha = linha + deslocamentoLinha;
                        int colunaVizinha = coluna + deslocamentoColuna;

                        // verifica se a posição está dentro do tabuleiro
                        if (linhaVizinha >= 0 && linhaVizinha < linhas &&
                                colunaVizinha >= 0 && colunaVizinha < colunas) {

                            // se existir mina na vizinha, incrementa contador
                            if (campo[linhaVizinha][colunaVizinha].isMina()) {
                                minasAoRedor++;
                            }
                        }
                    }
                }

                // salva o número de minas ao redor na célula
                campo[linha][coluna].setValor(minasAoRedor);
            }
        }
    }
}