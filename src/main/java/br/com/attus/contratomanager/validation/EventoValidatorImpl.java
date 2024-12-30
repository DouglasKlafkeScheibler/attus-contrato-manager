package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.exception.ServiceException;
import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.repository.ContratoRepository;
import br.com.attus.contratomanager.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class EventoValidatorImpl implements EventoValidator{

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ContratoRepository contratoRepository;

    @Override
    public Evento validarEventoExistente(Long eventoId) {
        return eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Evento não encontrado"));
    }

    @Override
    public void validarContratoExistente(Long contratoId) {
        contratoRepository.findById(contratoId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, String.format("Contrato com id %s não encontrado", contratoId)));
    }

}