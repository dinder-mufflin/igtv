package com.igtv.dto;

/**
 * A Score is a structure that stores notes as an 8-tuple: (pitch, velocity, channel, start (ticks),
 * duration (ticks), start (seconds), duration (seconds), track)
 * 
 * Serves as a temporary DTO to transmit between the MidiReader and the ScoreAnalyzer.
 */
public class Score {

  /**
   * An 8xN double array representing note tuples.
   */
  private double[][] notes;

  /**
   * Number of ticks per one quarter note.
   */
  private double ticksPerQuarterNotes;

  /**
   * Instantiates a
   * 
   * @pre !isNull(notes)
   * @param notes An 8xN double array representing note tuples.
   * @param ticksPerQuarterNotes Number of ticks per one quarter note.
   */
  public Score(double[][] notes, double ticksPerQuarterNotes) { ... }
  
  /* Getters and setters have been omitted. */

}
