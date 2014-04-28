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
   * @pre score != null
   * @post added frames to the tablature
   * @param score
   */
  private void parse() {
    // Notes for parsing
    ArrayList<Note> notes = score.getNotes();

    // Frames for saving into
    LinkedList<Frame> frames = new LinkedList<Frame>();

    // Convert to {@link Note} ArrayList
    notes = createTabNumbers(notes, PicCreator.START_PITCH, PicCreator.END_PITCH);

    // Bypass all incorrectly formated MIDI data from the MIDI file
    for (Note note : notes) {
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

    // Update pointers to the modified frame
    this.frames = frames;
  }

  /**
   * Gets measures in ticks per quarter note. Assuming 4/4 time
   * 
   * @pre Score != null
   * @post measure > 0
   * 
   * @return Measure value in ticks
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


  /**
   * Takes an {@link ArrayList} of {@link Note} objects and processes it to return that list with
   * all notes containing the correct string and fret markers
   * 
   * @pre min > 0 && max > 0 
   * @post Updated arraylist with note and string numbers
   * 
   * @param list The list to be iterated over
   * @param min The lowest note to start looking for
   * @param max The highest note to start looking for
   * @return The corrected list with all fret and string markers
   * 
   */
  public static ArrayList<Note> createTabNumbers(ArrayList<Note> list, int min, int max) {

    // Min and max cannot be negative numbers
    if (min < 0 || max < 0) {
      return null;
    }

    // Create the temporary ArrayList that will be returned
    ArrayList<Note> notesList = new ArrayList<Note>(list.size());

    // Counters for the fret and strings
    int fret = 0;
    int stringNo = 0;

    /*
     * Loop through the list of notes
     */
    for (int j = 0; j < list.size(); j++) {
      // Relevant note for this iteration
      Note curr = list.get(j);
      int currPitch = curr.getPitch();

      /*
       * Iterate through from min to max
       */
      for (int i = min; i < max; i++) {

        // If we have reached the pitch of the current note
        if (i == currPitch) {

          // Confirm the current string and fret number
          curr.setString(stringNo);
          curr.setFret(fret);

          // Add the corrected note to the notesList
          notesList.add(curr);

          // Reset the counter and string number to 0 for the new note
          fret = 0;
          stringNo = 0;
          break;
        } else if (fret == 7 && stringNo != 5) {
          // Increase string number and reset the fret counter
          fret = 0;
          stringNo++;

          // Must account for the reset of fret number
          if (stringNo != 4) {
            // Every string other than the G string
            i -= 3;
          } else {
            // Accounts for the G String
            i -= 4;
          }
        } else {
          // Increment fret
          fret++;
        }

      }
    }
    return notesList;
  }


}
