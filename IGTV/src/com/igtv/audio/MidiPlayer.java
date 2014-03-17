package com.igtv.audio;

/**
 * Plays and stops any
 */
public class MidiPlayer {

  /**
   * Plays the specified midi file beginning at a given onset
   * 
   * @pre onset <= maxOnset()
   * @pre !isPlaying()
   * @post isPlaying()
   * @param onset Onset where audio should begin playing.
   * @return Whether or not the play command was successfully executed
   */
  public boolean play(int onset) {
    // TODO: Fill this method in
    return false;
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
    // TODO: Fill this method in
  }

}
