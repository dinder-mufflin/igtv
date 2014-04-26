package com.igtv.audio;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * Acts as a basic driver for playing MIDI audio for the user. Relies on Java's {@link Sequence}
 * class for play/stop functionality.
 */
public class MidiPlayer {

  private Sequencer sequencer;
  private boolean isPlaying = false;

  /**
   * Loads a MIDI sequence into the player using {@link Sequencer#setSequence(Sequence)}
   * 
   * @param sequence
   * @return
   */
  public boolean load(Sequence sequence) {
    // Validate that the input is not null
    if (sequence == null) {
      return false;
    }

    // Stop any existing playback
    if (isPlaying()) {
      stop();
    }

    // Attempt to load media using Java's MidiSystem class
    try {
      // Create a sequencer for the sequence
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException e) {
      // Load failed
      e.printStackTrace();
      return false;
    } catch (MidiUnavailableException e) {
      // Load failed
      e.printStackTrace();
      return false;
    }

    // Load was successful. Return true.
    return true;
  }

  /**
   * Plays the specified MIDI file beginning at a given onset
   * 
   * @return Boolean representing whether or not the play command was successfully executed
   */
  public boolean play() {
    // Ensure that load() has already been called
    if (isLoaded()) {
      // Load was not called before calling play
      return false;
    }

    // Start playing
    sequencer.start();
    isPlaying = true;
    return true;
  }

  /**
   * Returns whether or not {@link #load(Sequence)} was not already called.
   * 
   * This is found by checking if the {@link #sequencer} object is null or not.
   * 
   * @return Boolean representing whether or not {@link #load(Sequence)} was not already called.
   */
  public boolean isLoaded() {
    return (sequencer != null);
  }

  /**
   * Seeks to a specified onset (in MIDI ticks).
   * 
   * This may be called during playback or while stopped. The only requirement is that a track is
   * required to have been loaded before being called.
   * 
   * @param onsetInTicks The desired onset (measured in MIDI ticks)
   * @return Boolean representing whether the seek was successful.
   */
  public boolean seek(long onsetInTicks) {
    if (!isLoaded()) {
      return false;
    }

    sequencer.setTickPosition(onsetInTicks);
    return true;
  }

  /**
   * Stops any audio that is playing.
   * 
   * @param onset Onset where audio should begin playing.
   * @return Boolean representing whether or not the play command was successfully executed
   */
  public void stop() {
    if (isPlaying()) {
      sequencer.stop();
      isPlaying = false;
    }
  }

  /**
   * Returns the position (in ticks) of playback. Helpful for implementing pause functionality.
   * 
   * @return The position (measured in ticks) of playback
   */
  public long getTickPosition() {
    return sequencer.getTickPosition();
  }

  /**
   * Returns whether or not MIDI is being played by this object.
   * 
   * @return Boolean representing whether or not MIDI is being played by this object.
   */
  public boolean isPlaying() {
    return isPlaying;
  }

}
