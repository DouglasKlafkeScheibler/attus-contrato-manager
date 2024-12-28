package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.repository.PessoaRepository;
import br.com.attus.contratomanager.validation.PessoaValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PessoaValidator pessoaValidator;

    @Override
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Override
    @Transactional
    public Pessoa addPessoa(Pessoa pessoa) {
        Pessoa pessoaFormatada = formatarCpfCnpj(pessoa);
        pessoaValidator.validarCpfCnpjUnico(pessoaFormatada.getCpfCnpj());

        return pessoaRepository.save(pessoa);
    }

    @Override
    @Transactional
    public Pessoa updatePessoa(Long id, Pessoa pessoa) {
        Pessoa pessoaExistente = pessoaValidator.validarPessoaExistente(id);
        pessoa.setId(pessoaExistente.getId());

        Pessoa pessoaFormatada = formatarCpfCnpj(pessoa);

        return pessoaRepository.save(pessoaFormatada);
    }

    @Override
    public Optional<Pessoa> findById(long pessoaId) {
        return pessoaRepository.findById(pessoaId);
    }

    @Override
    @Transactional
    public void deletePessoa(long id) {
        Pessoa pessoa = pessoaValidator.validarPessoaExistente(id);

        pessoaRepository.delete(pessoa);
    }

    private Pessoa formatarCpfCnpj(Pessoa pessoa) {
        if (pessoa.getCpfCnpj() != null && pessoa.getCpfCnpj().matches(".*[^0-9].*")) {
            String cpfCnpjSemFormatacao = pessoa.getCpfCnpj().replaceAll("[^0-9]", "");
            pessoa.setCpfCnpj(cpfCnpjSemFormatacao);
        }
        return pessoa;
    }

}
