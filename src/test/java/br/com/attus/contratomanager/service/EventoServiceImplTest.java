package br.com.attus.contratomanager.service;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.model.TipoEvento;
import br.com.attus.contratomanager.repository.EventoRepository;
import br.com.attus.contratomanager.validation.EventoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceImplTest {

    @InjectMocks
    private EventoServiceImpl eventoService;
    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private EventoValidator eventoValidator;
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
    void testFindAll() {
        Evento evento2 = new Evento();
        evento2.setId(2L);
        evento2.setTipoEvento(TipoEvento.ASSINATURA);
        evento2.setDataRegistro(LocalDate.of(2024, 2, 1));
        evento2.setDescricao("Descrição do evento 2");

        List<Evento> eventos = Arrays.asList(evento, evento2);

        when(eventoRepository.findAll()).thenReturn(eventos);

        List<Evento> result = eventoService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(eventoRepository, times(1)).findAll();
    }

    @Test
    void testAddEvento() {
        when(eventoRepository.save(evento)).thenReturn(evento);

        Evento result = eventoService.addEvento(evento);

        assertNotNull(result);
        assertEquals(evento.getTipoEvento(), result.getTipoEvento());
        assertEquals(evento.getDataRegistro(), result.getDataRegistro());
        assertEquals(evento.getDescricao(), result.getDescricao());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    void testUpdateEvento() {
        when(eventoValidator.validarEventoExistente(1L)).thenReturn(evento);
        when(eventoRepository.save(evento)).thenReturn(evento);

        Evento result = eventoService.updateEvento(1L, evento);

        assertNotNull(result);
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    void testFindById() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        Optional<Evento> result = eventoService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(evento.getId(), result.get().getId());
        verify(eventoRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteEvento() {
        when(eventoValidator.validarEventoExistente(1L)).thenReturn(evento);

        eventoService.deleteEvento(1L);

        verify(eventoRepository, times(1)).delete(evento);
    }

}