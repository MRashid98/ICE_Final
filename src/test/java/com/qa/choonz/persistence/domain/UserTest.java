package com.qa.choonz.persistence.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
	
	User testUser;
	User testUser2;
	final Long id = 1l;
	final String username = "OJ";
	final String password = "password";
	final String name = "myname";
	List<Playlist> playlists;
	
	private static int activeTest = 1;
	private static StringBuilder sBuilder = new StringBuilder();
	private static String div = "=============================================\n";
	
	@BeforeEach
	void init() {
		this.playlists = new ArrayList<Playlist>();
		this.testUser = new User(
				this.id, 
				this.username, 
				this.password,
				this.name,
				this.playlists);
		
		this.testUser2 = new User(
				this.id, 
				this.username, 
				this.password,
				this.name,
				this.playlists);
		
		// Start of console test format
		sBuilder.setLength(0);
		sBuilder
		.append("\tTest ").append(activeTest).append("\n")
		.append(div);
		
		System.out.println(sBuilder.toString());
		activeTest++;
		// End of console test format
	}
	

	@Test
	void noArguConsTest() {

		User newGenre = new User();			
		assertThat(newGenre instanceof User);
	}
	
	@Test
	void oneArguConsTest() {

		User newGenre = new User( 
				this.username);
		
		assertThat(newGenre instanceof User);
	}
	
	@Test
	void twoArguConsTest() {

		User newGenre = new User( 
				this.username,
				this.password);
		
		assertThat(newGenre instanceof User);
	}
	
	@Test
	void otherTwoArguConsTest() {

		User newGenre = new User( 
				this.id,
				this.password);
		
		assertThat(newGenre instanceof User);
	}
	@Test
	void threeArguConsTest() {

		User newGenre = new User( 
				this.username,
				this.password,
				this.name);
		
		assertThat(newGenre instanceof User);
	}
	
	@Test
	void AllArguConsTest() {

		User newGenre = new User(
				this.id, 
				this.username, 
				this.password,
				this.name,
				this.playlists);
		
		assertThat(newGenre instanceof User);
	}
	
	@Test
    public void equalsWithNull() {
        assertFalse(testUser.equals(null));
    }
	
	@Test
    public void equalsWithDifferentObject() {
        assertFalse(testUser.equals(new Object()));
    }
	
	@Test
	public void checkEquality() {
	     assertTrue(testUser.equals(testUser));
	}
	
	@Test
    public void checkEqualityBetweenDifferentObjects() {
        assertTrue(testUser.equals(testUser2));
    }
	
	 @Test
	    public void otherIdDifferent() {
	        testUser2.setId(2L);
	        assertFalse(testUser.equals(testUser2));
	}
	
	@Test
	void GetIdTest() {
		assertThat(this.testUser.getId() == this.id);
	}
	
	@Test
	void SetIdTest() {
		Long newId = 2l;
		this.testUser.setId(newId);		
		assertThat(this.testUser.getId() == newId);
	}
	
	@Test
	void GetUsernameTest() {
		assertThat(this.testUser.getUsername().equals(this.username));
	}
	
	@Test
	void SetUsernameTest() {
		String newUsername = "Funk";
		this.testUser.setUsername(newUsername);
		assertThat(this.testUser.getUsername().equals(newUsername));
	}
	
	@Test
	void GetPasswordTest() {
		assertThat(this.testUser.getPassword().equals(this.username));
	}
	
	@Test
	void SetPasswordTest() {
		String newPass = "Funky";
		this.testUser.setPassword(newPass);
		assertThat(this.testUser.getPassword().equals(newPass));
	}
	@Test
	void GetNameTest() {
		assertThat(this.testUser.getName().equals(this.name));
	}
	
	@Test
	void SetUserNameTest() {
		String newName = "Funk";
		this.testUser.setName(newName);
		assertThat(this.testUser.getUsername().equals(newName));
	}
	
	
	@Test
	void GetPlaylistsTest() {
		assertThat(this.testUser.getPlaylists() == this.playlists);
	}
	
	@Test
	void SetAlbumsTest() {
		Playlist newPass = new Playlist();
		List<Playlist> newPlaylists = new ArrayList<Playlist>();
		newPlaylists.add(newPass);
		this.testUser.setPlaylists(newPlaylists);
		assertThat(this.testUser.getPlaylists() == newPlaylists);
	}
	
	
	@Test
	void HashcodeTest() {
		User pl1 = new User(id,username,password,name,playlists);
		User pl2 = new User(id,username,password,name,playlists);
		
		assertTrue(pl1.hashCode() == pl2.hashCode());
	}
	
	@Test
	void EqualsTest() {
		User emptyUser = new User();
		User fullUser = new User(
				this.id, 
				this.username, 
				this.password,
				this.name,
				this.playlists);
		
		
		assertThat(!this.testUser.equals(emptyUser));
		assertThat(this.testUser.equals(fullUser));		
	}

	@AfterEach
	void teardown() {
		this.testUser = null;
	}

}
