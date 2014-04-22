package com.igtv.pic.util;

import java.util.ArrayList;

import com.igtv.structures.Note;

public class PicCreator {

  private static final int START_PITCH = 40;
  private static final int END_PITCH = 88;
  static int mainListIndex, stackListIndex;
  private static ArrayList sameNotes;

  public static void generatePicture(ArrayList<Note> notesList) throws Exception {
    correctNoteRange(notesList);
    createTabNumbers(notesList, START_PITCH, END_PITCH);
  }

  /**
   * Correct all note to be in guitar range. Guitar range: E2 (28) - E6 (76)
   * 
   * @param list
   */
  private static void correctNoteRange(ArrayList<Note> list) {
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
   * TODO CORRECT ALL SYSO WITH PICTURE PRINTING
   * 
   * @param list
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  private static void createTabNumbers(ArrayList<Note> list, int lower, int high) {
    boolean hasBeenWriten = true;
    sameNotes = new ArrayList<Note>(list.size());
    int counter, string;
    mainListIndex = 0;
    stackListIndex = 0;
    for (int j = 0; j < list.size(); j++) {
      Note note = list.get(j);
      counter = 0;
      string = 0;
      int pitch = note.getPitch();
      for (int i = lower; i < high; i++) {
        if (i == pitch) {
          ArrayList<Note> stack = new ArrayList<Note>(10);
          note.setString(string);
          note.setFret(counter);
          stack.add(stackListIndex, note);
          stackListIndex++;

          while (j + 1 < list.size()) {
            Note next = list.get(j + 1);
            if (next.getOnsetInTicks() == note.getOnsetInTicks() && string != 5) {
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
          string = 0;
          stackListIndex = 0;
        } else if (counter == 7 && string != 5) {
          counter = 0;
          string++;
          if (string != 4) {
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
      PicUtil.drawTab(sameNotes);
      hasBeenWriten = false;
    }
  }

  public static void updateNextNote(Note note, Note compare) {
    int pitch = compare.getPitch();
    int string = 0;
    int fret = 0;
    for (int i = START_PITCH; i < END_PITCH; i++) {
      if (i == pitch && string != note.getString()) {
        compare.setString(string);
        compare.setFret(fret);
        return;
      } else if (i == pitch && string == note.getString()) {
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
