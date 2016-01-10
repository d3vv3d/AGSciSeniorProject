package com.d3v.senior.project.music;

public class Note {
	
	double range = 2.0d;
	double step = 0.08d;
	
	// This note is C3 for every not after just add to it
	int baseNote = 48;
	
	//The default not is middle C (C4)
	int note = 60;
	
	//The array to store what values cause what note to play
	double[] notesC3toC5 = new double[25];
	//Where we are in the array 
	int index = 0;
	
	//Note scale
	// S stands for sharp
	/*double C3;
	double CS3;
	double D3;
	double DS3;
	double E3;
	double F3;
	double FS3;
	double G3;
	double GS3;
	double A4;
	double AS4;
	double B4;
	double C4;
	double CS4;
	double D4;
	double DS4;
	double E4;
	double F4;
	double FS4;
	double G4;
	double GS4;
	double A5;
	double AS5;
	double B5;
	double C5;*/
	
	/*
	 * rangeStart is the bottom end of the range and should be negative or zero (rangeStart =< 0)
	 * rangeEnd is the top end of the range and should always be positive or zero (rangeEnd >= 0)
	 */
	public Note(double rangeStart, double rangeEnd) {
		range = Math.abs(rangeStart) + Math.abs(rangeEnd);
		step = range / 25.0d;
		
		//C3 = rangeStart + step;
		
		notesC3toC5[0] = rangeStart + step;
		for (int i = 2; i <= 25; i++)
			notesC3toC5[i - 1] = rangeStart + (step * (i));
	}
	
	public int getNote(double noise) {
		index = 0;
		
		while(noise > notesC3toC5[index])
			index++;
		
		return baseNote + index;
	}
}
