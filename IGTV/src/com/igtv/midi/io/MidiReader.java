package com.igtv.midi.io;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import com.igtv.structures.Score;

import java.io.File;

public class MidiReader {

  /**
   * Returns the notes from a MIDI file as a two-dimensional double array. Columns are pitch,
   * velocity, channel, start (ticks), duration (ticks), start (seconds), duration (seconds), and
   * track
   * 
   * @param midiFilePath Location of the MIDI file
   * @param sortNotes Whether notes should be returned as sorted.
   * @return Score object that holds the MIDI data
   */
  public static Score readScore(String midiFilePath) {
    // Create the file and pull from the passed file path
    File midiFile = new File(midiFilePath);

    // Test if the path exists, if not returns null so no system crashes
    if (!midiFile.exists()) {
      return null;
    }

    try {
      // Pull the MIDI sequence for the file
      Sequence seq = MidiSystem.getSequence(midiFile);
      return new Score(seq);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
