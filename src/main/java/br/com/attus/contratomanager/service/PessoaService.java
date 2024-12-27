package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {
    List<Pessoa> findAll();
    Pessoa addPessoa(Pessoa pessoa);
    Pessoa updatePessoa(Long id, Pessoa pessoa);
    Optional<Pessoa> findById(long pessoaId);
    void deletePessoa(long id);
}
