package com.igtv.structures;

import java.util.ArrayList;
import java.util.Iterator;

public class Tablature {
  
  public Tablature() {
    
  }
  
  /**
   * Parses a {@Score}
   * @param score
   */
  public void parse(Score score) {
    //Notes for parsing
    ArrayList<Note> notes = score.getNotes();
    
    //Frames for saving into
    ArrayList<Frame> frames = new ArrayList<Frame>();
    
    //Set up for loop iteration
    double currentOnset = notes.get(0).getOnsetInTicks();
    frames.add(new Frame());
    
    Iterator<Note> i = notes.iterator();
    while(i.hasNext()) {
      //Get the current note
      Note curr = i.next();
      
      //TODO: Finish this loop
    }
  }
  
}
