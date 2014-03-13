package com.igtv.ui;

/**
 * This class manages the GUI elements related to the displayed guitar Tablature
 * representation of a score.
 */
public class Tablature {
  
  /**
   * Maps each frame that it contains to an integer representing the
   * onset of the frame
   */
  private Hashtable<Frame, Integer> frames;
  
  /**
   * Visually renders the UIElement corresponding to the tablature box
   * @pre !isNull(tab)
   * @pre !isNull(frames)
   * @post isRendered()
   * @param tab UIElement corresponding to the tablature box
   */
  private void render(UIElement tab) { ... }
  
  /* Constructor and other methods have been omitted */
}
