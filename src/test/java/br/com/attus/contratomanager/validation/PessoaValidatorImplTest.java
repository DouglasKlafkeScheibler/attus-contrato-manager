package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.dto.ContratoIdDTO;
import br.com.attus.contratomanager.exception.ServiceException;
import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.model.TipoPessoa;
import br.com.attus.contratomanager.repository.PessoaRepository;
import br.com.attus.contratomanager.service.ContratoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaValidatorImplTest {

    @InjectMocks
    private PessoaValidatorImpl pessoaValidator;
    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private ContratoService contratoService;

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Pessoa Teste");
        pessoa.setCpfCnpj("69254550000133");
        pessoa.setContato("pessoa@teste.com");
        pessoa.setTipoPessoa(TipoPessoa.FORNECEDOR);
    }

    @Test
    void testValidarCpfCnpjUnico_ComCpfCnpjExistente_DeveLancarExcecao() {
        when(pessoaRepository.findByCpfCnpj(pessoa.getCpfCnpj()))
                .thenReturn(Optional.of(pessoa));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            pessoaValidator.validarCpfCnpjUnico(pessoa.getCpfCnpj());
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("CPF/CNPJ '69254550000133' já cadastrado", exception.getMessage());
    }

    @Test
    void testValidarCpfCnpjUnico_ComCpfCnpjNaoExistente_DeveNaoLancarExcecao() {
        when(pessoaRepository.findByCpfCnpj(pessoa.getCpfCnpj()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> pessoaValidator.validarCpfCnpjUnico(pessoa.getCpfCnpj()));
    }

    @Test
    void testValidarPessoaExistente_ComPessoaNaoExistente_DeveLancarExcecao() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            pessoaValidator.validarPessoaExistente(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Pessoa não encontrada", exception.getMessage());
    }

    @Test
    void testValidarPessoaExistente_ComPessoaExistente_DeveRetornarPessoa() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaValidator.validarPessoaExistente(1L);

        assertNotNull(result);
        assertEquals(pessoa.getId(), result.getId());
        assertEquals(pessoa.getCpfCnpj(), result.getCpfCnpj());
        assertEquals(pessoa.getNome(), result.getNome());
    }

    @Test
    void testValidarContratosExistentes_ComContratosAssociados_DeveLancarExcecao() {
        List<ContratoIdDTO> contratos = Arrays.asList(
                new ContratoIdDTO(1L),
                new ContratoIdDTO(2L)
        );
        when(contratoService.findContratosByCpfCnpj(pessoa.getCpfCnpj())).thenReturn(contratos);

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            pessoaValidator.validarContratosExistentes(pessoa.getCpfCnpj());
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Existem contratos associados a este CPF/CNPJ que impedem a deleção, sendo os contratos: 1, 2", exception.getMessage());
    }

    @Test
    void testValidarContratosExistentes_SemContratosAssociados_DeveNaoLancarExcecao() {
        when(contratoService.findContratosByCpfCnpj(pessoa.getCpfCnpj())).thenReturn(List.of());

        assertDoesNotThrow(() -> pessoaValidator.validarContratosExistentes(pessoa.getCpfCnpj()));
    }
}