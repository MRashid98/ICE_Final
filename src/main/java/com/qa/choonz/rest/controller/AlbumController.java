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

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.service.AlbumService;

@RestController
@RequestMapping("/albums")
@CrossOrigin
public class AlbumController {

    private AlbumService service;

    public AlbumController(AlbumService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")

    public ResponseEntity<AlbumDTO> create(@RequestBody AlbumDTO album) {
        ModelMapper mapper = new ModelMapper();
        Album returned = mapper.map(album,Album.class);
        return new ResponseEntity<>(this.service.create(returned), HttpStatus.CREATED);

    }

    @GetMapping("/read")
    public ResponseEntity<List<AlbumDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<AlbumDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }
    @GetMapping("/read/artists/{id}")
    public ResponseEntity<List<AlbumDTO>> readArtists(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.readArtist(id), HttpStatus.OK);
    }
    @GetMapping("/read/genres/{id}")
    public ResponseEntity<List<AlbumDTO>> readGenres(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.readGenre(id), HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<AlbumDTO> update(@RequestBody AlbumDTO album, @PathVariable Long id) {
        return new ResponseEntity<>(this.service.update(album, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<AlbumDTO> delete(@PathVariable Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
