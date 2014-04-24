package com.igtv.structures;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Represents all notes beginning at one point in time.
 */
public class Frame {
  
  //TODO: Update values
  /**
   * Open pitch for each string
   */
  private final int[] basePitches = new int[]{ 0, 0, 0, 0, 0, 0 };
  

  /**
   * 
   * @param stringNo String number from the bottom string (0 corresponds to the low E string).
   * @param midiPitch
   * @return
   */
  private static int pitchToFret(int stringNo, int midiPitch) {
    switch (stringNo) {
      case 0:
        
    }
    return 9;
  }

  private double onsetInTicks;

  // Assign one note to each string (when needed)
  private Note[] guitarStrings = new Note[6];

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
