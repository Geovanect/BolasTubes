package BolaTubes.Controller;
import BolaTubes.Model.Pilha;
import BolaTubes.Service.BolaTubesService;
import BolaTubes.Model.Bola;
import java.util.List;
import BolaTubes.Service.BolaTubesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/BolaTubes")

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
    public ResponseEntity<Void> iniciarJogo() {
        bolaTubesService.iniciarJogo();
        return ResponseEntity.ok().build();
    }

    @PostMapping("jogo/mover")
    public boolean moverBola(@RequestParam int origem, @RequestParam int destino){
        return bolaTubesService.moverBola(origem, destino);
    }

    @GetMapping("jogo/finalizado")
    public boolean estaFinalizado(){
        return bolaTubesService.jogoFinalizado();
    }


}
