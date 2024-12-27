package br.com.attus.contratomanager.controller;

import br.com.attus.contratomanager.model.Pessoa;
import br.com.attus.contratomanager.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/pessoas")
    ResponseEntity<Pessoa> addPessoa(@Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaCriada = pessoaService.addPessoa(pessoa);
        return new ResponseEntity<>(pessoaCriada, HttpStatus.CREATED);
    }

    @GetMapping("/pessoas")
    ResponseEntity<List<Pessoa>> findAll() {
        List<Pessoa> pessoaList = pessoaService.findAll();
        return new ResponseEntity<>(pessoaList, HttpStatus.OK);
    }

    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable("id") long id, @Valid @RequestBody Pessoa pessoaBody) {
        Pessoa pessoaAtualizada = pessoaService.updatePessoa(id, pessoaBody);
        return new ResponseEntity<>(pessoaAtualizada, HttpStatus.OK);
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable("id") long id) {
        pessoaService.deletePessoa(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
