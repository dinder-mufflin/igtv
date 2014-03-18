package com.igtv.structures;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

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

  private Sequence seq;

  public Score(Sequence seq) {
    this.seq = seq;
  }

  public int numberOfTracks() {
    return seq.getTracks().length;
  }
  
  public Score getTrack(int track) {
    
    Track t = seq.getTracks()[track];
    
    Sequence requested;
    try {
      
      requested = new Sequence(seq.getDivisionType(), seq.getResolution());
      Track newt = requested.createTrack();
      for(int i=0; i<t.size(); i++) {
        newt.add(t.get(i));
      }
      
      return new Score(requested);
    } catch (InvalidMidiDataException e) {
      return null;
    }
  }
  
  public Sequence getSequence() {
    return seq;
  }

}
