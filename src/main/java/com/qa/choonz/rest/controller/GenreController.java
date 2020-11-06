package com.qa.choonz.rest.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.service.GenreService;

@RestController
@RequestMapping("/genres")
@CrossOrigin
public class GenreController {

    private GenreService service;

    public GenreController(GenreService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> create(@RequestBody GenreDTO genre) {
        ModelMapper mapper = new ModelMapper();
        Genre returned = mapper.map(genre,Genre.class);
        return new ResponseEntity<>(this.service.create(returned), HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<GenreDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }
    @GetMapping("/read/desc")
    public ResponseEntity<List<GenreDTO>> readDesc() {
        return new ResponseEntity<>(this.service.readDesc(), HttpStatus.OK);
    }
    @GetMapping("/read/name")
    public ResponseEntity<List<GenreDTO>> readByName() {
        return new ResponseEntity<>(this.service.readByName(), HttpStatus.OK);
    }
    @GetMapping("/read/nameDesc")
    public ResponseEntity<List<GenreDTO>> readByNameDesc() {
        return new ResponseEntity<>(this.service.readByNameDesc(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<GenreDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<GenreDTO> update(@RequestBody GenreDTO genre, @PathVariable Long id) {
        return new ResponseEntity<>(this.service.update(genre, id), HttpStatus.ACCEPTED);
    }
    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenreDTO> delete(@PathVariable Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
