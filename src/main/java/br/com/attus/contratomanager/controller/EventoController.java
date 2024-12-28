package br.com.attus.contratomanager.controller;

import br.com.attus.contratomanager.model.Evento;
import br.com.attus.contratomanager.service.EventoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Evento")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping("/eventos")
    ResponseEntity<Evento> addEvento(@Valid @RequestBody Evento evento) {
        Evento eventoCriado = eventoService.addEvento(evento);
        return new ResponseEntity<>(eventoCriado, HttpStatus.CREATED);
    }

    @GetMapping("/eventos")
    ResponseEntity<List<Evento>> findAll() {
        List<Evento> eventoList = eventoService.findAll();
        return new ResponseEntity<>(eventoList, HttpStatus.OK);
    }

    @PutMapping("/eventos/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable("id") long id, @Valid @RequestBody Evento eventoBody) {
        Evento eventoAtualizado = eventoService.updateEvento(id, eventoBody);
        return new ResponseEntity<>(eventoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable("id") long id) {
        eventoService.deleteEvento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
