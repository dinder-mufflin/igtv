package com.igtv.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 *
 */
public class Tablature {
  
  private LinkedList<Frame> frames;

  /**
   * 
   * @param score
   */
  public Tablature(Score score) {
    parse(score);
  }

  /**
   * Parses a {@Score}
   * 
   * @param score
   */
  private void parse(Score score) {
    // Notes for parsing
    ArrayList<Note> notes = score.getNotes();

    // Frames for saving into
    LinkedList<Frame> frames = new LinkedList<Frame>();

    Iterator<Note> i = notes.iterator();
    while (i.hasNext()) {
      // Get the current note
      Note curr = i.next();

      if (frames.isEmpty() || frames.getLast().getOnsetInTicks() != curr.getOnsetInTicks()) {
        frames.add(new Frame(curr.getOnsetInTicks()));
      }

      frames.getLast().addNote(curr);
    }
    
    this.frames = frames;
  }

}
