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

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.service.ArtistService;

@RestController
@RequestMapping("/artists")
@CrossOrigin
public class ArtistController {

    private ArtistService service;

    public ArtistController(ArtistService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtistDTO> create(@RequestBody ArtistDTO artist) {
        ModelMapper mapper = new ModelMapper();
        Artist returned = mapper.map(artist,Artist.class);
        return new ResponseEntity<>(this.service.create(returned), HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<ArtistDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }
    @GetMapping("/read/desc")
    public ResponseEntity<List<ArtistDTO>> readDesc() {
        return new ResponseEntity<>(this.service.readDesc(), HttpStatus.OK);
    }
    @GetMapping("/read/name")
    public ResponseEntity<List<ArtistDTO>> readByName() {
        return new ResponseEntity<>(this.service.readByName(), HttpStatus.OK);
    }
    @GetMapping("/read/nameDesc")
    public ResponseEntity<List<ArtistDTO>> readByNameDesc() {
        return new ResponseEntity<>(this.service.readByNameDesc(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<ArtistDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ArtistDTO> update(@RequestBody ArtistDTO artist, @PathVariable Long id) {
        return new ResponseEntity<>(this.service.update(artist, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ArtistDTO> delete(@PathVariable Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
