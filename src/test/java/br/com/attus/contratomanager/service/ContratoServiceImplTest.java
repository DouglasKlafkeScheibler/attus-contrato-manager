package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.dto.ContratoIdDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Test
    void testFindContratosByStatusDataCriacaoCpfCnpjPageable() {
        Status status = Status.ATIVO;
        String cpfCnpj = "19357451072";
        LocalDate dataCriacao = LocalDate.of(2024, 1, 1);
        Pageable pageable = Pageable.unpaged();

        ContratoDTO contratoDTO = new ContratoDTO();
        contratoDTO.setId(1L);
        contratoDTO.setNumeroContrato("123456");
        contratoDTO.setDescricao("Contrato teste");
        contratoDTO.setStatus(Status.ATIVO);
        contratoDTO.setDataCriacao(LocalDate.of(2024, 1, 1));

        Page<ContratoDTO> contratosPage = new PageImpl<>(Collections.singletonList(contratoDTO));

        when(contratoRepository.findContratosByStatusDataCriacaoCpfCnpjPageable(status, cpfCnpj, dataCriacao, pageable))
                .thenReturn(contratosPage);

        Page<ContratoDTO> result = contratoService.findContratosByStatusDataCriacaoCpfCnpjPageable(status, cpfCnpj, dataCriacao, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals("123456", result.getContent().get(0).getNumeroContrato());
        verify(contratoRepository, times(1))
                .findContratosByStatusDataCriacaoCpfCnpjPageable(status, cpfCnpj, dataCriacao, pageable);
    }

    @Test
    void testFindContratosByCpfCnpj() {
        String cpfCnpj = "19357451072";
        ContratoIdDTO contratoIdDTO = new ContratoIdDTO();
        contratoIdDTO.setId(1L);

        List<ContratoIdDTO> contratosIdDTO = Collections.singletonList(contratoIdDTO);

        when(contratoRepository.findContratosByCpfCnpj(cpfCnpj)).thenReturn(contratosIdDTO);

        List<ContratoIdDTO> result = contratoService.findContratosByCpfCnpj(cpfCnpj);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(contratoRepository, times(1)).findContratosByCpfCnpj(cpfCnpj);
    }

    @Test
    void testFindContratosByCpfCnpjWhenNoContractsFound() {
        String cpfCnpj = "19357451072";
        when(contratoRepository.findContratosByCpfCnpj(cpfCnpj)).thenReturn(Collections.emptyList());

        List<ContratoIdDTO> result = contratoService.findContratosByCpfCnpj(cpfCnpj);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(contratoRepository, times(1)).findContratosByCpfCnpj(cpfCnpj);
    }

}