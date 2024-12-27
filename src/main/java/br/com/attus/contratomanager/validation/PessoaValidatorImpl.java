package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.exception.ServiceException;
import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PessoaValidatorImpl implements PessoaValidator{

    @Autowired
    private PessoaRepository pessoaRepository;

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
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada"));
    }


}