package br.com.attus.contratomanager.validation;

import br.com.attus.contratomanager.exception.ServiceException;
import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.model.TipoEvento;
import br.com.attus.contratomanager.repository.ContratoRepository;
import br.com.attus.contratomanager.repository.EventoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoValidatorImplTest {

    @InjectMocks
    private EventoValidatorImpl eventoValidator;
    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private ContratoRepository contratoRepository;

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
    void testValidarEventoExistente_ComEventoNaoExistente_DeveLancarExcecao() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            eventoValidator.validarEventoExistente(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Evento não encontrado", exception.getMessage());
    }

    @Test
    void testValidarEventoExistente_ComEventoExistente_DeveRetornarEvento() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        Evento result = eventoValidator.validarEventoExistente(1L);

        assertNotNull(result);
        assertEquals(evento.getId(), result.getId());
    }

    @Test
    void testValidarContratoExistente_ComContratoNaoExistente_DeveLancarExcecao() {
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            eventoValidator.validarContratoExistente(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("Contrato com id 1 não encontrado", exception.getMessage());
    }

    @Test
    void testValidarContratoExistente_ComContratoExistente_DeveNaoLancarExcecao() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(new Contrato()));

        Assertions.assertDoesNotThrow(() -> {
            eventoValidator.validarContratoExistente(1L);
        });
    }
}