package BolaTubes.Service;


import java.util.Collections;
import java.util.Stack;
import java.util.Random;
import BolaTubes.Model.Pilha;
import BolaTubes.Model.Bola;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service

public class BolaTubesService {
    private static Stack<Pilha> pilhas = new Stack<>();

    static {
        for(int i = 0; i<7; i++){
            pilhas.add((new Pilha()));
        }
    }



    public void iniciarJogo(){

        for(Pilha pilha : pilhas){
            pilha.getBolas().clear();
        }


        String[] cores = {"Vermelho", "Azul", "Verde", "Amarelo" , "Laranja" , "Roxo", "Branco" };
        Stack<Bola> todasBolas = new Stack<>();


        for(String cor: cores){
            for(int i = 0; i<7; i++){
                todasBolas.add(new Bola(cor));
            }
        }

        Collections.shuffle(todasBolas);

        int index = 0;

        for(int i = 0; i<6;i++){
            for(int j = 0; j<7; j++) {
               pilhas.get(i).adicionarBola(todasBolas.get(index++));
            }
        }

    }

    public boolean moverBola(int origem, int destino){
        if(origem < 0 || origem >= pilhas.size() || destino < 0 || destino >= pilhas.size()){
            return false;
        }

        Pilha pilhaOrigem = pilhas.get(origem);
        Pilha pilhaDestino = pilhas.get(destino);

        if(!pilhaOrigem.getBolas().isEmpty()){

            Bola bola = pilhaOrigem.getBolas().getFirst();  // Retira a bola do topo da pilha origem

            if(bola.isMovida()){  // Se a bola já foi movida, não permita movê-la novamente
                return false;
            }

            pilhaOrigem.getBolas().removeFirst();
            bola.setMovida(true);


            System.out.println("Movendo bola de " + origem + " para " + destino + ": " + bola.getCor());

            // Adiciona a bola no topo da pilha destino
            boolean sucesso = pilhaDestino.adicionarBola(bola);
            System.out.println("Bola movida com sucesso: " + sucesso);
            return sucesso;
        }
        return false;
    }

    public boolean jogoFinalizado(){
        for(Pilha pilha : pilhas){
            if(!pilha.estaCheia()){
                return false;
            }

            String corTopo = pilha.getBolas().peek().getCor();
            for(Bola bola : pilha.getBolas()){
                if(!bola.getCor().equals(corTopo)){
                    return false;
                }
            }
        }
        return true;
    }

    public Stack<Pilha> getPilhas(){
        return pilhas;
    }



    public void liberarBolasPilha(int pilhaindex){
        Pilha pilha = pilhas.get(pilhaindex);
        for(Bola bola : pilha.getBolas()){
            bola.setMovida(false);
        }
    }


}
