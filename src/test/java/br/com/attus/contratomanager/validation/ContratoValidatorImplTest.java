package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.exception.ServiceException;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import br.com.attus.contratomanager.repository.ContratoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContratoValidatorImplTest {

    @InjectMocks
    private ContratoValidatorImpl contratoValidator;
    @Mock
    private ContratoRepository contratoRepository;
    private Contrato contrato;

    @BeforeEach
    void setUp() {
        contrato = new Contrato();
        contrato.setId(1);
        contrato.setNumeroContrato("123456");
        contrato.setDataCriacao(LocalDate.of(2024, 1, 1));
        contrato.setDescricao("Contrato teste");
        contrato.setStatus(Status.SUSPENSO);
    }

    @Test
    void testValidarInicioContratoDiferenteEncerrado_ComStatusEncerrado_DeveLancarExcecao() {
        contrato.setStatus(Status.ENCERRADO);

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            contratoValidator.validarInicioContratoDiferenteEncerrado(contrato.getStatus());
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Contrato não pode inicializar com status encerrado", exception.getMessage());
    }

    @Test
    void testValidarInicioContratoDiferenteEncerrado_ComStatusDiferente_DeveNaoLancarExcecao() {
        contrato.setStatus(Status.ATIVO);

        assertDoesNotThrow(() -> contratoValidator.validarInicioContratoDiferenteEncerrado(contrato.getStatus()));
    }

    @Test
    void testValidarNumeroContratoUnico_ComContratoExistente_DeveLancarExcecao() {
        when(contratoRepository.findByNumeroContrato(contrato.getNumeroContrato()))
                .thenReturn(Optional.of(contrato));

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            contratoValidator.validarNumeroContratoUnico(contrato.getNumeroContrato());
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("Número do contrato '123456' já cadastrado", exception.getMessage());
    }

    @Test
    void testValidarNumeroContratoUnico_ComContratoNaoExistente_DeveNaoLancarExcecao() {
        when(contratoRepository.findByNumeroContrato(contrato.getNumeroContrato()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> contratoValidator.validarNumeroContratoUnico(contrato.getNumeroContrato()));
    }

    @Test
    void testValidarContratosExistentes_ComContratosNaoEncontrados_DeveLancarExcecao() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));
        when(contratoRepository.findById(2L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            contratoValidator.validarContratosExistentes(List.of(1L, 2L));
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Contratos com os seguintes IDs não foram encontrados: 2", exception.getMessage());
    }

    @Test
    void testValidarContratosExistentes_ComTodosContratosEncontrados_DeveRetornarListaDeContratos() {
        Contrato contrato2 = new Contrato();
        contrato2.setId(2);
        contrato2.setNumeroContrato("654321");
        contrato2.setDataCriacao(LocalDate.of(2024, 1, 1));
        contrato2.setDescricao("Contrato teste");
        contrato2.setStatus(Status.SUSPENSO);

        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));
        when(contratoRepository.findById(2L)).thenReturn(Optional.of(contrato2));
        when(contratoRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(contrato, contrato2));

        var result = contratoValidator.validarContratosExistentes(List.of(1L, 2L));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testValidarContratoExistente_ComContratoNaoExistente_DeveLancarExcecao() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            contratoValidator.validarContratoExistente(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Contrato não encontrado", exception.getMessage());
    }

    @Test
    void testValidarContratoExistente_ComContratoExistente_DeveRetornarContrato() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        Contrato result = contratoValidator.validarContratoExistente(1L);

        assertNotNull(result);
        assertEquals(contrato.getId(), result.getId());
        assertEquals(contrato.getNumeroContrato(), result.getNumeroContrato());
    }
}