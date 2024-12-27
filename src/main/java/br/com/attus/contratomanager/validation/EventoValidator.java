package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.model.Status;

import java.util.List;

public interface EventoValidator {
    Evento validarEventoExistente(Long eventoId);
}
