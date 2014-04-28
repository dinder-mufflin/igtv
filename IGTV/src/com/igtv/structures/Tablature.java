package com.igtv.structures;

import java.util.ArrayList;
import java.util.LinkedList;

import com.igtv.pic.util.PicCreator;

/**
 * 
 *
 */
public class Tablature {
  
  /**
   * Notes compartmentalized into frames (ordered by onset)
   */

  /**
   * Contains all frames of the track, the {@link Frame} holds {@link Note} objects and several
   * other variables pertaining to length and note format
   */
  private LinkedList<Frame> frames;

  /**
   * Title of the MIDI file
   */
  private String title;

  /**
   * References the user imported {@link Score}
   */
  private Score score;

  /**
   * 
   * @return Title of the MIDI file
   */
  public String getTitle() {
    return title;
  }

  /**
   * 
   * @param title Title of the MIDI file
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 
   * @param score Sets the {@link Score} and parses it
   */
  public Tablature(Score score) {
    this.score = score;
    parse();
  }

  /**
   * 
   * @return All {@link Frame} objects attached to this {@link Tablature} object
   */
  public LinkedList<Frame> getFrames() {
    return frames;
  }

  /**
   * 
   * @return The {@link Score} object attached to this {@link Tablature} object
   */
  public Score getScore() {
    return score;
  }

  /**
   * Parses a {@link Score}
   * 
   * @param score
   */
  private void parse() {
    // Notes for parsing
    ArrayList<Note> notes = score.getNotes();

    // Frames for saving into
    LinkedList<Frame> frames = new LinkedList<Frame>();

    // Creates an ArrayList of Tablature fret numberings
    ArrayList something =
        PicCreator.createTabNumbers(notes, PicCreator.START_PITCH, PicCreator.END_PITCH);

    // Convert to {@link Note} ArrayList
    for (int j = 0; j < something.size(); j++) {
      ArrayList<Note> secondList = (ArrayList<Note>) something.get(j);

      // Bypass all incorrectly formated MIDI data from the MIDI file
      for (Note note : secondList) {
        if (note.getStringNo() < 0) {
          continue;
        }

        // Add a frame to the frames LinkedList
        if (frames.isEmpty() || frames.getLast().getOnsetInTicks() != note.getOnsetInTicks()) {
          frames.add(new Frame(note.getOnsetInTicks()));
        }

        // Add the note to the {@link Frame} 
        Frame currFrame = frames.getLast();

        currFrame.durations[note.getStringNo()] = note.getDurationInTicks();
        currFrame.guitarStringFrets[note.getStringNo()] = note.getFret();
      }
    }

    // Update pointers to the modified frame
    this.frames = frames;
  }

  /**
   * Gets measures in ticks per quarter note. Assuming 4/4 time
   * 
   * @return measure
   */
  public int getMeasure() {
    int ticksPerQuarterNote = score.getTimingResolution();
    int measure = ticksPerQuarterNote * 4;
    return measure;
  }

  public void print() {

    for (int i = 0; i < 30; i++) {
      Frame curr = frames.get(i);
      // curr.print();
    }
  }

}
