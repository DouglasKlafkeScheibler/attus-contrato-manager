package br.com.attus.contratomanager.controller;

import br.com.attus.contratomanager.model.Contrato;
import br.com.attus.contratomanager.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContratoController {
    @Autowired
    private ContratoService contratoService;

    @PostMapping("/contratos")
    ResponseEntity<Contrato> addContrato(@Valid @RequestBody Contrato contrato) {
        Contrato contratoCriado = contratoService.addContrato(contrato);
        return new ResponseEntity<>(contratoCriado, HttpStatus.CREATED);
    }

    @GetMapping("/contratos")
    ResponseEntity<List<Contrato>> findAll() {
        List<Contrato> contratoList = contratoService.findAll();
        return new ResponseEntity<>(contratoList, HttpStatus.OK);
    }

    @PutMapping("/contratos/{id}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable("id") long id, @Valid @RequestBody Contrato contratoBody) {
        Contrato contratoAtualizado = contratoService.updateContrato(id, contratoBody);
        return new ResponseEntity<>(contratoAtualizado, HttpStatus.OK);
    }

    @PostMapping("/arquivarContratos")
    ResponseEntity<Contrato> arquivarContrato(@Valid @RequestBody List<Long> contratoIds) {
        contratoService.arquivarContrato(contratoIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
