package br.com.attus.contratomanager.controller;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.model.TipoEvento;
import br.com.attus.contratomanager.service.EventoService;
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
class EventoControllerTest {
    @InjectMocks
    private EventoController eventoController;
    @Mock
    private EventoService eventoService;
    private Evento evento;

    @BeforeEach
    void setUp() {
        Contrato contrato = new Contrato();
        contrato.setId(1);

        evento = new Evento();
        evento.setId(1L);
        evento.setTipoEvento(TipoEvento.RENOVAÇÃO);
        evento.setDataRegistro(LocalDate.of(2024, 1, 1));
        evento.setDescricao("Descrição do evento de teste");
        evento.setContrato(contrato);
    }

    @Test
    void testAddEvento() {
        when(eventoService.addEvento(any(Evento.class))).thenReturn(evento);

        Assertions.assertDoesNotThrow(() -> {
            eventoController.addEvento(evento);
        });

        verify(eventoService, times(1)).addEvento(any(Evento.class));
    }

    @Test
    void testFindAll() {
        Evento evento2 = new Evento();
        evento2.setId(2L);
        evento2.setTipoEvento(TipoEvento.ASSINATURA);
        evento2.setDataRegistro(LocalDate.of(2024, 2, 1));
        evento2.setDescricao("Descrição do evento 2");

        List<Evento> eventoList = Arrays.asList(evento, evento2);

        when(eventoService.findAll()).thenReturn(eventoList);

        Assertions.assertDoesNotThrow(() -> {
            eventoController.findAll();
        });

        verify(eventoService, times(1)).findAll();
    }

    @Test
    void testUpdateEvento() {
        when(eventoService.updateEvento(eq(1L), any(Evento.class))).thenReturn(evento);

        Assertions.assertDoesNotThrow(() -> {
            eventoController.updateEvento(1L, evento);
        });

        verify(eventoService, times(1)).updateEvento(eq(1L), any(Evento.class));
    }

    @Test
    void testDeleteEvento() {
        doNothing().when(eventoService).deleteEvento(anyLong());

        Assertions.assertDoesNotThrow(() -> {
            eventoController.deleteEvento(1L);
        });

        verify(eventoService, times(1)).deleteEvento(anyLong());
    }
}