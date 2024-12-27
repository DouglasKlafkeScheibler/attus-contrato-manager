package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface EventoService {
    List<Evento> findAll();
    Evento addEvento(Evento evento);
    Evento updateEvento(Long id, Evento evento);
    Optional<Evento> findById(long eventoId);
    void deleteEvento(long id);
}
