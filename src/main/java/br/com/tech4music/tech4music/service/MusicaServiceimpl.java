package br.com.tech4music.tech4music.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tech4music.tech4music.model.Musica;
import br.com.tech4music.tech4music.repository.MusicaRepository;
import br.com.tech4music.tech4music.shared.MusicaDTO;

@Service
public class MusicaServiceimpl implements MusicaService{

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private MusicaRepository repositorio;

    @Override
    public List<MusicaDTO> obterTodasAsMusicas() {
        List<Musica> musicas = repositorio.findAll(); 

        return musicas.stream()
                .map(m -> mapper.map(m, MusicaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MusicaDTO> obterMusicaPorId(String id) {
        Optional<Musica> musica = repositorio.findById(id);

        if(musica.isPresent()){
            return Optional.of(mapper.map(musica.get(), MusicaDTO.class));
        }

        return Optional.empty();
    }

    @Override
    public MusicaDTO armazenarMusica(MusicaDTO musica) {
        Musica gravarmusica = mapper.map(musica, Musica.class);
        gravarmusica = repositorio.save(gravarmusica);

        return mapper.map(gravarmusica, MusicaDTO.class);
    }

    @Override
    public void excluirMusicaPorId(String id) {
        repositorio.deleteById(id);
    }

    @Override
    public MusicaDTO atualizarMusica(String id, MusicaDTO musica) {
        Musica musicaAtualizar = mapper.map(musica, Musica.class);
        musicaAtualizar.setId(id);
        musicaAtualizar = repositorio.save(musicaAtualizar);

        return mapper.map(musicaAtualizar, MusicaDTO.class);
    }
    
}