package application;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ProfileController implements Initializable {
	
	@FXML private Label nameProfileLabel;
	@FXML private TextField passwordProfileTextField;
	@FXML private ImageView profilePhoto;
	@FXML private TextField ageTextField;
	@FXML private DatePicker birthdayDatePicker;
	@FXML private Label crossingFingersLabel;
	
	//These Items are for the RadioButtons
	@FXML private RadioButton popRadioButton;
	@FXML private RadioButton rapRadioButton;
	@FXML private RadioButton rockRadioButton;
	@FXML private RadioButton allSongsRadioButton;
	
	
	//Items to chose a profile picture
	List<String> lstFile;
	List<String> lstCSV;
	@FXML private Button profileImageFileChooser;
	
	//TextFields and Combo boxes to add Songs
	//List<String> sngFile;
	@FXML private TextField songNameTextField;
	@FXML private TextField songArtistTextField;
	@FXML private ComboBox<String> moodComboBox;
	@FXML private ComboBox<String> energyComboBox;
	@FXML private ComboBox<String> genreComboBox;
	
	@FXML private Slider moodLevelSlider;
	@FXML private Slider energyLevelSlider;
	
	
	/**
	 * These are the details for the Table
	 */
	
	@FXML private TableView<Song> tableView;
	@FXML private TableColumn<Song, String> songNameColumn;
	@FXML private TableColumn<Song, String> songArtistColumn;
	@FXML private TableColumn<Song, String> songMoodColumn;
	@FXML private TableColumn<Song, String> songEnergyColumn;
	//@FXML private ComboBox<String> iconComboBox;
	
	private Person profile;
	
	/**
	 * Method that accepts a person to initialize the profile
	 */
	public void initData(Person person) {
		
		profile = person;
		nameProfileLabel.setText(profile.name);
		passwordProfileTextField.setText(profile.password);
		ageTextField.setText(profile.age);
		
		try {
			Image loadImage = new Image(profile.profilePicture);
			profilePhoto.setImage(loadImage);
		} catch (Exception e) {
			Image defaultImage = new Image("/images/None.jpg");
			profilePhoto.setImage(defaultImage);
			profile.profilePicture = "/images/None.jpg";
		}
		
		
	}
	
	/**
	 * Method for the single file chooser to choose an Image
	 */
	
	//String absolute;
	public void singleFileChooser(ActionEvent event) {
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Image Files", lstFile));
		File f = fc.showOpenDialog(null);
		String absolute = f.getAbsolutePath();
		if(f != null) {
			Image fileChooserImage = new Image("file:///"+f.getAbsolutePath());
			profilePhoto.setImage(fileChooserImage);
			profile.profilePicture = "file:///"+absolute;
			
		}
	}
	
	//Generate age with date picker
	
	@FXML
	private void showAge() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int birthYear = (birthdayDatePicker.getValue().getYear());
		int age1 = year - birthYear;
		ageTextField.setText(Integer.toString(age1));
		
	}
	
	//Import Playlist Button File Chooser
	public void importPlaylistButtonPushed(ActionEvent event) {
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", lstCSV));
		File f = fc.showOpenDialog(null);
		String absolute = "file:///"+f.getAbsolutePath();
		File file = new File(absolute);
		//Write chosen CSV in User's CSV from program
		if(f != null) {
			
		}
	}
	
	
	

	
	
	public void saveProfileInformationButton(ActionEvent event) throws IOException {
		
		//Save a profile object information into BIN file
	
		
				profile.name = nameProfileLabel.getText().toString();
				profile.password = passwordProfileTextField.getText().toString();
				
				
				if(ageTextField.getText() == null || ageTextField.getText().trim().isEmpty()) {
					AlertBox.display("Alert", "Make sure that all fields are completed");
				} else {
					profile.age = ageTextField.getText().toString();
				
					String file = nameProfileLabel.getText().toString();
					String fileName = file + ".bin";
					try {
						ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
						os.writeObject(profile);
						os.close();
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}	
					
					String csvFile = nameProfileLabel.getText().toString();
					String csvFileName = csvFile + ".csv";
					
					//Write Tableview records in Csv (call the CsvReaderWriter method)
					CsvReaderWriter.writeCsv(csvFileName);
					
					FXMLLoader profileLoader = new FXMLLoader();
					profileLoader.setLocation(getClass().getResource("/application/PlaylistGenerator.fxml"));
					Parent playlistWindow = profileLoader.load();
					
					
					Scene playlistScene = new Scene(playlistWindow);
					  
					//Access the controller and call a method
					PlaylistGeneratorController controller = profileLoader.getController();
					controller.initData(profile);
					//get stage info
					Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
					window.setScene(playlistScene);
					window.show();
				
				
				}
				
		
	}
	
	
	public void moodComboAction() {
		
		this.moodLevelSlider.setDisable(false);
		
	}
	
	public void energyComboAction() {
		
		this.energyLevelSlider.setDisable(false);
		
	}
	
	public void indexExplained() {
		AlertBox.display("Help", "Please indicate how much you want to associate this\n"
				+ " song to your mood or your energy, this will be used\n"
				+ " to help us generate the best playlists for you");
	}
	
	
	
	static ObservableList<Song> songs = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		

		
		moodComboBox.getItems().addAll("Joy","Sad");
	
		
		energyComboBox.getItems().addAll("Tired","Energetic");
		
		
		genreComboBox.getItems().addAll("Pop","Rap","Rock");
		
		
		this.moodLevelSlider.setMin(0);
		this.moodLevelSlider.setMax(100);
		this.moodLevelSlider.setMajorTickUnit(100);
		this.moodLevelSlider.setShowTickLabels(true);
		this.moodLevelSlider.setShowTickMarks(true);
		
		this.energyLevelSlider.setMin(0);
		this.energyLevelSlider.setMax(100);
		this.energyLevelSlider.setMajorTickUnit(100);
		this.energyLevelSlider.setShowTickLabels(true);
		this.energyLevelSlider.setShowTickMarks(true);
		
		
		//Disable and enable Sliders
		
		this.moodLevelSlider.setDisable(true);
		this.energyLevelSlider.setDisable(true);
		
		lstFile = new ArrayList<>();
		lstFile.add("*.jpg");
		lstFile.add("*.png");
		
		lstCSV = new ArrayList<>();
		lstCSV.add("*.csv");
		
		
		
		//These Items are for configuring the RadioButtons
		ToggleGroup genreProfileToggleGroup = new ToggleGroup();
		this.popRadioButton.setToggleGroup(genreProfileToggleGroup);
		this.rapRadioButton.setToggleGroup(genreProfileToggleGroup);
		this.rockRadioButton.setToggleGroup(genreProfileToggleGroup);
		this.allSongsRadioButton.setToggleGroup(genreProfileToggleGroup);
		
		allSongsRadioButton.setSelected(true);
		
		//Add Listener 
		genreProfileToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() 
		
		{
			public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n)
			{
				RadioButton rb = (RadioButton)genreProfileToggleGroup.getSelectedToggle();
				
				if(rb != null) {
					String s = rb.getText();
					crossingFingersLabel.setText(s);	
				} 
			}
		});
		
		
		//Sets up columns in the table
		songNameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
		songArtistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songArtist"));
		songMoodColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songMood"));
		songEnergyColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songEnergy"));
		
		
		
		
		
		
		
		// Wrap the observable list in a filtered list (initially display all data)
		FilteredList<Song> filteredData = new FilteredList<>(songs, p -> true);
		
			crossingFingersLabel.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(song -> {
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}
					
					String lowerCaseFilter = newValue.toLowerCase();
					
					if(song.getSongGenre().toLowerCase().contains(lowerCaseFilter)) {
						return true;
					}
					return false;
					
				});
				
			});
			
			//Wrap in a SortedList
			SortedList<Song> sortedData = new SortedList<>(filteredData);
			
			//Bind the Sortedlist comparator to the Tableview comparator
			
			sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		
		
			tableView.setItems(sortedData);
			
			
			//This will allow me to select multiple songs
			tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	/**
	 * Add Song Method
	 */
	
	public void addNewSongButtonPushed() {
		
		
		try{
			Song newSong = new Song(songNameTextField.getText(),songArtistTextField.getText(),moodComboBox.getValue().toString(),energyComboBox.getValue().toString(), genreComboBox.getValue().toString(), (int) Math.round(moodLevelSlider.getValue()),(int) Math.round(energyLevelSlider.getValue()));
			songs.add(newSong);
			
			songNameTextField.clear();
			songArtistTextField.clear();
			moodComboBox.setValue(null);
			moodLevelSlider.setDisable(true);
			energyComboBox.setValue(null);
			energyLevelSlider.setDisable(true);
			genreComboBox.setValue(null);
			
		}catch (Exception e) {
			AlertBox.display("Alert", "Please complete all fields before adding a new song");
		}
		
		
		
	}
	
	
	/**
	 * Delete Song Method
	 */
	public void deleteSongsButtonPushed() {
		
		ObservableList<Song> selectedRows, allSongs;
		
		allSongs = songs;
		
		selectedRows = tableView.getSelectionModel().getSelectedItems();
		
		for(Song song: selectedRows) {
			
			allSongs.remove(song);
		}
	}
	
	
	
	
	
	
}