package com.igtv.structures;

import java.util.HashSet;

/**
 * Representation as a frame (as outlined in the dictionary of terms) Contains a listing of all
 * notes that occur at a particular onset. Each note will correspond to a single frame. This class
 * is used for construction of the Tablature object.
 */
public class Frame {

  /**
   * Onset of all notes appearing at this frame
   */
  private double onsetInTicks;

  /**
   * Frets corresponding to pitch of each note (indexes correspond to guitar strings)
   */
  public Integer[] guitarStringFrets = new Integer[6];

  /**
   * Duration of each note (indexes correspond to guitar strings)
   */
  public Long[] durations = new Long[6];

  /**
   * All notes occurring at the onset in the onset
   */
  HashSet<Note> notes = new HashSet<Note>();

  /**
   * Constructor. Creates using a given onset (measured in ticks).
   * 
   * @param currentOnset
   */
  public Frame(double currentOnset) {
    this.onsetInTicks = currentOnset;
  }

  /**
   * Adds a note to the frame.
   * 
   * @param n
   */
  public void addNote(Note n) {
    notes.add(n);
  }

  /**
   * Returns all notes in the frame.
   * 
   * @return
   */
  public HashSet<Note> getNotes() {
    return notes;
  }

  /**
   * Returns the onset (in ticks) where all notes in the frame begin.
   * 
   * @return The onset where the frame begins
   */
  public double getOnsetInTicks() {
    return onsetInTicks;
  }
}
