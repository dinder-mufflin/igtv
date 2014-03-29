package com.igtv.structures;

/**
 * DTO representation of a note. Performs no logic apart from its construction
 */
public class Note {

  /**
   * Onset of the note (in ticks)
   */
  private int onsetInTicks;

  /**
   * Duration of the note (in ticks)
   */
  private int durationInTicks;

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

  public int getOnsetInTicks() {
    return onsetInTicks;
  }

  public void setOnsetInTicks(int onsetInTicks) {
    this.onsetInTicks = onsetInTicks;
  }

  public int getDurationInTicks() {
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

}
