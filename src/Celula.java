public class Celula {
    private boolean temMina;
    private boolean aberta;
    private boolean marcada;
    private int valor;

    public boolean isMina() {
        return temMina;
    }

    public void setMina(boolean temMina) {
        this.temMina = temMina;
    }

    public boolean isAberta() {
        return aberta;
    }
    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public boolean isMarcada() {
        return marcada;
    }
    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }

    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }


}
