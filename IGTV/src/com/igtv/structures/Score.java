package com.igtv.structures;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import edu.columbia.ee.csmit.MidiKaraoke.read.NotesInMidi;
import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRoll;
import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRollViewParser;

/**
 * A Score is a structure that stores notes as an 8-tuple: (pitch, velocity, channel, start (ticks),
 * duration (ticks), start (seconds), duration (seconds), track)
 * 
 * Serves as a temporary DTO to transmit between the MidiReader and the ScoreAnalyzer.
 */
public class Score {

  /**
   * An 8xN double array representing note tuples.
   */
  private double[][] notes;

  /**
   * Number of ticks per one quarter note.
   */
  private double ticksPerQuarterNotes;

  private ArrayList<Note> notesList;

  private NotesInMidi[] notes1D;
  
  private int tempo;

  private Sequence seq;

  public Score(Sequence seq) {
    this.seq = seq;
    
    try {
      PianoRoll roll = PianoRollViewParser.parse(seq);
      NotesInMidi[] notes = roll.getNotes();
      double[][] noteArray = roll.getNotesDoubles();
      this.notes = noteArray;
      this.notes1D = notes;
      this.notesList = getNotes();
      //this.tempo = getRes();
      // PicCreator.generatePicture(this.notesList);
    } catch (Exception e) {
      System.out.println("Problem!");
      e.printStackTrace();
      System.out.println(e.toString());
    }

  }

  /**
   * Total number of tracks in the MIDI file
   * 
   * @return
   */
  public int numberOfTracks() {
    return seq.getTracks().length;
  }

  /**
   * Returns content of the specified track
   * 
   * @param Track The track number being returned
   * @return The score of the requested track
   */
  public Score getTrack(int track) {

    Track t = seq.getTracks()[track];

    Sequence requested;
    try {
      requested = new Sequence(seq.getDivisionType(), seq.getResolution());
      Track newt = requested.createTrack();
      for (int i = 0; i < t.size(); i++) {
        newt.add(t.get(i));
      }

      return new Score(requested);
    } catch (InvalidMidiDataException e) {
      return null;
    }
  }

  /**
   * Returns the entire MIDI sequence
   * 
   * @return
   */
  public Sequence getSequence() {
    return seq;
  }

  /**
   * Converts the double[][] note array into an ArrayList
   * 
   * @return
   */
  public ArrayList<Note> getNotes() {
    ArrayList<Note> output = new ArrayList<Note>();
    Note note = null;

    for (int i = 0; i < this.notes1D.length; i++) {
      // Pull value for the note object
      NotesInMidi test = this.notes1D[i];
      long onset = test.getStartTick();
      long duration = test.getDurationTick();
      int pitch = test.getNote();
      int channel = test.getChannel();
      int track = test.getTrackNumber();

      // Create the note and add it to the ArrayList
      note = new Note(onset, duration, pitch, channel, track, 0, 0);
      output.add(note);
    }
    return output;
  }
  
  /**
   * Gets the resolution of a sequence, ticks per second (PPQ) or frame (STMPE)
   * 
   * @return int ticks per second (PPQ) or frame (SMPTE)
   */
  public int getTimingResolution() {
    int timeRes = seq.getResolution();
    return timeRes;
  }
}
