package application;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.shape.Circle;

public class PlaylistGeneratorController extends ProfileController implements Initializable {

	@FXML private Label playlistUserLabel;
	@FXML private ImageView playlistPicture;
	
	//Sliders
	@FXML private Slider moodSlider;
	@FXML private Label moodLabel;
	@FXML private Slider energySlider;
	@FXML private Label energyLabel;
	
	//CheckBoxes 
	@FXML private CheckBox popCheckBox;
	@FXML private CheckBox rapCheckBox;
	@FXML private CheckBox rockCheckBox;


	
	
	//Media Player
	@FXML private Button playButton;
	@FXML private Button stopButton;
	@FXML private Button pauseButton;
	
	@FXML private TableView<Song> playlistTableView;
	@FXML private TableColumn<Song, String> playlistSongNameColumn;
	@FXML private TableColumn<Song, String> playlistSongArtistColumn;
	List<String> lstFile;
	
	private Person playlistPerson;
	//private Song playlistSong;
	
	public void initData(Person person) {
			
			
			playlistPerson = person;
			playlistUserLabel.setText(playlistPerson.name);
			Image loadPlaylistImage = new Image(playlistPerson.profilePicture);
			playlistPicture.setImage(loadPlaylistImage);
			
		}
	

	
	
	
	//Button to generate  a Playlist using an observable list 
	public void generatePlaylistButtonPushed(ActionEvent event) {
		
		

		FilteredList<Song> filteredData = new FilteredList<>(songs);
		playlistTableView.setItems(filteredData);
		
		
		
		Predicate<Song> containsPop = song -> (song.getSongGenre().contains(popCheckBox.getText()))&&(popCheckBox.isSelected() == true);
		Predicate<Song> containsRap = song -> (song.getSongGenre().contains(rapCheckBox.getText()))&&(rapCheckBox.isSelected() == true);
		Predicate<Song> containsRock = song -> (song.getSongGenre().contains(rockCheckBox.getText()))&&(rockCheckBox.isSelected() == true);
		
		//PREDICATES FOR SAD AND JOY
		Predicate<Song> containsSad = song ->(song.getSongMood().contains("Sad")&&(moodSlider.getValue() == 0));
		
		Predicate<Song> containsSadUntil25 = song ->((song.getSongMood().contains("Sad")&&(song.getMoodLevel() < 100)&&(song.getMoodLevel() >= 75))&&(moodSlider.getValue() <= 25)
												&&(moodSlider.getValue() > 0)||(song.getSongMood().contains("Joy"))&&(song.getMoodLevel() <= 25)&&(song.getMoodLevel() > 0)
												&&(moodSlider.getValue() > 0)&&(moodSlider.getValue()<= 25));
		
		
		Predicate<Song> containsSadUntil50 = song ->((song.getSongMood().contains("Sad")&&(song.getMoodLevel() < 75)&&(song.getMoodLevel() > 50))&&(moodSlider.getValue() < 50)
												&&(moodSlider.getValue() > 25)||(song.getSongMood().contains("Joy"))&&(song.getMoodLevel() < 50)&&(song.getMoodLevel() > 25)
												&&(moodSlider.getValue() > 25)&&(moodSlider.getValue()< 50));
		
		
		Predicate<Song> containsAllMoods = song ->(moodSlider.getValue() == 50);
		
		Predicate<Song> containsJoyUntil50 = song ->((song.getSongMood().contains("Sad")&&(song.getMoodLevel() < 50)&&(song.getMoodLevel() > 25))&&(moodSlider.getValue() < 75)
												&&(moodSlider.getValue() > 50)||(song.getSongMood().contains("Joy"))&&(song.getMoodLevel() < 75)&&(song.getMoodLevel() > 50)
												&&(moodSlider.getValue() > 50)&&(moodSlider.getValue()< 75));
		
		Predicate<Song> containsJoyUntil25 = song ->((song.getSongMood().contains("Sad")&&(song.getMoodLevel() <= 25)&&(song.getMoodLevel() > 0))&&(moodSlider.getValue() < 100)
												&&(moodSlider.getValue() >= 75)||(song.getSongMood().contains("Joy"))&&(song.getMoodLevel() < 100)&&(song.getMoodLevel() >= 75)
												&&(moodSlider.getValue() >= 75)&&(moodSlider.getValue()< 100));
		
		
		
		Predicate<Song> containsJoy = song ->(song.getSongMood().contains("Joy")&&(moodSlider.getValue() == 100));
		
		
		
		//PREDICATES FOR TIRED AND ENERGETIC
		Predicate<Song> containsTired = song ->(song.getSongEnergy().contains("Tired")&&(energySlider.getValue() == 0));
		
		
		Predicate<Song> containsTiredUntil25 = song ->((song.getSongEnergy().contains("Tired")&&(song.getEnergyLevel() < 100)&&(song.getEnergyLevel() >= 75))&&(energySlider.getValue() <= 25)
												&&(energySlider.getValue() > 0)||(song.getSongEnergy().contains("Energetic"))&&(song.getEnergyLevel() <= 25)&&(song.getEnergyLevel() > 0)
												&&(energySlider.getValue() > 0)&&(energySlider.getValue()<= 25));
										
		Predicate<Song> containsTiredUntil50 = song ->((song.getSongEnergy().contains("Tired")&&(song.getEnergyLevel() < 75)&&(song.getEnergyLevel() > 50))&&(energySlider.getValue() < 50)
												&&(energySlider.getValue() > 25)||(song.getSongEnergy().contains("Energetic"))&&(song.getEnergyLevel() < 50)&&(song.getEnergyLevel() > 25)
												&&(energySlider.getValue() > 25)&&(energySlider.getValue()< 50));
		
		Predicate<Song> containsAllEnergy = song ->(energySlider.getValue() == 50);
		
		
		Predicate<Song> containsEnergeticUntil50 = song ->((song.getSongEnergy().contains("Tired")&&(song.getEnergyLevel() < 50)&&(song.getEnergyLevel() > 25))&&(energySlider.getValue() < 75)
												&&(energySlider.getValue() > 50)||(song.getSongEnergy().contains("Energetic"))&&(song.getEnergyLevel() < 75)&&(song.getEnergyLevel() > 50)
												&&(energySlider.getValue() > 50)&&(energySlider.getValue() < 75));
		
		Predicate<Song> containsEnergeticUntil25 = song ->((song.getSongEnergy().contains("Tired")&&(song.getEnergyLevel() <= 25)&&(song.getEnergyLevel() > 0))&&(energySlider.getValue() < 100)
												&&(energySlider.getValue() >= 75)||(song.getSongEnergy().contains("Energetic"))&&(song.getEnergyLevel() < 100)&&(song.getEnergyLevel() >= 75)
												&&(energySlider.getValue() >= 75)&&(energySlider.getValue() < 100));
			
		
		
		Predicate<Song> containsEnergetic = song ->(song.getSongEnergy().contains("Energetic")&&(energySlider.getValue() == 100));
	
		
		
		
		
		Predicate<Song> filter = ((containsPop.or(containsRock).or(containsRap)).and((containsSad).or(containsSadUntil25).or(containsSadUntil50).or(containsAllMoods).or(containsJoyUntil50).or(containsJoyUntil25).or(containsJoy)).and((containsTired).or(containsTiredUntil25).or(containsTiredUntil50).or(containsAllEnergy).or(containsEnergeticUntil50).or(containsEnergeticUntil25).or(containsEnergetic)));
		
		filteredData.setPredicate(filter);
		
		
		
	
			
		
		
		this.playButton.setDisable(false);
		
	}
	
	MediaPlayer mediaplayer;
	
	
	
	//Button to play the song 
	public void playButtonPushed(ActionEvent event) {
		
		
		
		//FILE LOADER 
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Music Files", lstFile));
		File f = fc.showOpenDialog(null);
		String absolute = f.getAbsolutePath();
		if(f != null) {
			Media fileChooserMedia = new Media("file:///"+absolute);
			mediaplayer = new MediaPlayer(fileChooserMedia);
			mediaplayer.setAutoPlay(true);
			mediaplayer.setVolume(0.1);
			mediaplayer.play();
		}
		
	
	/*Song selectedSong = playlistTableView.getSelectionModel().getSelectedItem();
	String music = selectedSong.getSongName().replaceAll("\\p{Blank}", "%20") + ".m4a";
	URL beepURL = PlaylistGeneratorController.class.getResource(music);
	
	try {
		
		
		Media musicFile = new Media(beepURL.toURI().toString());
		mediaplayer = new MediaPlayer(musicFile);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setVolume(0.1);
		mediaplayer.play();
	
		
		this.stopButton.setDisable(false);
		this.pauseButton.setDisable(false);
		this.stopButton.setDisable(false);
		this.pauseButton.setDisable(false);
		this.playButton.setDisable(true);
		

		} catch (Exception e) {
			
			AlertBox.display("Song not Found", "The player is searching on my desktop\n"
					+ "if you want to play a song, please play Everglow "
					+ "as it has been added to the program");
			
		}*/
		
		
	}
	
	public void stopButtonPushed(ActionEvent event) {
		
		mediaplayer.stop();
		this.stopButton.setDisable(true);
		this.pauseButton.setDisable(true);
		this.playButton.setDisable(false);
	}
	
	public void pauseButtonPushed(ActionEvent event) {
		
		mediaplayer.pause();
		this.stopButton.setDisable(true);
		this.pauseButton.setDisable(true);
		this.playButton.setDisable(false);
	}
	
	
	
	
	
	public void editProfileButtonPushed(ActionEvent event) throws IOException {
		
	
		FXMLLoader editloader = new FXMLLoader();
		editloader.setLocation(getClass().getResource("/application/Profile.fxml"));
		Parent profileWindow = editloader.load();
		
		
		Scene newProfileScene = new Scene(profileWindow);
		  
		//Access the controller and call a method
		ProfileController controller = editloader.getController();
		controller.initData(playlistPerson);
		//get stage info
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(newProfileScene);
		window.show();
		
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		lstFile = new ArrayList<>();
		lstFile.add("*.m4a");
		lstFile.add("*.mp3");
		
		final Circle Clip = new Circle();
				Clip.setCenterX(75);
				Clip.setCenterY(70);
				Clip.setRadius(70);
		playlistPicture.setClip(Clip);
		
		final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
		playlistPicture.setOnMousePressed((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
			}
		});
		playlistPicture.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				double deltaX = event.getSceneX() - mouseAnchor.get().getX();
				double deltaY = event.getSceneY() - mouseAnchor.get().getY();
				playlistPicture.setLayoutX(playlistPicture.getLayoutX() + deltaX);
				playlistPicture.setLayoutY(playlistPicture.getLayoutY() + deltaY);
				Clip.setCenterX(Clip.getCenterX() - deltaX);
				Clip.setCenterY(Clip.getCenterY() - deltaY);
				mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
			}
		});
		
		
		
		
		
		playlistSongNameColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
		playlistSongArtistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("songArtist"));
		
		
		// Mood Slider
		
		this.moodSlider.setMin(0);
		this.moodSlider.setMax(100);
		this.moodSlider.setValue(50);
		
		this.moodSlider.setShowTickLabels(true);
		this.moodSlider.setShowTickMarks(true);
		this.moodSlider.setBlockIncrement(25);
		this.moodSlider.setSnapToTicks(true);
		
		//Add slider Listener to value property
		this.moodSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					
					public void changed(ObservableValue<? extends Number >
								observable, Number oldValue, Number newValue)
					{
						moodLabel.setText("Value " + newValue);
					}
					
					
				});
		
		
		//Energy Slider
		this.energySlider.setMin(0);
		this.energySlider.setMax(100);
		this.energySlider.setValue(50);
		this.energySlider.setSnapToTicks(true);
		
		this.energySlider.setShowTickLabels(true);
		this.energySlider.setShowTickMarks(true);
		this.energySlider.setBlockIncrement(25);
		
		//Add slider Listener to value property
		this.energySlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					
					public void changed(ObservableValue<? extends Number >
								observable, Number oldValue, Number newValue)
					{
						energyLabel.setText("Value " + newValue);
					}
					
					
				});
		
		this.playButton.setDisable(true);
		this.stopButton.setDisable(true);
		this.pauseButton.setDisable(true);
		
		
		
	}
	
}
