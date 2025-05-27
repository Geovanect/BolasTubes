package BolaTubes.Model;

public class Bola {
    private String cor;
    private boolean movida;

    public Bola(String cor){
        this.cor = cor;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
    public boolean isMovida(){
        return movida;
    }
    public void setMovida(boolean movida){
        this.movida = movida;
    }

}
