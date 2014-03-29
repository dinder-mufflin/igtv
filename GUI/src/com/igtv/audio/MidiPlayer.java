package com.igtv.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

/**
 * Plays and stops any
 */
public class MidiPlayer {
  
  private Sequencer sequencer;
  private boolean isPlaying = false;

  public boolean load(Sequence seq) {
    
    if(isPlaying) {
      stop();
    }

    try {
      //Sequence sequence = MidiSystem.getSequence(file);

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
   * Stops any audio that is playing.
   * 
   * @pre isPlaying()
   * @post !isPlaying()
   * @param onset Onset where audio should begin playing.
   * @return Whether or not the play command was successfully executed
   */
  public void stop() {
    sequencer.stop();
    isPlaying = false;
  }
  
  public boolean isPlaying() {
    return isPlaying;
  }

}
