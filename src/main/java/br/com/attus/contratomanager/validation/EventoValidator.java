package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.model.Evento;

public interface EventoValidator {
    Evento validarEventoExistente(Long eventoId);
    void validarContratoExistente(Long contratoId);
}
