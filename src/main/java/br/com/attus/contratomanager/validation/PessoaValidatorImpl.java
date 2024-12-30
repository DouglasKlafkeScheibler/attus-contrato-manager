package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.dto.ContratoIdDTO;
import br.com.attus.contratomanager.exception.ServiceException;
import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.repository.PessoaRepository;
import br.com.attus.contratomanager.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PessoaValidatorImpl implements PessoaValidator{

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ContratoService contratoService;

    @Override
    public void validarCpfCnpjUnico(String cpfCnpj) {
        pessoaRepository.findByCpfCnpj(cpfCnpj)
                .ifPresent(existingPessoa -> {
                    String mensagemErro = String.format("CPF/CNPJ '%s' já cadastrado",
                            cpfCnpj);
                    throw new ServiceException(HttpStatus.BAD_REQUEST, mensagemErro);
                });
    }

    @Override
    public Pessoa validarPessoaExistente(Long pessoaId) {
        return pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
    }

    @Override
    public void validarContratosExistentes(String cpfCnpj) {
        List<ContratoIdDTO> contratosByCpfCnpj = contratoService.findContratosByCpfCnpj(cpfCnpj);

        if (!contratosByCpfCnpj.isEmpty()) {
            List<Long> contratoIds = contratosByCpfCnpj.stream()
                    .map(ContratoIdDTO::getId).toList();

            String ids = contratoIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new ServiceException(HttpStatus.BAD_REQUEST, "Existem contratos associados a este CPF/CNPJ que impedem a deleção, sendo os contratos: " + ids);
        }
    }


}