package com.igtv.structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.igtv.pic.util.PicCreator;

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

//    Iterator<Note> i = notes.iterator();
//    while (i.hasNext()) {
//      // Get the current note
//      Note curr = i.next();
//
//      if (frames.isEmpty() || frames.getLast().getOnsetInTicks() != curr.getOnsetInTicks()) {
//        frames.add(new Frame(curr.getOnsetInTicks()));
//      }
//
//      frames.getLast().addNote(curr);
//    }

    //I don't know what this is
    ArrayList something = PicCreator.createTabNumbers(notes, PicCreator.START_PITCH, PicCreator.END_PITCH);
    
    for (int j = 0; j < something.size(); j++) {
      ArrayList<Note> secondList = (ArrayList<Note>) something.get(j);
      
      for (Note note : secondList) {
        if (note.getStringNo() < 0) {
          continue;
        }
        
        if (frames.isEmpty() || frames.getLast().getOnsetInTicks() != note.getOnsetInTicks()) {
          frames.add(new Frame(note.getOnsetInTicks()));
        }

        Frame currFrame = frames.getLast();
        
        currFrame.guitarStringFrets[note.getStringNo()] = note.getFret();
      }
    }

    this.frames = frames;
  }

  public void print() {

    for (int i = 0; i < 30; i++) {
      Frame curr = frames.get(i);
      curr.print();
    }
  }

}
