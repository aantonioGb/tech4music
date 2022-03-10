package br.com.tech4music.tech4music.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech4music.tech4music.service.MusicaService;
import br.com.tech4music.tech4music.shared.MusicaDTO;

@RestController
@RequestMapping("/api/musicas")
public class musicaController {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private MusicaService servico;

    @GetMapping
    public ResponseEntity<List<MusicaDTO>> obterMusicas(){
        List<MusicaDTO> dto = servico.obterTodasAsMusicas();

        List<MusicaDTO> musicas = dto.stream()
                        .map(m -> mapper.map(m, MusicaDTO.class))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(musicas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaDTO> obterMusicaPorId(@PathVariable String id){
        Optional<MusicaDTO> musica = servico.obterMusicaPorId(id);

        if(musica.isPresent()){
            return new ResponseEntity<>(mapper.map(musica.get(),MusicaDTO.class), (HttpStatus.FOUND));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<MusicaDTO> cadastrarMusica(@RequestBody @Valid MusicaDTO musica){

        return new ResponseEntity<>(servico.armazenarMusica(musica), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaDTO> atualizarMusica(@PathVariable String id, @RequestBody @Valid MusicaDTO musica){

        return new ResponseEntity<>(servico.atualizarMusica(id, musica), HttpStatus.ACCEPTED);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<MusicaDTO> deletarMusica(@PathVariable String id){
        servico.excluirMusicaPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
