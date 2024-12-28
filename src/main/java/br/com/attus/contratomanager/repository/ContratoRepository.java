package br.com.attus.contratomanager.repository;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>, ContratoRepositoryCustom {

    Optional<Contrato> findByNumeroContrato(String numeroContrato);
}
