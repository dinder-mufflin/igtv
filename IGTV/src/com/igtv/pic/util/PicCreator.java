package com.igtv.pic.util;

import java.util.ArrayList;

import com.igtv.structures.Note;

public class PicCreator {

  public static final int START_PITCH = 40;
  public static final int END_PITCH = 88;
  static int mainListIndex, stackListIndex;
  private static ArrayList sameNotes;

  public static void generatePicture(ArrayList<Note> notesList) throws Exception {
    transposeToGuitarRange(notesList);
    createTabNumbers(notesList, START_PITCH, END_PITCH);
  }

  /**
   * Correct all note to be in guitar range. Guitar range: E2 (28) - E6 (76)
   * 
   * @param list
   */
  private static void transposeToGuitarRange(ArrayList<Note> list) {
    for (Note note : list) {
      int pitch = note.getPitch();
      if (pitch < 40) {
        while (pitch < 40) {
          pitch += 12;
        }
      } else if (pitch > 88) {
        while (pitch > 88) {
          pitch -= 12;
        }
      }
      note.setPitch(pitch);
    }
  }

  /**
   * Uses {@PicUtil} to generate a tablature image.
   * 
   * @param list List of notes for parsing
   * @param min Lower pitch?
   * @param max Higher pitch?
   * @return
   */
  public static ArrayList createTabNumbers(ArrayList<Note> list, int min, int max) {
    // What is the purpose of this?
    boolean hasBeenWriten = true;

    sameNotes = new ArrayList<Note>(list.size());
    int counter, stringNo;
    mainListIndex = 0;
    stackListIndex = 0;

    /*
     * Loop through the list of notes
     */
    for (int j = 0; j < list.size(); j++) {
      // Relevant note for this iteration
      Note curr = list.get(j);
      int currPitch = curr.getPitch();

      counter = 0;
      stringNo = 0;

      /*
       * Iterate through from min to max
       */
      for (int i = min; i < max; i++) {

        // If we have reached the pitch of the current note
        if (i == currPitch) {
          ArrayList<Note> stack = new ArrayList<Note>(10);

          // Confirm the current string and fret number
          curr.setString(stringNo);
          curr.setFret(counter);

          // I do not know what this means
          stack.add(stackListIndex, curr);
          stackListIndex++;

          // I do not understand
          while (j + 1 < list.size()) {
            // Note following curr
            Note next = list.get(j + 1);

            // I'm lost.
            if (next.getOnsetInTicks() == curr.getOnsetInTicks() && stringNo != 5) {
              stack.add(stackListIndex, next);
              stackListIndex++;
              i++;
              j++;
            } else {
              sameNotes.add(mainListIndex, stack);
              mainListIndex++;
              break;
            }
          }

          counter = 0;
          stringNo = 0;
          stackListIndex = 0;
        } else if (counter == 7 && stringNo != 5) {
          counter = 0;
          stringNo++;
          if (stringNo != 4) {
            i -= 3;
          } else {
            i -= 4;
          }
        } else {
          counter++;
        }

      }
    }
    if (hasBeenWriten) {
      // PicUtil.drawTab(sameNotes);
      hasBeenWriten = false;
    }
    return sameNotes;
  }

  public static void updateNextNote(Note note, Note compare) {
    int pitch = compare.getPitch();
    int string = 0;
    int fret = 0;
    for (int i = START_PITCH; i < END_PITCH; i++) {
      if (i == pitch && string != note.getStringNo()) {
        compare.setString(string);
        compare.setFret(fret);
        return;
      } else if (i == pitch && string == note.getStringNo()) {
        compare.setString(-1);;
        return;
      } else if (fret == 7 && string != 5) {
        fret = 0;
        string++;
      } else {
        fret++;
      }
    }
  }
}
