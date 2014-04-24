package com.igtv.audio;

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
  private boolean isPlaying = false;

  public boolean load(Sequence seq) {

    if (isPlaying()) {
      stop();
    }

    try {
      // Create a sequencer for the sequence
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.setSequence(seq);

      return true;

    } catch (InvalidMidiDataException e) {
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
      isPlaying = true;
      return true;
    }
  }

  /**
   * Seeks to a specified onset
   * 
   * @param onsetInTicks
   */
  public void seek(long onsetInTicks) {
    if (sequencer == null) {
      // Do nothing
    } else {
      sequencer.setTickPosition(onsetInTicks);
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
    if (isPlaying()) {
      sequencer.stop();
      isPlaying = false;
    }
  }
  
  public long getTickPosition() {
    return sequencer.getTickPosition();
  }

  /**
   * 
   * @return
   */
  public boolean isPlaying() {
    return isPlaying;
  }

}
