package com.d3v.senior.project.music;

import javax.sound.midi.*;

public class MidiHelper {
	
	Synthesizer synth;
	static MidiChannel[] midiChannel;
	static Instrument[] intruments;
	
	static int currentNote;
	
	public MidiHelper() {
		try {
			synth = MidiSystem.getSynthesizer();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
		long startTime = System.nanoTime();
		try {
			synth.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		long estimatedTime = System.nanoTime() - startTime;
		
		midiChannel = synth.getChannels();
		intruments = synth.getDefaultSoundbank().getInstruments();
		@SuppressWarnings("unused")
		boolean successLoadingInstrument = synth.loadInstrument(intruments[0]);
	}
	
	public void play(int midiCode) {
		midiChannel[0].noteOn(midiCode, 93);
		currentNote = midiCode;
	}
	
	public void stop() {
		midiChannel[0].noteOff(currentNote);
	}
	
	public void end() {
		midiChannel[0].noteOff(currentNote);
		synth.close();
	}
}
