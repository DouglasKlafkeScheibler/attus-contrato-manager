package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.exception.ServiceException;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import br.com.attus.contratomanager.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContratoValidatorImpl implements ContratoValidator{

    @Autowired
    private ContratoRepository contratoRepository;

    @Override
    public void validarInicioContratoDiferenteEncerrado(Status status) {
        if (Status.ENCERRADO.equals(status)) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Contrato não pode inicializar com status encerrado");
        }
    }

    @Override
    public void validarNumeroContratoUnico(String numeroContrato) {
        contratoRepository.findByNumeroContrato(numeroContrato)
                .ifPresent(existeContrato -> {
                    String mensagemErro = String.format("Número do contrato '%s' já cadastrado",
                            numeroContrato);
                    throw new ServiceException(HttpStatus.BAD_REQUEST, mensagemErro);
                });
    }

    @Override
    public List<Contrato> validarContratosExistentes(List<Long> contratoIds) {
        List<Long> contratosNaoEncontrados = contratoIds.stream()
                .filter(contratoId -> contratoRepository.findById(contratoId).isEmpty())
                .toList();

        if (!contratosNaoEncontrados.isEmpty()) {
            throw new ServiceException(HttpStatus.NOT_FOUND,
                    String.format("Contratos com os seguintes IDs não foram encontrados: %s",
                            contratosNaoEncontrados.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(", "))));
        }

        return contratoRepository.findAllById(contratoIds);
    }

    @Override
    public Contrato validarContratoExistente(Long contratoId) {
        return contratoRepository.findById(contratoId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Contrato não encontrado"));
    }


}