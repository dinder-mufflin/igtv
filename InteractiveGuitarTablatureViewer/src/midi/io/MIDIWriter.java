package midi.io;

import java.io.File;
import structures.Score;
import edu.columbia.ee.csmit.MidiKaraoke.write.SimpleMidiWriter;


@Deprecated //Not in the scope of work
public class MIDIWriter {

	/**
	 * 
	 * @param score
	 * @param fileName
	 * @param beatsPerMinute
	 * @return The relative path to the MIDI file or null if write is unsuccessful
	 */
	public static String writeScore(Score score, String fileName, double beatsPerMinute) {

		// pitch, velocity, channel, start (ticks), duration (ticks), start (seconds), duration (seconds), track
		double[][] notes = score.notes;

		double[] onset = new double[notes.length], duration = new double[notes.length];

		int[] channel = new int[notes.length], pitch = new int[notes.length], velocity = new int[notes.length];

		for (int i = 0; i < notes.length; i++) {

			pitch[i] = (int) notes[i][0];
			velocity[i] = (int) notes[i][1];
			channel[i] = (int) notes[i][2];
			onset[i] = notes[i][3] / 120.;
			duration[i] = notes[i][4] / 120.;

		}

		int resolution = 200;

		int microsecondsPerMinute = (int) (1e6 * 60);

		int microsecondsPerBeat = (int) (microsecondsPerMinute / beatsPerMinute);

		int[] timeSignature = new int[] { 4, 2, 1, 8 };

		String path = null;

		try {

			path = "output";

			// Make sure output folder exists
			createDirectory(path);

			path += "/" + fileName;

			SimpleMidiWriter.write(path, onset, duration, channel, pitch, velocity, microsecondsPerBeat, resolution,
					timeSignature);
		} catch (Exception e) {
			return null;
		}

		return path;

	}

	/**
	 * If a folder is not found, it will be created
	 * 
	 * @param directory
	 */
	private static void createDirectory(String directory) {
		File theDir = new File(directory);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();

			if (result) {
				// Unsuccessful
			}
		}
	}

}
