package application;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;



public class CsvReaderWriter extends ProfileController {
	
	
	
	public static void writeCsv(String filePath) {
		
		
		
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath);
			
			fileWriter.append("Id, Song Name, Song Artist, Song Genre, Song Mood, Song Energy, Song Mood Level, Song Energy Level \n");
			for(Song u: songs) {
				fileWriter.append(String.valueOf(u.getId()));
				fileWriter.append(",");
				fileWriter.append(u.getSongName());
				fileWriter.append(",");
				fileWriter.append(u.getSongArtist());
				fileWriter.append(",");
				fileWriter.append(u.getSongGenre());
				fileWriter.append(",");
				fileWriter.append(u.getSongMood());
				fileWriter.append(",");
				fileWriter.append(u.getSongEnergy());
				fileWriter.append(",");
				fileWriter.append(String.valueOf(u.getMoodLevel()));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(u.getEnergyLevel()));
				fileWriter.append("\n");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void readCsv(String filePath) {
		
		BufferedReader reader = null;
		
		try {
			
			
			String line = "";
			reader = new BufferedReader(new FileReader(filePath));
			reader.readLine();
			
			while((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				
				if(fields.length > 0) {
					Song song = new Song();
					song.setId(Integer.parseInt(fields[0]));
					song.setSongName(fields[1]);
					song.setSongArtist(fields[2]);
					song.setSongGenre(fields[3]);
					song.setSongMood(fields[4]);
					song.setSongEnergy(fields[5]);
					song.setMoodLevel(Integer.parseInt(fields[6]));
					song.setEnergyLevel(Integer.parseInt(fields[7]));
					
					songs.add(song);
				}
			}
			
	
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
}
