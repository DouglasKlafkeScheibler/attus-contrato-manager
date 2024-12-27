package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import br.com.attus.contratomanager.repository.ContratoRepository;
import br.com.attus.contratomanager.validation.ContratoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratoServiceImplTest {
    @InjectMocks
    private ContratoServiceImpl contratoService;
    @Mock
    private ContratoRepository contratoRepository;
    @Mock
    private ContratoValidator contratoValidator;
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
    void testFindAll() {
        Contrato contrato2 = new Contrato();
        contrato2.setId(2);
        contrato2.setNumeroContrato("654321");
        contrato2.setDataCriacao(LocalDate.of(2024, 1, 1));
        contrato2.setDescricao("Contrato teste");
        contrato2.setStatus(Status.SUSPENSO);

        List<Contrato> contratos = Arrays.asList(contrato, contrato2);

        when(contratoRepository.findAll()).thenReturn(contratos);

        List<Contrato> result = contratoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(contratoRepository, times(1)).findAll();
    }

    @Test
    void testAddContrato() {
        when(contratoRepository.save(contrato)).thenReturn(contrato);

        Contrato result = contratoService.addContrato(contrato);

        assertNotNull(result);
        assertEquals(contrato.getNumeroContrato(), result.getNumeroContrato());
        verify(contratoValidator).validarNumeroContratoUnico(contrato.getNumeroContrato());
        verify(contratoValidator).validarInicioContratoDiferenteEncerrado(contrato.getStatus());
        verify(contratoRepository, times(1)).save(contrato);
    }

    @Test
    void testUpdateContrato() {
        when(contratoValidator.validarContratoExistente(1L)).thenReturn(contrato);
        when(contratoRepository.save(contrato)).thenReturn(contrato);

        Contrato result = contratoService.updateContrato(1L, contrato);

        assertNotNull(result);
        assertEquals(contrato.getNumeroContrato(), result.getNumeroContrato());
        verify(contratoValidator).validarNumeroContratoUnico(contrato.getNumeroContrato());
        verify(contratoValidator).validarInicioContratoDiferenteEncerrado(contrato.getStatus());
        verify(contratoRepository, times(1)).save(contrato);
    }

    @Test
    void testFindById() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        Optional<Contrato> result = contratoService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(contrato.getId(), result.get().getId());
        verify(contratoRepository, times(1)).findById(1L);
    }

    @Test
    void testArquivarContrato() {
        Contrato contrato2 = new Contrato();
        contrato2.setId(2);
        contrato2.setNumeroContrato("654321");
        contrato2.setDataCriacao(LocalDate.of(2024, 1, 1));
        contrato2.setDescricao("Contrato teste");
        contrato2.setStatus(Status.ATIVO);

        List<Long> contratoIds = Arrays.asList(1L, 2L);
        List<Contrato> contratos = Arrays.asList(contrato, contrato2);

        when(contratoValidator.validarContratosExistentes(contratoIds)).thenReturn(contratos);
        when(contratoRepository.saveAll(contratos)).thenReturn(contratos);

        contratoService.arquivarContrato(contratoIds);

        assertEquals(Status.ARQUIVADO, contratos.get(0).getStatus());
        assertEquals(Status.ARQUIVADO, contratos.get(1).getStatus());
        verify(contratoRepository, times(1)).saveAll(contratos);
    }

}