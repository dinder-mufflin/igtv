package com.igtv.tests.unit;

import org.junit.Test;

import com.igtv.structures.Note;

public class SetPitchTest {

  // Unit Test Naming Convention: SetupSituation_ActionYouTake_ExpectedBeahavior()

  @Test
  public void NoteExists_ChangePitch_NoteHasEnteredPitch() {
    // Setup
    Note testNote = new Note(0, 0, 60, 0, 0, 0, 0);
    int newPitch = 67;

    // Act
    testNote.setPitch(newPitch);

    // Assert
    assert (testNote.getPitch() == newPitch);
  }

}
