package com.igtv.structures;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Representation as a frame (as outlined in the dictionary of terms) Contains a listing of all
 * notes that occur at a particular onset. Each note will correspond to a single frame. This class
 * is used for construction of the Tablature object.
 */
public class Frame {

  private double onsetInTicks;

  // Assign one note to each string (when needed)
  public Integer[] guitarStringFrets = new Integer[6];
  public Long[] durations = new Long[6];

  /**
   * All notes occurring at the onset in the onset
   */
  HashSet<Note> notes = new HashSet<Note>();

  public Frame(double currentOnset) {
    this.onsetInTicks = currentOnset;
  }

  /**
   * Adds a note to the frame
   * 
   * @param n
   */
  public void addNote(Note n) {
    notes.add(n);
  }

  /**
   * Returns all notes in the frame
   * 
   * @return
   */
  public HashSet<Note> getNotes() {
    return notes;
  }

  /**
   * 
   * @return The onset where the frame begins
   */
  public double getOnsetInTicks() {
    return onsetInTicks;
  }

  public void print() {
    System.out.println("\nFRAME: " + onsetInTicks);
    Iterator<Note> i = notes.iterator();
    while (i.hasNext()) {
      Note curr = i.next();
      System.out.println("\t" + curr.getPitch());
    }
  }
}
