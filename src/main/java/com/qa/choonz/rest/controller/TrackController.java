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

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;

@RestController
@RequestMapping("/tracks")
@CrossOrigin
public class TrackController {

    private TrackService service;

    public TrackController(TrackService service) {
        super();
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<TrackDTO> create(@RequestBody TrackDTO track) {
        ModelMapper mapper = new ModelMapper();
        Track returned = mapper.map(track,Track.class);
        return new ResponseEntity<>(this.service.create(returned), HttpStatus.CREATED);
    }

    @GetMapping("/read")
    public ResponseEntity<List<TrackDTO>> read() {
        return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<TrackDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
    }
    @GetMapping("/read/albums/{id}")

    public ResponseEntity<List<TrackDTO>> readAlbum(@PathVariable Long id) {
        return new ResponseEntity<>(this.service.readAlbum(id), HttpStatus.OK);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<TrackDTO> update(@RequestBody TrackDTO track, @PathVariable Long id) {
        return new ResponseEntity<>(this.service.update(track, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<TrackDTO> delete(@PathVariable Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
