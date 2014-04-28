package com.igtv.tests.unit;

import org.junit.Test;

import com.igtv.midi.io.MidiReader;
import com.igtv.structures.Score;

public class MidiReaderTest {

  // Unit Test Naming Convention: SetupSituation_ActionYouTake_ExpectedBehavior()

  @Test
  public void ValidMidiChosen_ReadScore_Imported() {
    // Setup
    String testPath = "assets/midi/Hotel California.mid";

    // Act
    Score result = MidiReader.readScore(testPath);

    // Assert
    assert (result != null);
  }

}
