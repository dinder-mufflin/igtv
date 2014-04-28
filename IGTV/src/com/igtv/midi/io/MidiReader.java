package com.igtv.midi.io;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import com.igtv.structures.Score;

import java.io.File;

/**
 * Used for reading files encoded using the MIDI protocol.
 * 
 * @see {@link Score}
 */
public class MidiReader {

  /**
   * Returns the notes from a MIDI file as a two-dimensional double array. Columns are pitch,
   * velocity, channel, start (ticks), duration (ticks), start (seconds), duration (seconds), and
   * track
   * 
   * @pre isValidPath(midiFilePath)
   * @pre hasReadAccessToPath(midiFilePath)
   * @pre isValidMidi(midiFilePath)
   * 
   * @post {@link Score} pulled from the MIDI file
   * 
   * @param midiFilePath Location of the MIDI file
   * @param sortNotes Whether notes should be returned as sorted.
   * @return Score object that holds the MIDI data
   */
  public static Score readScore(String midiFilePath) {

    if (!hasReadAccessToPath(midiFilePath)) {
      return null;
    }

    // Create the file and pull from the passed file path
    File midiFile = new File(midiFilePath);

    // Test if the path exists
    if (!midiFile.exists()) {
      // The file does not exist. Return false.
      return null;
    }

    try {
      // Pull the MIDI sequence for the file
      Sequence seq = MidiSystem.getSequence(midiFile);

      // Return the data as a Score object
      return new Score(seq);
    } catch (Exception e) {

      // An error occurred
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Returns whether or not the program has access to the file at the given file path.
   * 
   * @pre isValidPath(midiFilePath)
   * 
   * @param midiFilePath
   * @return Read access at the file path
   */
  private static boolean hasReadAccessToPath(String midiFilePath) {

    // Ensure the path is valid
    if (!isValidPath(midiFilePath)) {
      // Path is invalid
      return false;
    }

    // Creates a File object using the path
    File midiFile = new File(midiFilePath);

    return midiFile.exists();
  }

  /**
   * Returns whether or not the path given is valid or not
   * 
   * @param midiFilePath
   * @return Whether the path is valid or not
   */
  private static boolean isValidPath(String midiFilePath) {
    if (midiFilePath == null || midiFilePath == "") {
      return false;
    } else {
      // Creates a File object using the path
      File midiFile = new File(midiFilePath);

      return midiFile.exists();
    }
  }
}
