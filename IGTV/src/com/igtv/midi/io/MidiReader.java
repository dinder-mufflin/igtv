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
   * @return
   */
  public static Score readScore(String midiFilePath) {
    File midiFile = new File(midiFilePath);
    if (!midiFile.exists()) {
      return null;
    }
    
    try {
      Sequence seq = MidiSystem.getSequence(midiFile);
      return new Score(seq);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
