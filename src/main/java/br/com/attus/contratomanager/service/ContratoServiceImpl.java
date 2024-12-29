package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import br.com.attus.contratomanager.repository.ContratoRepository;
import br.com.attus.contratomanager.validation.ContratoValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoServiceImpl implements ContratoService {
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private ContratoValidator contratoValidator;

    @Override
    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }

    @Override
    @Transactional
    public Contrato addContrato(Contrato contrato) {
        contratoValidator.validarInicioContratoDiferenteEncerrado(contrato.getStatus());
        contratoValidator.validarNumeroContratoUnico(contrato.getNumeroContrato());

        return contratoRepository.save(contrato);
    }

    @Override
    @Transactional
    public Contrato updateContrato(Long id, Contrato contrato) {
        Contrato contratoExistente = contratoValidator.validarContratoExistente(id);
        contrato.setId(contratoExistente.getId());

        return contratoRepository.save(contrato);
    }

    @Override
    public Optional<Contrato> findById(long contratoId) {
        return contratoRepository.findById(contratoId);
    }

    @Override
    @Transactional
    public void arquivarContrato(List<Long> contratoIds) {
        List<Contrato> contratos = contratoValidator.validarContratosExistentes(contratoIds);

        contratos.forEach(contrato -> contrato.setStatus(Status.ARQUIVADO));

        contratoRepository.saveAll(contratos);
    }

    @Override
    public Page<ContratoDTO> findContratosByStatusDataCriacaoCpfCnpjPageable(Status status, String cpfCnpj, LocalDate dataCriacao, Pageable paginacao) {
        return contratoRepository.findContratosByStatusDataCriacaoCpfCnpjPageable(status, cpfCnpj, dataCriacao, paginacao);
    }

}
