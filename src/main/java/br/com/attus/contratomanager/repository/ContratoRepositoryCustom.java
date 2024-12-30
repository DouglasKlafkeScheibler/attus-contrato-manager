package br.com.attus.contratomanager.repository;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.dto.ContratoIdDTO;
import br.com.attus.contratomanager.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ContratoRepositoryCustom {

    Page<ContratoDTO> findContratosByStatusDataCriacaoCpfCnpjPageable(Status status, String cpfCnpj, LocalDate dataCriacao, Pageable paginacao);
    List<ContratoIdDTO> findContratosByCpfCnpj(String cpfCnpj);
}