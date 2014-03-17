package com.igtv.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * Plays and stops any
 */
public class MidiPlayer {

  private Sequencer sequencer;

  public boolean load(File file) {

    try {
      Sequence sequence = MidiSystem.getSequence(file);

      // Create a sequencer for the sequence
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.setSequence(sequence);

      return true;

    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }


    return false;
  }

  /**
   * Plays the specified midi file beginning at a given onset
   * 
   * @pre onset <= maxOnset()
   * @pre !isPlaying()
   * @post isPlaying()
   * @param onset Onset where audio should begin playing.
   * @return Whether or not the play command was successfully executed
   */
  public boolean play() {

    if (sequencer == null) {
      return false;
    } else {

      // Start playing
      sequencer.start();
      return true;
    }
  }

  /**
   * Stops any audio that is playing.
   * 
   * @pre isPlaying()
   * @post !isPlaying()
   * @param onset Onset where audio should begin playing.
   * @return Whether or not the play command was successfully executed
   */
  public void stop() {
    sequencer.stop();
  }

}
