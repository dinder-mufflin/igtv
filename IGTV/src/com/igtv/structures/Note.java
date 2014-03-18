package com.igtv.structures;

/**
 * DTO representation of a note. Performs no logic apart from its construction
 */
public class Note {

  /**
   * Onset of the note (in ticks)
   */
  private int onsetInTicks;

  /**
   * Duration of the note (in ticks)
   */
  private int durationInTicks;

  /**
   * MIDI pitch (60 is Middle C, 61 is C#, etc.)
   */
  private int pitch;

  /**
   * Channel of the note from the imported MIDI file
   */
  private int channel;

  /**
   * Track of the note from the imported MIDI file
   */
  private int track;

  /* Getters, Setters, and Contructor have been omitted */

}
