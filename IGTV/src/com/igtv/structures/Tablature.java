package com.igtv.structures;

import java.util.ArrayList;
import java.util.LinkedList;

import com.igtv.pic.util.PicCreator;

/**
 * 
 *
 */
public class Tablature {

  private LinkedList<Frame> frames;

  private String title;

  private Score score;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 
   * @param score
   */
  public Tablature(Score score) {
    this.score = score;
    parse();
  }

  public LinkedList<Frame> getFrames() {
    return frames;
  }

  public Score getScore() {
    return score;
  }

  /**
   * Parses a {@Score}
   * 
   * @param score
   */
  private void parse() {
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
  
  /**
   * Gets measures in ticks per quarter note. Assuming 4/4 time
   * 
   * @return measure
   */
  public int getMeasure() {
    int  ticksPerQuarterNote = score.getTimingResolution();
    int measure = ticksPerQuarterNote * 4;
    return measure;
  }
  
  public int getScale() {
    int scale = score.getTimingResolution();
    return scale;
  }
  
  /**
   * Gets the length of the score for the tablature in MIDI ticks
   * 
   * 
   * @return long, value in ticks of MIDI length
   */
  public long getTabLength() {
    long length = score.getScoreTickLength();
    System.out.println("Tab length = " +length);
    return length;
  }
  
  /**
   * Gets the ratio of the length of a MIDI score to the length of it's view
   * 
   * 
   * @param boxWidth
   * @param tickLength
   * @return double ratio representing (length of view) / (MIDI length in ticks)
   */
  
  public double getTabTickRatio(double boxWidth, long tickLength) {
    double ratio =  (boxWidth / tickLength);
    return ratio;
  }

  public void print() {

    for (int i = 0; i < 30; i++) {
      Frame curr = frames.get(i);
      curr.print();
    }
  }

}
