package com.cyclone;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Random;

public class SplashActivity extends Activity {

	private ImageView imgSplash;

	// daftar gambar buat di splash screen
	private int[] images = {R.drawable.background_login, R.drawable.wallpaper};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// milih gambar random dari daftar gambar
		imgSplash = (ImageView) findViewById(R.id.img_splash);
		int counter = images.length;
		Random random = new Random();
		int selectedImage = random.nextInt(counter);
		imgSplash.setImageResource(images[selectedImage]);

		// play sound effect
		MediaPlayer mp = MediaPlayer.create(this, R.raw.sound_effect);
		mp.start();

		// buat start ke aplikasi setelah splash screen
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(getApplicationContext(), EmptyActivity.class);
				i.putExtra("fragmentType", EmptyActivity.FRAGMENT_GET_STARTED);
				startActivity(i);
				finish();
			}
		}, 500/*mp.getDuration()*/); // pake mp.getDuration kalo mau dengerin sound effect sampe abis
	}

}
