package com.igtv.structures;

/**
 * DTO representation of a note. Performs no logic apart from its construction
 * 
 */
public class Note implements Comparable<Note> {

  //the string a note is found on a guitar (values {0-6})
  private int string;

  //the fret of a string that corresponds to the note
  private int fret;

  /**
   * 
   * Constructor of a Note.
   * Construction params based on an imported MIDI file.
   * 
   * @param onsetInTicks The starting position (in MIDI ticks) of a note
   * @param durationInTicks The length (in MIDI ticks) of a note
   * @param pitch The frequency of a note in MIDI format (60 = Middle C).
   * @param channel The MIDI channel (values {1-16}) this note occupies
   * @param track The track of the note in the MIDI file
   * @param string The string of a guitar on which the note resides
   * @param fret The fret of a guitar string corresponding to the note
   */
  Note(long onsetInTicks, long durationInTicks, int pitch, int channel, int track, int string,
      int fret) {
    this.onsetInTicks = onsetInTicks;
    this.durationInTicks = durationInTicks;
    this.pitch = pitch;
    this.channel = channel;
    this.track = track;
    this.string = -1;
    this.fret = -1;
  }

  /**
   * Onset of the note (in ticks)
   */
  private long onsetInTicks;

  /**
   * Duration of the note (in ticks)
   */
  private long durationInTicks;

  /**
   * MIDI pitch (60 is Middle C, 61 is C#, etc.)
   */
  private int pitch;

  /**
   * Channel of the note from the imported MIDI file, values {1-16}
   */
  private int channel;

  /**
   * Track of the note from the imported MIDI file
   */
  private int track;
  
  public long getOnsetInTicks() {
    return onsetInTicks;
  }

  public void setOnsetInTicks(int onsetInTicks) {
    this.onsetInTicks = onsetInTicks;
  }

  public long getDurationInTicks() {
    return durationInTicks;
  }

  public void setDurationInTicks(int durationInTicks) {
    this.durationInTicks = durationInTicks;
  }

  public int getPitch() {
    return pitch;
  }

  public void setPitch(int pitch) {
    this.pitch = pitch;
  }

  public int getChannel() {
    return channel;
  }

  public void setChannel(int channel) {
    this.channel = channel;
  }

  public int getTrack() {
    return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public void setOnsetInTicks(long onsetInTicks) {
		this.onsetInTicks = onsetInTicks;
	}

	public void setDurationInTicks(long durationInTicks) {
		this.durationInTicks = durationInTicks;
	}

	public int getStringNo() {
		return string;
	}

	public void setString(int string) {
		this.string = string;
	}

	public int getFret() {
		return fret;
	}

	public void setFret(int fret) {
		this.fret = fret;
	}

	/**
	 * Returns a string value, printing all parameters of a note in context.
	 * Useful for debugging purposes. 
	 * 
	 * @return String value listing all of a note's param values.
	 */
	@Override
	public String toString() {
		return "Note [string=" + string + ", fret=" + fret + ", onsetInTicks="
				+ onsetInTicks + ", durationInTicks=" + durationInTicks
				+ ", pitch=" + pitch + ", channel=" + channel + ", track="
				+ track + "]";
	}
	
	/**
	 * Compares two notes based on their pitch value
	 * 
	 * @param note The note to compare to super.
	 * @return 0 if equal; -1 if less than. +1 if greater than
	 */
	@Override
	public int compareTo(Note note){
		if (this.getPitch() < note.getPitch())
            return -1;
        else if (this.getPitch() == note.getPitch())
            return 0;
        else 
            return 1;
	}

}
