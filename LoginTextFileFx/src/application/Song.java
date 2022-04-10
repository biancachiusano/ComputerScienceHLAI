package application;


import javafx.beans.property.SimpleStringProperty;



public class Song {
	
	private SimpleStringProperty songName;
	private SimpleStringProperty songArtist;
	private SimpleStringProperty songGenre;
	private SimpleStringProperty songMood;
	private SimpleStringProperty songEnergy;
	private int id;
	private int moodLevel;
	private int energyLevel;
	

	public Song() {
		
	}
	
	public Song(String songName, String songArtist, String songMood, String songEnergy, String songGenre, int moodLevel, int energyLevel) {
		this.songName = new SimpleStringProperty(songName);
		this.songArtist = new SimpleStringProperty(songArtist);
		this.songMood = new SimpleStringProperty(songMood);
		this.songEnergy = new SimpleStringProperty(songEnergy);
		this.songGenre = new SimpleStringProperty(songGenre);
		this.moodLevel = moodLevel;
		this.energyLevel = energyLevel;
		
	}
	
	public Song(String songName, String songArtist, String songMood, String songEnergy) {
		this.songName = new SimpleStringProperty(songName);
		this.songArtist = new SimpleStringProperty(songArtist);
		this.songMood = new SimpleStringProperty(songMood);
		this.songEnergy = new SimpleStringProperty(songEnergy);
		
	}
	
	public int getMoodLevel() {
		return moodLevel;
	}
	
	public void setMoodLevel(int moodLevel) {
		this.moodLevel = moodLevel;
	}
	
	public int getEnergyLevel() {
		return energyLevel;
	}
	
	public void setEnergyLevel(int energyLevel) {
		this.energyLevel = energyLevel;
	}

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Song(String songName, String songArtist) {
		this.songName = new SimpleStringProperty(songName);
		this.songArtist = new SimpleStringProperty(songArtist);
	}
	
	public String getSongName() {
		return songName.get();
	}
	
	public void setSongName(String songName) {
		this.songName = new SimpleStringProperty(songName);
	}
	
	public String getSongArtist() {
		return songArtist.get();
	}
	
	public void setSongArtist(String songArtist) {
		this.songArtist = new SimpleStringProperty(songArtist);
	}
	
	public String getSongMood() {
		return songMood.get();
	}
	
	public void setSongMood(String songMood) {
		this.songMood = new SimpleStringProperty(songMood);
	}
	
	public String getSongEnergy() {
		return songEnergy.get();
	}
	
	public void setSongEnergy(String songEnergy) {
		this.songEnergy = new SimpleStringProperty(songEnergy);
	}
	
	public String getSongGenre() {
		return songGenre.get();
	}
	
	public void setSongGenre(String songGenre) {
		this.songGenre = new SimpleStringProperty(songGenre);
	}
}
