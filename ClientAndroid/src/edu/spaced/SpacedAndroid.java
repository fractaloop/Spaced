package edu.spaced;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class SpacedAndroid extends AndroidApplication {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new Spaced(), false);               
	}
}