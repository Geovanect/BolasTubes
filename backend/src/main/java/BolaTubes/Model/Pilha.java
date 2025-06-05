package BolaTubes.Model;
import java.util.Stack;

public class Pilha {
    private Stack<Bola> bolas;

    public Pilha(){
        bolas = new Stack<>();
    }

    public boolean estaCheia(){
        return bolas.size() == 7;
    }

    public boolean topoCorIgual(Bola bola){
        return !bolas.isEmpty() && bolas.peek().getCor().equals(bola.getCor());
    }

    public boolean adicionarBola(Bola bola){
        if(bolas.size() < 7 ){
            bolas.push(bola);
            return true;
        }
        return false;
    }

    public Stack<Bola> getBolas(){
        return bolas;
    }

}
