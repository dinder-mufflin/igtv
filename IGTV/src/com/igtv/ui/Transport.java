package com.igtv.ui;

/**
 * Represents an audio transport (i.e. the media controls) Reponds to user actions on the rendered
 * transport and forwards them to the MidiPlayer object.
 */
public class Transport {

  /**
   * Executes when a user presses play on the audio transport.
   * 
   * @pre isFileLoaded()
   * @pre !isPlaying()
   * @post isPlaying()
   * @return
   */
  public boolean play() {
    // TODO: Fill this method in
    return false;
  }

  /**
   * Executes when a user presses pause on the audio transport.
   * 
   * @pre isFileLoaded()
   * @pre isPlaying()
   * @post !isPlaying()
   * @return
   */
  public boolean pause() {
    // TODO: Fill this method in
    return false;
  }

  /**
   * Renders the audio transport visually to the user.
   * 
   * @pre isFileLoaded()
   * @post isRendered()
   * @return
   */
  public boolean render() {
    // TODO: Fill this method in
    return false;
  }

  /* Constructor and other methods have been omitted */

}
