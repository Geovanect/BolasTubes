package BolaTubes.Controller;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BolaTubes.Model.MovimentoRequestDTO;
import BolaTubes.Model.Pilha;
import BolaTubes.Service.BolaTubesService;


@RestController
@RequestMapping("/BolaTubes")
@CrossOrigin(origins = "http://localhost:5173")
public class BolaTubesController {

    private final BolaTubesService bolaTubesService;

    public BolaTubesController(BolaTubesService bolaTubesService){
        this.bolaTubesService = bolaTubesService;
    }

    @GetMapping("jogo/estado")
    public List<Pilha> obterEstado(){
        return bolaTubesService.getPilhas();
    }
    @PostMapping("jogo/iniciar")
    public List<Pilha> iniciarJogo() {
        bolaTubesService.iniciarJogo();
        return bolaTubesService.getPilhas();
    }

    @PostMapping("jogo/mover")
    public boolean moverBola(@RequestBody MovimentoRequestDTO movimentoRequest){
        return bolaTubesService.moverBola(movimentoRequest.getOrigem(), movimentoRequest.getDestino());
    }

    @GetMapping("jogo/finalizado")
    public boolean estaFinalizado(){
        return bolaTubesService.jogoFinalizado();
    }


}
