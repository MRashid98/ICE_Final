package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qa.choonz.exception.GenreNotFoundException;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.repository.GenreRepository;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.utils.SAPIBeanUtils;

@Service
@Transactional
public class GenreService {

    private GenreRepository repo;
    private ModelMapper mapper;

    public GenreService(GenreRepository repo, ModelMapper mapper) {
        super();
        this.repo = repo;
        this.mapper = mapper;
    }

    private GenreDTO mapToDTO(Genre genre) {
        return this.mapper.map(genre, GenreDTO.class);
    }

    public GenreDTO create(Genre genre) {
        Genre created = this.repo.save(genre);
        return this.mapToDTO(created);
    }

    public List<GenreDTO> read() {
        return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    public List<GenreDTO> readDesc() {
        return this.repo.findAllDesc().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    public List<GenreDTO> readByName() {
        return this.repo.orderByName().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    public List<GenreDTO> readByNameDesc() {
        return this.repo.orderByNameDesc().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public GenreDTO read(long id) {
        Genre found = this.repo.findById(id).orElseThrow(GenreNotFoundException::new);
        return this.mapToDTO(found);
    }


    public GenreDTO update(GenreDTO genre, long id) {
        Genre toUpdate = this.repo.findById(id).orElseThrow(GenreNotFoundException::new);
        SAPIBeanUtils.mergeNotNull(genre,toUpdate);
        return this.mapToDTO(this.repo.save(toUpdate));
    }

    public boolean delete(long id) {
        this.repo.deleteById(id);
        return !this.repo.existsById(id);
    }

}
