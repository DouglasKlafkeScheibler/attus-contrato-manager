package br.com.attus.contratomanager.controller;

import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.model.TipoPessoa;
import br.com.attus.contratomanager.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {
    @InjectMocks
    private PessoaController pessoaController;
    @Mock
    private PessoaService pessoaService;
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
    void testAddPessoa() {
        when(pessoaService.addPessoa(any(Pessoa.class))).thenReturn(pessoa);

        Assertions.assertDoesNotThrow(() -> {
            pessoaController.addPessoa(pessoa);
        });

        verify(pessoaService, times(1)).addPessoa(any(Pessoa.class));
    }

    @Test
    void testFindAll() {
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2L);
        pessoa2.setNome("Pessoa Teste 2");
        pessoa2.setCpfCnpj("07048933984");
        pessoa2.setContato("pessoa@teste.com");
        pessoa2.setTipoPessoa(TipoPessoa.CLIENTE);

        List<Pessoa> pessoaList = Arrays.asList(pessoa, pessoa2);

        when(pessoaService.findAll()).thenReturn(pessoaList);

        Assertions.assertDoesNotThrow(() -> {
            pessoaController.findAll();
        });

        verify(pessoaService, times(1)).findAll();
    }

    @Test
    void testUpdatePessoa() {
        when(pessoaService.updatePessoa(eq(1L), any(Pessoa.class))).thenReturn(pessoa);

        Assertions.assertDoesNotThrow(() -> {
            pessoaController.updatePessoa(1L, pessoa);
        });

        verify(pessoaService, times(1)).updatePessoa(eq(1L), any(Pessoa.class));
    }

    @Test
    void testDeletePessoa() {
        doNothing().when(pessoaService).deletePessoa(anyLong());

        Assertions.assertDoesNotThrow(() -> {
            pessoaController.deletePessoa(1L);
        });

        verify(pessoaService, times(1)).deletePessoa(anyLong());
    }
}