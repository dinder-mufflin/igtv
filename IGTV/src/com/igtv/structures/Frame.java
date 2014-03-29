package com.igtv.structures;

import java.util.HashSet;

/**
 * Represents all notes beginning at one point in time.
 */
public class Frame {

  HashSet<Note> notes = new HashSet<Note>();

  public Frame() {

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
}
