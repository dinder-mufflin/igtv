package com.igtv.structures;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import edu.columbia.ee.csmit.MidiKaraoke.read.NotesInMidi;
import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRoll;
import edu.columbia.ee.csmit.MidiKaraoke.read.PianoRollViewParser;
import edu.columbia.ee.csmit.MidiKaraoke.read.SequenceDivisionTypeException;

/**
 * A Score is a structure that stores notes as an 8-tuple: (pitch, velocity, channel, start (ticks),
 * duration (ticks), start (seconds), duration (seconds), track)
 * 
 * Serves as a DTO to house each {@link Note}
 * 
 * @see Note
 * @see Frame
 * @see Tablature
 */
public class Score {

  /**
   * An 8xN double array representing note tuples (pitch, velocity, channel, start (ticks), duration
   * (ticks), start (seconds), duration (seconds), track)
   */
  private double[][] notes;

  /**
   * Number of ticks per one quarter note.
   */
  private double ticksPerQuarterNotes;

  /**
   * {@link ArrayList} of Note objects used for creating the {@link Tablature}
   */
  private ArrayList<Note> notesList;

  /**
   * Fixed array with NotesInMidi objects. The NotesInMidi object holds several more variables than
   * the Note object, but much of this extra information is not needed in IGTV
   * 
   * Serves as temporary placeholder between the MIDI file and the notesList ArrayList
   */
  private NotesInMidi[] notes1D;

  /**
   * The tempo of the score in BPM
   */
  private int tempo;

  /**
   * The sequence that the score will be based on
   */
  private Sequence seq;

  /**
   * Constructs a Score using a {@link Sequence} object
   * 
   * @pre !isNull(seq)
   * 
   * @param seq
   * 
   * @throws SequenceDivisionTypeException If the sequence is invalid
   * @throws RuntimeException if the sequence is null
   */
  public Score(Sequence seq) throws SequenceDivisionTypeException {

    // Check to see if the sequence is valid
    if (isNull(seq)) {

      // Sequence given is invalid. Throw an exception.
      throw new RuntimeException("The sequence given is invalid.");
    }

    // Store the sequence given
    this.seq = seq;

    /*
     * Parse the sequence into a easy to manipulate PianoRoll object.
     * 
     * If seq is invalid, a SequenceDivisionTypeException will be thrown here.
     */
    PianoRoll roll = PianoRollViewParser.parse(seq);

    // Get the notes as NotesInMidi objects
    NotesInMidi[] notes = roll.getNotes();

    // Convert the PianoRoll object into 1D and 2D arrays
    double[][] noteArray = roll.getNotesDoubles();

    // Update variables to point to the parsed arrays
    this.notes = noteArray;
    this.notes1D = notes;
    this.notesList = getNotes();

  }

  /**
   * Checks if an object is null
   * 
   * @param obj The object for testing
   * @return Whether or note the input is null
   */
  private boolean isNull(Object obj) {
    return obj == null;
  }

  /**
   * Total number of tracks in the MIDI file
   * 
   * @pre seq != null
   * @pre seq has tracks
   * 
   * @post No side effects occur
   * 
   * @return The number of tracks in the sequence
   */
  public int numberOfTracks() {
    return seq.getTracks().length;
  }

  /**
   * Returns content of the specified track
   * 
   * @pre track > 0
   * @post {@link Score} converted from a track
   * 
   * @param Track The track number being returned
   * @return The score of the requested track
   */
  public Score getTrack(int track) {

    if (track < 0) {
      return null;
    }

    // The desired track as a Track object
    Track t = seq.getTracks()[track];

    // Our output
    Sequence requested;

    try {
      // Get division and resolution for the Sequence object
      float divType = seq.getDivisionType();

      // Resolution (ticks per second)
      int ticksPerSecond = seq.getResolution();

      // Construct a new Sequence
      requested = new Sequence(divType, ticksPerSecond);

      // Update track information
      Track newt = requested.createTrack();

      for (int i = 0; i < t.size(); i++) {

        // Add the midi events to the track newt
        newt.add(t.get(i));
      }

      try {

        // Score may throw an exception
        return new Score(requested);

      } catch (SequenceDivisionTypeException e) {

        // An error occurred. Return null.
        e.printStackTrace();
        return null;
      }

    } catch (InvalidMidiDataException e) {

      // An error occurred. Return null
      return null;
    }
  }

  /**
   * Returns the entire MIDI sequence
   * 
   * @return The midi sequence given in the constructor
   */
  public Sequence getSequence() {
    return seq;
  }

  /**
   * Converts the double[][] note array into an ArrayList
   * 
   * @pre notes1D.length >= 0
   * 
   * @post notes1D converted to ArrayList
   * 
   * @return ArrayList of {@link Note} objects
   */
  public ArrayList<Note> getNotes() {
    ArrayList<Note> output = new ArrayList<Note>();
    Note note = null;

    for (int i = 0; i < this.notes1D.length; i++) {
      // Pull value for the note object
      NotesInMidi curr = this.notes1D[i];

      // Save each of the attributes for construction of a Note object
      long onset = curr.getStartTick();
      long duration = curr.getDurationTick();
      int pitch = curr.getNote();
      int channel = curr.getChannel();
      int track = curr.getTrackNumber();

      // Create the note from the data
      note = new Note(onset, duration, pitch, channel, track, 0, 0);

      // Add it to the ArrayList
      output.add(note);
    }

    // Return the notes as a list of Note objects
    return output;
  }

  /**
   * Gets the resolution of a sequence, ticks per second (PPQ) or frame (STMPE)
   * 
   * @pre seq != null
   * 
   * @post timeRes = Sequence resolution
   * 
   * @return int ticks per second (PPQ) or frame (SMPTE)
   */
  public int getTimingResolution() {
    int timeRes = seq.getResolution();
    return timeRes;
  }
}
