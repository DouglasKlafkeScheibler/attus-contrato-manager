package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContratoValidator {
    void validarInicioContratoDiferenteEncerrado(Status status);
    void validarNumeroContratoUnico(String numeroContrato);
    List<Contrato> validarContratosExistentes(List<Long> contratoIds);
    Contrato validarContratoExistente(Long contratoId);
}
