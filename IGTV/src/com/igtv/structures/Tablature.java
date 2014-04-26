package com.igtv.structures;

import java.util.ArrayList;
import java.util.LinkedList;

import com.igtv.pic.util.PicCreator;

/**
 * Represents a full guitar tablature.
 */
public class Tablature {

  /**
   * Notes compartmentalized into frames (ordered by onset)
   */
  private LinkedList<Frame> frames;

  /**
   * Song title for the tablature
   */
  private String title;

  /**
   * Original score the tablature is made from
   */
  private Score score;

  /**
   * Returns the song title
   * 
   * @return The tablature's song title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Assigns a new song title to the tablature
   * 
   * @param title Song title to be assigned
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Constructor. Creates a Tablature object and parses notes into individual frames.
   * 
   * @param score Score of notes used to generate the assignment
   */
  public Tablature(Score score) {
    this.score = score;
    parse();
  }

  /**
   * Returns all frames in the score
   * 
   * @return
   */
  public LinkedList<Frame> getFrames() {
    return frames;
  }

  /**
   * Returns the original score given in the constructor
   * 
   * @return
   */
  public Score getScore() {
    return score;
  }

  // TODO: Add documentation here
  protected void parse() {
    // Notes for parsing
    ArrayList<Note> notes = score.getNotes();

    // Frames for saving into
    LinkedList<Frame> frames = new LinkedList<Frame>();

    // I don't know what this is
    ArrayList something =
        PicCreator.createTabNumbers(notes, PicCreator.START_PITCH, PicCreator.END_PITCH);

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

        currFrame.durations[note.getStringNo()] = note.getDurationInTicks();
        currFrame.guitarStringFrets[note.getStringNo()] = note.getFret();
      }
    }
    this.frames = frames;
  }
}
