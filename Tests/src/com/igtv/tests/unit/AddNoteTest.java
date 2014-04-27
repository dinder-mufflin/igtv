package com.igtv.tests.unit;

import org.junit.Test;

import com.igtv.structures.Note;
import com.igtv.structures.Frame;

public class AddNoteTest {

	//Unit Test Naming Convention:  SetupSituation_ActionYouTake_ExpectedBeahavior()
	
	@Test
	public void FrameIsEmptyAndNoteExists_AddNote_FrameHasOneNote() {
		//Setup
	    Note testNote = new Note(0, 0, 0, 0, 0, 0, 0);
		Frame testFrame = new Frame(0);
		int empty = testFrame.getNotes().size();
		
	    //Act
	    testFrame.addNote(testNote);
	    int result = testFrame.getNotes().size();
		
	    //Assert
	    assert(empty == 0);
	    assert(result == 1);
	}

}
