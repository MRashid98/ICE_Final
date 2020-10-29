package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.persistence.repository.UserRepository;
import com.qa.choonz.rest.dto.UserDTO;

@SpringBootTest
public class UserServiceIntergrationTest {
	
	@Autowired
    private UserService service;

    @Autowired
    private UserRepository repo;
    

    @Autowired
    private ModelMapper modelMapper;


    private User testUser;
    private User testUserWithId;


    
    private UserDTO mapToDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }


    @BeforeEach
    public void init() {
    	this.repo.deleteAll();
        this.testUser = new User("White User");
        this.testUserWithId = this.repo.save(this.testUser);
    }

    @Test
    public void createUserTest() {
        assertThat(this.mapToDTO(this.testUserWithId))
        .isEqualTo(this.service.create(testUser));
    }

 
    @Test
    void ReadByIdTest() {
        assertThat(this.service.read(this.testUserWithId.getId()))
        .isEqualTo(this.mapToDTO(this.testUserWithId));
    }

    @Test
    void ReadAllUsersTest() {
    	assertThat(this.service.read())
        .isEqualTo(Stream.of(this.mapToDTO(testUserWithId)).collect(Collectors.toList()));
    }
    @Test
    void testUpdate() {
    	UserDTO newUser = new UserDTO(null, "Tuesday");
    	UserDTO updatedUser = new UserDTO(this.testUserWithId.getId(), newUser.getUsername());

        assertThat(this.service.update(newUser, this.testUserWithId.getId()))
            .isEqualTo(updatedUser);
    }
    
    @Test
    void DeleteTest() {
        assertThat(this.service.delete(this.testUserWithId.getId())).isTrue();
    }

}
