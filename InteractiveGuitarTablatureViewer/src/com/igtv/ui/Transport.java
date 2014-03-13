package com.igtv.ui;

/**
 * Represents an audio transport (i.e. the media controls)
 * Reponds to user actions on the rendered transport and forwards them
 * to the MidiPlayer object.
 */
public class Transport {

  /**
   * Executes when a user presses play on the audio transport.
   * @pre isFileLoaded()
   * @pre !isPlaying()
   * @post isPlaying()
   * @return
   */
  public boolean play() { ... }
  
  /**
   * Executes when a user presses pause on the audio transport.
   * @pre isFileLoaded()
   * @pre isPlaying()
   * @post !isPlaying()
   * @return
   */
  public boolean pause() { ... }
  
  /**
   * Renders the audio transport visually to the user.
   * @pre isFileLoaded()
   * @post isRendered()
   * @return
   */
  public boolean render() { ... }
  
  /* Constructor and other methods have been omitted */
  
}
