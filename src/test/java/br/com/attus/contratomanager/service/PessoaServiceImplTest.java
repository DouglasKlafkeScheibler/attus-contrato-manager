package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.model.TipoPessoa;
import br.com.attus.contratomanager.repository.PessoaRepository;
import br.com.attus.contratomanager.validation.PessoaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceImplTest {
    @InjectMocks
    private PessoaServiceImpl pessoaService;
    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private PessoaValidator pessoaValidator;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Pessoa Teste");
        pessoa.setCpfCnpj("69.254.550/0001-33");
        pessoa.setContato("pessoa@teste.com");
        pessoa.setTipoPessoa(TipoPessoa.FORNECEDOR);
    }

    @Test
    void testFindAll() {
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2L);
        pessoa2.setNome("Pessoa Teste 2");
        pessoa2.setCpfCnpj("07048933984");
        pessoa2.setContato("pessoa@teste.com");
        pessoa2.setTipoPessoa(TipoPessoa.CLIENTE);

        List<Pessoa> pessoas = Arrays.asList(pessoa, pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> result = pessoaService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testAddPessoa() {
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa result = pessoaService.addPessoa(pessoa);

        assertNotNull(result);
        assertEquals(pessoa.getCpfCnpj(), result.getCpfCnpj());
        verify(pessoaValidator, times(1)).validarCpfCnpjUnico(pessoa.getCpfCnpj());
    }

    @Test
    void testUpdatePessoa() {
        Pessoa pessoaUpdated = new Pessoa();
        pessoaUpdated.setId(2L);
        pessoaUpdated.setNome("Pessoa Teste 2");
        pessoaUpdated.setCpfCnpj("07048933984");
        pessoaUpdated.setContato("pessoa2@teste.com");
        pessoaUpdated.setTipoPessoa(TipoPessoa.FORNECEDOR);

        when(pessoaValidator.validarPessoaExistente(1L)).thenReturn(pessoa);
        when(pessoaRepository.save(pessoaUpdated)).thenReturn(pessoaUpdated);

        Pessoa result = pessoaService.updatePessoa(1L, pessoaUpdated);

        assertNotNull(result);
        assertNotEquals(pessoa.getNome(), result.getNome());
        assertNotEquals(pessoa.getContato(), result.getContato());
        assertNotEquals(pessoa.getCpfCnpj(), result.getCpfCnpj());
        assertEquals(pessoa.getTipoPessoa(), result.getTipoPessoa());
    }

    @Test
    void testFindById() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> result = pessoaService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(pessoa.getId(), result.get().getId());
        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    void testDeletePessoa() {
        when(pessoaValidator.validarPessoaExistente(1L)).thenReturn(pessoa);

        pessoaService.deletePessoa(1L);

        verify(pessoaValidator, times(1)).validarPessoaExistente(1L);
        verify(pessoaRepository, times(1)).delete(pessoa);
    }

//    @Test
//    void testFormatarCpfCnpj() {
//        Pessoa pessoaComCnpj = new Pessoa();
//        pessoaComCnpj.setCpfCnpj("123.456.789/0001-10");
//
//        Pessoa result = pessoaService.formatarCpfCnpj(pessoaComCnpj);
//
//        assertEquals("123456789000110", result.getCpfCnpj());
//    }
}