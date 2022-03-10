package br.com.tech4music.tech4music.service;

import java.util.List;
import java.util.Optional;

import br.com.tech4music.tech4music.shared.MusicaDTO;

public interface MusicaService {

    List<MusicaDTO> obterTodasAsMusicas();

    MusicaDTO armazenarMusica(MusicaDTO musica);

    Optional<MusicaDTO> obterMusicaPorId(String id);

    MusicaDTO atualizarMusica(String id, MusicaDTO musica);

    void excluirMusicaPorId(String id);

    
}
