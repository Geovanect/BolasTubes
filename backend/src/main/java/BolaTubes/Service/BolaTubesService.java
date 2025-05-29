package BolaTubes.Service;

import java.util.Collections;
import java.util.Stack;

import org.springframework.stereotype.Service;

import BolaTubes.Model.Bola;
import BolaTubes.Model.Pilha;

@Service

public class BolaTubesService {
    private static Stack<Pilha> pilhas = new Stack<>();

    static {
        for(int i = 0; i < 7; i++){
            pilhas.add(new Pilha()); // 7 pilhas, capacidade 4 cada (definido em Pilha.java)
        }
    }

    public void iniciarJogo(){
        for(Pilha pilha : pilhas){
            pilha.getBolas().clear();
        }

        // Usaremos 6 cores distintas para ter 1 tubo vazio no início
        String[] cores = {"Vermelho", "Azul", "Verde", "Amarelo", "Laranja", "Roxo"};
        // A 7ª cor ("Branco") não será usada para deixar um tubo vazio ao final da distribuição.

        Stack<Bola> todasBolas = new Stack<>();

        // Criar 4 bolas de cada uma das 6 cores (Total 24 bolas)
        for(String cor : cores){
            for(int i = 0; i < 4; i++){ // 4 bolas por cor
                todasBolas.add(new Bola(cor));
            }
        }

        Collections.shuffle(todasBolas); // Embaralhar as 24 bolas

        // Distribuir as 24 bolas entre os primeiros 6 tubos, 4 bolas por tubo
        // O 7º tubo permanecerá vazio.
        int index = 0;
        for(int i = 0; i < 6; i++){ // Iterar sobre os primeiros 6 tubos
            for(int j = 0; j < 4; j++) { // Adicionar 4 bolas a cada um desses 6 tubos
               if(index < todasBolas.size()) {
                   pilhas.get(i).adicionarBola(todasBolas.get(index++));
               } else {
                   System.err.println("Tentativa de adicionar mais bolas do que o disponível (isso não deveria ocorrer aqui)!");
                   break;
               }
            }
        }
        // Após este loop, pilhas.get(0) a pilhas.get(5) terão 4 bolas cada.
        // pilhas.get(6) estará vazia, pois foi apenas limpa e não recebeu bolas.
    }

    public boolean moverBola(int origem, int destino){
        if(origem < 0 || origem >= pilhas.size() || destino < 0 || destino >= pilhas.size()){
            return false;
        }

        Pilha pilhaOrigem = pilhas.get(origem);
        Pilha pilhaDestino = pilhas.get(destino);

        if(!pilhaOrigem.getBolas().isEmpty()){

            Bola bola = pilhaOrigem.getBolas().peek();

            if(pilhaDestino.getBolas().isEmpty() || 
              (!pilhaDestino.estaCheia() && pilhaDestino.topoCorIgual(bola))) {

                pilhaOrigem.getBolas().pop();

                System.out.println("Movendo bola de " + origem + " para " + destino + ": " + bola.getCor());

                boolean sucesso = pilhaDestino.adicionarBola(bola);
                if (!sucesso) {
                    System.err.println("Falha ao adicionar bola no destino APESAR das checagens - Pilha destino pode estar cheia.");
                }
                System.out.println("Bola movida com sucesso para o backend: " + sucesso);
                return sucesso;
            }
            return false;
        }
        return false;
    }

    public boolean jogoFinalizado(){
        // Condição de vitória: 6 tubos cheios com cores únicas, 1 tubo vazio.
        // Ou: Todos os 7 tubos cheios com cores únicas (se todas as 28 bolas forem usadas e redistribuídas)
        // A lógica atual (todos os tubos cheios com bolas da mesma cor) implica que não haverá tubos vazios no final.
        // Se um tubo DEVE estar vazio no final, a lógica precisa mudar.
        // Assumindo que para ganhar, todas as bolas coloridas (24 delas) devem estar em 6 tubos, cada um com sua cor.
        // E o 7º tubo deve estar vazio.

        int tubosCompletosDeUmaCor = 0;
        int tubosVazios = 0;

        for(Pilha pilha : pilhas){
            if(pilha.getBolas().isEmpty()){
                tubosVazios++;
                continue;
            }
            if(pilha.estaCheia()){ // estáCheia() é 4 bolas
                String corBase = null;
                if (!pilha.getBolas().isEmpty()) {
                    corBase = pilha.getBolas().peek().getCor(); // Pega a cor de uma bola para comparar
                }
                boolean mesmoCor = true;
                for(Bola bola : pilha.getBolas()){
                    if(!bola.getCor().equals(corBase)){
                        mesmoCor = false;
                        break;
                    }
                }
                if(mesmoCor){
                    tubosCompletosDeUmaCor++;
                }
            } else {
                return false; // Se um tubo não está nem vazio nem cheio e completo, não é estado final.
            }
        }
        // Condição de vitória: 6 tubos completos, cada um com uma única cor, e 1 tubo vazio.
        return tubosCompletosDeUmaCor == 6 && tubosVazios == 1;
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
