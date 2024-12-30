package br.com.attus.contratomanager.controller;

import br.com.attus.contratomanager.dto.ContratoDTO;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Status;
import br.com.attus.contratomanager.service.ContratoService;
import org.junit.jupiter.api.Assertions;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratoControllerTest {
    @InjectMocks
    private ContratoController contratoController;
    @Mock
    private ContratoService contratoService;
    private Contrato contrato;

    @BeforeEach
    void BeforeEach() {
        contrato = new Contrato();
        contrato.setId(1);
        contrato.setNumeroContrato("123456");
        contrato.setDataCriacao(LocalDate.of(2024, 1, 1));
        contrato.setDescricao("Contrato teste");
        contrato.setStatus(Status.SUSPENSO);
    }

    @Test
    void testAddContrato() {
        when(contratoService.addContrato(any(Contrato.class))).thenReturn(contrato);

        Assertions.assertDoesNotThrow(() -> {
            contratoController.addContrato(contrato);
        });

        verify(contratoService, times(1)).addContrato(any(Contrato.class));
    }

    @Test
    void testFindAll() {
        Contrato contrato2 = new Contrato();
        contrato2.setId(2);
        contrato2.setNumeroContrato("789123");
        contrato2.setDataCriacao(LocalDate.of(2024, 1, 1));
        contrato2.setDescricao("Contrato teste 2");
        contrato2.setStatus(Status.ATIVO);

        List<Contrato> contratoList = Arrays.asList(contrato, contrato2);

        when(contratoService.findAll()).thenReturn(contratoList);

        Assertions.assertDoesNotThrow(() -> {
            contratoController.findAll();
        });

        verify(contratoService, times(1)).findAll();
    }

    @Test
    void testUpdateContrato() {
        when(contratoService.updateContrato(eq(1L), any(Contrato.class))).thenReturn(contrato);

        Assertions.assertDoesNotThrow(() -> {
            contratoController.updateContrato(1L, contrato);
        });

        verify(contratoService, times(1)).updateContrato(eq(1L), any(Contrato.class));
    }

    @Test
    void testArquivarContrato() {
        Assertions.assertDoesNotThrow(() -> {
            contratoController.arquivarContrato(List.of(1L));
        });

        verify(contratoService, times(1)).arquivarContrato(anyList());
    }

    @Test
    void testBuscarContratos_ComContratosEncontrados_DeveRetornarPaginaComContratos() {
        Contrato contrato2 = new Contrato();
        contrato2.setId(2);
        contrato2.setNumeroContrato("789123");
        contrato2.setDataCriacao(LocalDate.of(2024, 1, 1));
        contrato2.setDescricao("Contrato teste 2");
        contrato2.setStatus(Status.ATIVO);

        ContratoDTO contratoDTO1 = new ContratoDTO(
                contrato.getId(),
                contrato.getNumeroContrato(),
                contrato.getDataCriacao(),
                contrato.getDescricao(),
                contrato.getStatus(),
                null,
                null
        );

        ContratoDTO contratoDTO2 = new ContratoDTO(
                contrato2.getId(),
                contrato2.getNumeroContrato(),
                contrato2.getDataCriacao(),
                contrato2.getDescricao(),
                contrato2.getStatus(),
                null,
                null
        );

        Page<ContratoDTO> contratoPage = new PageImpl<>(Arrays.asList(contratoDTO1, contratoDTO2));

        when(contratoService.findContratosByStatusDataCriacaoCpfCnpjPageable(any(), any(), any(), any(Pageable.class)))
                .thenReturn(contratoPage);

        Page<ContratoDTO> result = contratoController.buscarContratos(Status.SUSPENSO, LocalDate.of(2024, 1, 1), "69254550000133", Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        verify(contratoService, times(1)).findContratosByStatusDataCriacaoCpfCnpjPageable(any(), any(), any(), any(Pageable.class));
    }

    @Test
    void testBuscarContratos_SemContratosEncontrados_DeveRetornarPaginaVazia() {
        Page<ContratoDTO> contratoPage = new PageImpl<>(List.of());

        when(contratoService.findContratosByStatusDataCriacaoCpfCnpjPageable(any(), any(), any(), any(Pageable.class)))
                .thenReturn(contratoPage);

        Page<ContratoDTO> result = contratoController.buscarContratos(Status.ATIVO, LocalDate.of(2024, 1, 1), "69254550000133", Pageable.unpaged());

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());

        verify(contratoService, times(1)).findContratosByStatusDataCriacaoCpfCnpjPageable(any(), any(), any(), any(Pageable.class));
    }
}