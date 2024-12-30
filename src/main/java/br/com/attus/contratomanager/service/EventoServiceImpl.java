package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.repository.EventoRepository;
import br.com.attus.contratomanager.validation.EventoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private EventoValidator eventoValidator;

    @Override
    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    @Override
    @Transactional
    public Evento addEvento(Evento evento) {
        eventoValidator.validarContratoExistente(evento.getContrato().getId());

        return eventoRepository.save(evento);
    }

    @Override
    @Transactional
    public Evento updateEvento(Long id, Evento evento) {
        Evento eventoExistente = eventoValidator.validarEventoExistente(id);
        evento.setId(eventoExistente.getId());

        return addEvento(evento);
    }

    @Override
    public Optional<Evento> findById(long eventoId) {
        return eventoRepository.findById(eventoId);
    }

    @Override
    @Transactional
    public void deleteEvento(long id) {
        Evento evento = eventoValidator.validarEventoExistente(id);

        eventoRepository.delete(evento);
    }

}
