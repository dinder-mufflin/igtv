package com.igtv.dto;

import com.igtv.dto.Note;

/**
 * Representation as a frame (as outlined in the dictionary of terms)
 * Contains a listing of all notes that occur at a particular onset.
 * Each note will correspond to a single frame.
 * This class is used for construction of the Tablature object.
 */
public class Frame {
  
  /**
   * All notes occurring at the onset in the field "onset"
   */
  private List<Note> notes;
  
  /**
   * The onset where the frame begins
   */
  private int onsetInTicks;
  
  /**
   * Returns the duration of the Frame.
   * Duration is the maximum of the duration of all notes in the frame.
   * @pre !isNull(notes)
   * @post duration() = maxDuration(notes)
   * @return The duration of the frame
   */
  public int getDuration() { ... }
  
  /* Constructor and certain methods have been omitted */
}
