package cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.controllers;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.domain.FlorEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.dto.FlorDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.services.FlorClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9002")
@RestController
@RequestMapping("/flor/v1")
public class FlorClientController {
    private FlorClientService florClientService;

    public FlorClientController(FlorClientService florClientService){
        this.florClientService = florClientService;
    }

    @PostMapping("clientFlorsAdd")
    public ResponseEntity<String> createFlor(@RequestBody FlorEntity florEntity){
        florClientService.addFlor(florEntity).block();
        return new ResponseEntity<>("Flor creada correctament", HttpStatus.OK);

    }
    @PutMapping("clientFlorsUpdate/{id}")
    public ResponseEntity<String> updateFlor(@PathVariable("id") Integer id, @RequestBody FlorEntity florEntity){
        FlorDTO florDTO =  florClientService.getOneFlor(id).block();
        FlorEntity _florEntity = new FlorEntity(florDTO.getPk_FlorID(), florDTO.getNomFlor(), florEntity.getPaisFlor());
        _florEntity.setNomFlor(florEntity.getNomFlor());
        _florEntity.setPaisFlor(florEntity.getPaisFlor());
        florClientService.updateFlor(_florEntity).block();
        return new ResponseEntity<>("Flor actualitzada correctament", HttpStatus.OK);
    }

    @DeleteMapping("clientFlorsDelete/{id}")
    public ResponseEntity<String> deleteFlorEntity(@PathVariable("id") Integer id){
        florClientService.deleteFlor(id);
        return new ResponseEntity<>("Flor eliminada correctament", HttpStatus.OK);
    }

    @GetMapping("clientFlorsGetOne/{id}")
    public ResponseEntity<FlorDTO> getOne(@PathVariable("id") Integer id){
        FlorDTO florDTO = florClientService.getOneFlor(id).block();
        return new ResponseEntity<>(florDTO, HttpStatus.OK);
    }

    @GetMapping("clientFlorsAll")
    public ResponseEntity<List<FlorDTO>> getAll(){
        List<FlorDTO> flors = florClientService.getAllFlor().collectList().block();
        return new ResponseEntity<>(flors, HttpStatus.OK);
    }
}
