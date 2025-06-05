package BolaTubes.Service;

import java.util.Collections;
import java.util.Stack;

import org.springframework.stereotype.Service;

import BolaTubes.Model.Bola;
import BolaTubes.Model.Pilha;

@Service

public class BolaTubesService {
    private static Stack<Pilha> pilhas = new Stack<>();
    private Integer ultimoOrigem = null;
    private Integer ultimoDestino = null;

    static {
        for(int i = 0; i < 8; i++){
            pilhas.add(new Pilha()); // 8 pilhas, capacidade 7 cada
        }
    }

    public void iniciarJogo(){
        for(Pilha pilha : pilhas){
            pilha.getBolas().clear();
        }
        ultimoOrigem = null;
        ultimoDestino = null;

        String[] cores = {"Vermelho", "Azul", "Verde", "Amarelo", "Laranja", "Roxo", "Rosa"};
        Stack<Bola> todasBolas = new Stack<>();
        for(String cor : cores){
            for(int i = 0; i < 7; i++){
                todasBolas.add(new Bola(cor));
            }
        }

        boolean valido = false;
        while (!valido) {
            Collections.shuffle(todasBolas);
            int index = 0;
            for(int i = 0; i < 7; i++){
                pilhas.get(i).getBolas().clear();
                for(int j = 0; j < 7; j++) {
                    if(index < todasBolas.size()) {
                        pilhas.get(i).adicionarBola(todasBolas.get(index++));
                    }
                }
            }
            // pilhas.get(7) estará vazia
            java.util.Set<String> topos = new java.util.HashSet<>();
            for(int i = 0; i < 7; i++) {
                Stack<Bola> bolas = pilhas.get(i).getBolas();
                if (!bolas.isEmpty()) {
                    topos.add(bolas.peek().getCor());
                }
            }
            valido = (topos.size() == 7);
        }
        pilhas.get(7).getBolas().clear();
    }

    public boolean moverBola(int origem, int destino){
        // Impede desfazer o último movimento imediatamente
        if (ultimoOrigem != null && ultimoDestino != null && origem == ultimoDestino && destino == ultimoOrigem) {
            return false;
        }

        if(origem < 0 || origem >= pilhas.size() || destino < 0 || destino >= pilhas.size()){
            return false;
        }

        Pilha pilhaOrigem = pilhas.get(origem);
        Pilha pilhaDestino = pilhas.get(destino);

        if(pilhaOrigem.getBolas().isEmpty()){
            return false;
        }

        int espacoDisponivel = 7 - pilhaDestino.getBolas().size();
        if(espacoDisponivel < 1) {
            return false;
        }

        Bola bola = pilhaOrigem.getBolas().pop();
        pilhaDestino.getBolas().push(bola);

        // Atualiza o último movimento
        ultimoOrigem = origem;
        ultimoDestino = destino;
        return true;
    }

    public boolean jogoFinalizado(){
        // Condição de vitória: 6 tubos cheios com cores únicas, 1 tubo vazio.
        int tubosCompletosDeUmaCor = 0;
        int tubosVazios = 0;

        for(Pilha pilha : pilhas){
            if(pilha.getBolas().isEmpty()){
                tubosVazios++;
                continue;
            }
            if(pilha.estaCheia()){
                String corBase = null;
                if (!pilha.getBolas().isEmpty()) {
                    corBase = pilha.getBolas().peek().getCor();
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
                return false;
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

