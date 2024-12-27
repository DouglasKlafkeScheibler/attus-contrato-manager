package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Evento;

import java.util.List;
import java.util.Optional;

public interface ContratoService {
    List<Contrato> findAll();
    Contrato addContrato(Contrato contrato);
    Contrato updateContrato(Long id, Contrato contrato);
    Optional<Contrato> findById(long contratoId);
    void arquivarContrato(List<Long> contratoIds);
}
