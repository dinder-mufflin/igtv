package midi.io;

import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRoll;
import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRollViewParser;
import structures.Score;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;

public class MIDIReader {

	/**
	 * Returns the notes from a MIDI file as a two-dimensional double array.
	 * Columns are pitch, velocity, channel, start (ticks), duration (ticks),
	 * start (seconds), duration (seconds), and track
	 * 
	 * @param fileLocation Location of the MIDI file
	 * @param sortNotes Whether notes should be returned as sorted.
	 * @return
	 */
	public static Score readScore(String fileLocation) {

		File midiFile = new File(fileLocation);

		if (!midiFile.exists()) {
			return null;
		}

		try {

			Sequence seq = MidiSystem.getSequence(midiFile);

			int ticksPerQuarterNotes = seq.getResolution();

			PianoRoll roll = PianoRollViewParser.parse(seq, false);

			// pitch, velocity, channel, onset (ticks), duration (ticks), onset
			// (seconds), duration (seconds), track
			double[][] notes = roll.getNotesDoubles();

			return new Score(notes, ticksPerQuarterNotes);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}

}
