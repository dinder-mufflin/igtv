package com.igtv.structures;

/**
 * DTO representation of a note. Performs no logic apart from its construction
 * 
 */
public class Note implements Comparable<Note>{

	private int string;

	private int fret;

	/**
	 * 
	 * @param onsetInTicks
	 * @param durationInTicks
	 * @param pitch
	 * @param channel
	 * @param track
	 */
	Note(long onsetInTicks, long durationInTicks, int pitch, int channel,
			int track, int string, int fret) {
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
	 * Channel of the note from the imported MIDI file
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

	public int getString() {
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

	@Override
	public String toString() {
		return "Note [string=" + string + ", fret=" + fret + ", onsetInTicks="
				+ onsetInTicks + ", durationInTicks=" + durationInTicks
				+ ", pitch=" + pitch + ", channel=" + channel + ", track="
				+ track + "]";
	}
	
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
