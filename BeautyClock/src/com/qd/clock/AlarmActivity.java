package com.qd.clock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class AlarmActivity extends Activity{	
	private VideoView mVideoView;
	
	@Override
	 public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	 }
	
	@Override
	public void onResume(){
		super.onResume();	
		
		 
		 MediaController mc = new MediaController(this);
			mc.setVisibility(View.INVISIBLE);
			mVideoView.setMediaController(mc); 
		 
		 String path = "android.resource://com.qd.clock/" + R.raw.video1;
		 mVideoView.setVideoPath(path);		 
		 mVideoView.start();
		 mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {  
			  
	            @Override  
	            public void onPrepared(MediaPlayer mp) {  
	                mp.start();  
	                mp.setLooping(true);  
	  
	            }  
	        }); 
		 
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		final Window win = getWindow();
		 win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
		 | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		 win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
		 | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		 setContentView(R.layout.alarm_video_view);

		
		mVideoView=(VideoView) findViewById(R.id.vedio_playing);
		
	}
	public void onStopMediaClick(View v) {
		// TODO Auto-generated method stub
		//mVideoView.pause();
		if (mVideoView.isPlaying()) {
			mVideoView.stopPlayback();
		}
		this.finish();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		if (mVideoView.isPlaying()) {
			mVideoView.stopPlayback();
			
		}
	}
	
}
