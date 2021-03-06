package com.qa.choonz.Cuke;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PlaylistAndUserPageTest {
	
	WebDriver driver; 
	
	WebDriverWait wait;
	Actions action;
	
	@Before("@tagPL")
	public void init() {
		System.setProperty("webdriver.edge.driver","C:\\Users\\taydz\\Desktop\\Choonz-Starter-master\\src\\test\\resources\\msedgedriver.exe");
	    driver = new EdgeDriver(); 
	    wait = new WebDriverWait(driver,15);
	    action = new Actions(driver);
	}
	@Before("@tagUser")
	public void initUser() {
		System.setProperty("webdriver.edge.driver","C:\\Users\\taydz\\Desktop\\Choonz-Starter-master\\src\\test\\resources\\msedgedriver.exe");
	    driver = new EdgeDriver(); 
	    wait = new WebDriverWait(driver,15);
	    action = new Actions(driver);
	}
	@Given("I am logged in and on Playlist {int} page")
	public void logged_in_and_on_playlist_page(int PlID) throws Throwable {
	    driver.get("http://127.0.0.1:5500/src/main/resources/static/html/Playlist.html?user=1&playlists="+PlID);
	}
	
	@Given("I am logged in and on USER page")
	public void logged_in_and_on_user_page() throws Throwable {
	    driver.get("http://127.0.0.1:5500/src/main/resources/static/html/User.html?user=1");
	}
	
	@When("I click to view the Track with id {int} for Playlist {int}")
	public void select_track_from_pl(int TrackID,int PlID) throws Throwable {
		WebElement PlRow = driver.findElement(By.id("PL"+PlID+"PlaylistRow"+TrackID));
		wait.until(ExpectedConditions.visibilityOf(PlRow));
		action.moveToElement(PlRow);
	    WebElement TrackLink = driver.findElement(By.id("PL"+PlID+"ViewTrackButton"+TrackID));
	    wait.until(ExpectedConditions.visibilityOf(TrackLink));
	    wait.until(ExpectedConditions.elementToBeClickable(TrackLink));
	    TrackLink.click();
	}
	@When("I open the Playlist side bar and select Playlist {int}")
	public void select_playlist(int PlID) throws Throwable {
		WebElement openFilter = driver.findElement(By.id("FilterByBTN"));
		wait.until(ExpectedConditions.elementToBeClickable(openFilter));
		openFilter.click();
	    WebElement SelectLink = driver.findElement(By.id("Select_"+PlID));
	    wait.until(ExpectedConditions.elementToBeClickable(SelectLink));
	    SelectLink.click();
	}
	@When("I add Track {int} {string} to Playlist {int}")
	public void playlist_add_track(int TrackID,String TrackName,int PlID) throws Throwable{
		WebElement AddTrackBTN = driver.findElement(By.id("AddTrackButton"+PlID));
	    wait.until(ExpectedConditions.elementToBeClickable(AddTrackBTN));
	    AddTrackBTN.click();
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("AddTrackPLModal"))));
		driver.findElement(By.id("TrackAdd")).sendKeys(TrackID+". "+TrackName+" "+TrackID);
		driver.findElement(By.id("AddTrackPLSubmit")).click();
	}
	@When("I remove Track {int} from Playlist {int}")
	public void playlist_remove_track(int TrackID,int PlID) throws Throwable{
		WebElement PlRow = driver.findElement(By.id("PL"+PlID+"PlaylistRow"+TrackID));
		wait.until(ExpectedConditions.visibilityOf(PlRow));
		action.moveToElement(PlRow);
	    WebElement RemoveTrackBTN = driver.findElement(By.id("PL"+PlID+"RemoveTrackButton"+TrackID));
	    wait.until(ExpectedConditions.visibilityOf(RemoveTrackBTN));
	    wait.until(ExpectedConditions.elementToBeClickable(RemoveTrackBTN));
	    RemoveTrackBTN.click();
	}
	@When("I edit the Playlist {int} info")
	public void edit_playlist(int PlID) throws Throwable{
		WebElement EditPLBTN = driver.findElement(By.id("EditPLButton"+PlID));
	    wait.until(ExpectedConditions.elementToBeClickable(EditPLBTN));
	    EditPLBTN.click();
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("EditPLModal"))));
		WebElement newName = driver.findElement(By.id("EditPLName"));
		newName.clear();
		newName.sendKeys("TEST PL NAME");
		WebElement newDescription =driver.findElement(By.id("EditPLDescription"));
		newDescription.clear();
		newDescription.sendKeys("TEST PL Description");
		driver.findElement(By.id("EditPLSubmit")).click();
		
	}
	@When("I delete Playlist {int}")
	public void delete_playlist(int PlID) throws Throwable{
		WebElement DeletePLBTN = driver.findElement(By.id("DeletePLButton"+PlID));
	    wait.until(ExpectedConditions.elementToBeClickable(DeletePLBTN));
	    DeletePLBTN.click();
	}

	@When("I add a new Playlist")
	public void new_playlist() throws Throwable{
	    WebElement AddPLBTN = driver.findElement(By.id("addPLButton"));
	    wait.until(ExpectedConditions.visibilityOf(AddPLBTN));
	    AddPLBTN.click();
	    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("AddPLModal"))));
		driver.findElement(By.id("AddPLName")).sendKeys("TEST PL NAME");
		driver.findElement(By.id("AddPLDescription")).sendKeys("TEST PL Description");
		driver.findElement(By.id("AddPLSubmit")).click();

	}
	
	@Then("I will be on the Track page with track id {int}")
	public void assert_on_tracks_page(int id) throws Throwable {
		assertThat(driver.getCurrentUrl()).isEqualTo("http://127.0.0.1:5500/src/main/resources/static/html/Track.html?user=1&tracks="+id);
	}
	
	@Then("I will be on the Playlist page for {int} and be logged in")
	public void assert_logged_in_on_playlist_page(int id) throws Throwable {
		assertThat(driver.getCurrentUrl()).isEqualTo("http://127.0.0.1:5500/src/main/resources/static/html/Playlist.html?user=1&playlists="+id);
	}
	
	@Then("The Playlist {int} includes Track {int}")
	public void assert_playlist_includes_track(int PlaylistID,int TrackID) throws Throwable{
		Thread.sleep(500);
		WebElement PLRow = driver.findElement(By.id("PL"+PlaylistID+"PlaylistRow"+TrackID));
		wait.until(ExpectedConditions.visibilityOf(PLRow));
		action.moveToElement(PLRow);
		WebElement TrackName = driver.findElement(By.id("PL"+PlaylistID+"Trackname"+TrackID));
		wait.until(ExpectedConditions.visibilityOf(TrackName));
		assertThat(TrackName.getText()).isEqualTo("track "+TrackID);
	}
	@Then("The Track {int} can not be found on Playlist {int}")
	public void assert_track_removed_from_playlist(int TrackID,int PlaylistID) throws Throwable{
		Thread.sleep(500);		
		WebElement PLempty = driver.findElement(By.id("PLEmpty"+PlaylistID));
		wait.until(ExpectedConditions.visibilityOf(PLempty));
		assertThat(PLempty.getText()).isEqualTo("NO TRACKS");
	}
	@Then("Playlist {int} info has been changed")
	public void assert_playlist_info_changed(int PlaylistID) throws Throwable {
		Thread.sleep(500);
//		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/Playlist.html?user=1&playlists="+PlaylistID);
		WebElement PLName = driver.findElement(By.id("PLTitle"+PlaylistID));
		wait.until(ExpectedConditions.visibilityOf(PLName));
		assertThat(PLName.getText()).isEqualTo("Playlist: TEST PL NAME");
		WebElement PLDescription = driver.findElement(By.id("PLDescription"+PlaylistID));
		wait.until(ExpectedConditions.visibilityOf(PLDescription));
		assertThat(PLDescription.getText()).isEqualTo("Description: TEST PL Description");
	}
	@Then("Playlist {int} info has been changed USER")
	public void assert_playlist_info_changed_USER(int PlaylistID) throws Throwable {
		Thread.sleep(500);
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/Playlist.html?user=1&playlists="+PlaylistID);
		Thread.sleep(500);
		WebElement PLName = driver.findElement(By.id("PLTitle"+PlaylistID));
		wait.until(ExpectedConditions.visibilityOf(PLName));
		assertThat(PLName.getText()).isEqualTo("Playlist: TEST PL NAME");
		WebElement PLDescription = driver.findElement(By.id("PLDescription"+PlaylistID));
		wait.until(ExpectedConditions.visibilityOf(PLDescription));
		assertThat(PLDescription.getText()).isEqualTo("Description: TEST PL Description");
	}
	@Then("Playlist {int} is not found")
	public void assert_playlist_deleted(int PlaylistID) throws Throwable {
		Thread.sleep(500);
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/Playlist.html?user=1&playlists="+PlaylistID);
		Thread.sleep(500);
		WebElement PLName = driver.findElement(By.id("PLTitle"));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("PLTitle")));
		assertThat(PLName.getText()).isEqualTo("");
		WebElement PLDescription = driver.findElement(By.id("PLDescription"));
		assertThat(PLDescription.getText()).isEqualTo("");
	}
	@Then("My new Playlist is found")
	public void assert_playlist_added() throws Throwable {
		driver.get("http://127.0.0.1:5500/src/main/resources/static/html/Playlist.html?user=1&playlists=5");
		Thread.sleep(500);
		WebElement PLName = driver.findElement(By.id("PLTitle"+5));
		wait.until(ExpectedConditions.visibilityOf(PLName));
		assertThat(PLName.getText()).isEqualTo("Playlist: TEST PL NAME");
		WebElement PLDescription = driver.findElement(By.id("PLDescription"+5));
		wait.until(ExpectedConditions.visibilityOf(PLDescription));
		assertThat(PLDescription.getText()).isEqualTo("Description: TEST PL Description");
	}

	@After("@tagPL")
	public void quit() {
		driver.quit();
	}
	@After("@tagUser")
	public void quitUser() {
		driver.quit();
	}

}
