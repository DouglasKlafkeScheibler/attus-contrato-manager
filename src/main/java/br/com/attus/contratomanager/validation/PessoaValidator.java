package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.model.Pessoa;

public interface PessoaValidator {
    void validarCpfCnpjUnico(String cpfCnpj);
    Pessoa validarPessoaExistente(Long pessoaId);
}
