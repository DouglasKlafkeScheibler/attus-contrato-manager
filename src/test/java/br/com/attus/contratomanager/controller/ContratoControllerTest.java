package br.com.attus.contratomanager.controller;

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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
}