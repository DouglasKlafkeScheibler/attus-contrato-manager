package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.dto.ContratoIdDTO;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContratoService {
    List<Contrato> findAll();
    Contrato addContrato(Contrato contrato);
    Contrato updateContrato(Long id, Contrato contrato);
    Optional<Contrato> findById(long contratoId);
    void arquivarContrato(List<Long> contratoIds);
    Page<ContratoDTO> findContratosByStatusDataCriacaoCpfCnpjPageable(Status status, String cpfCnpj, LocalDate dataCriacao, Pageable paginacao);
    List<ContratoIdDTO> findContratosByCpfCnpj(String cpfCnpj);
}
