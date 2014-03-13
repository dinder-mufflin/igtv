package com.igtv.midi.io;

import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRoll;
import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRollViewParser;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import com.igtv.dto.Score;

import java.io.File;

/**
 * Class used for importing a file that stores notes using the MIDI protocol
 */
public class MidiReader {

  /**
   * Relative path to the midi file
   */
  private String path;

  /**
   * Returns the notes from a MIDI file as a two-dimensional double array. Columns are pitch,
   * velocity, channel, start (ticks), duration (ticks), start (seconds), duration (seconds), and
   * track
   * 
   * @pre exists(path)
   * @post isSorted(output)
   * @return A score representing the notes in the midi file. The return value will be null if the
   *         import was unsuccessful.
   */
  public Score read() { ... }
  /* Constructor and select methods have been omitted */

}
