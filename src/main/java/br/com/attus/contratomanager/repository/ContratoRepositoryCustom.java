package br.com.attus.contratomanager.repository;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ContratoRepositoryCustom {

    Page<Contrato> findContratosByStatusDataCriacaoCpfCnpjPageable(Status status, String cpfCnpj, LocalDate dataCriacao, Pageable paginacao);
}