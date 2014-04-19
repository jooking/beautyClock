package com.qd.clock;

import java.io.IOException;

import com.qd.clock.utils.CommonHelp;

import android.R.raw;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v4.app.Fragment;
import android.text.method.Touch;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

@SuppressLint("NewApi")
public class GKVideoFragment extends Fragment implements OnTouchListener,OnCompletionListener {
	private static final String TAG = "tag";
	private VideoView mVideoView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// currDisplay = getActivity().getWindowManager().getDefaultDisplay();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("Create:::", "onCreateView called");
		View rootView = inflater.inflate(R.layout.video_fragment, container,
				false);
		mVideoView = (VideoView) rootView.findViewById(R.id.shouye_video_view);
		mVideoView.setOnTouchListener(this);
		mVideoView.setOnCompletionListener(this);
		
		MediaController mc = new MediaController(this.getActivity());
		mc.setVisibility(View.INVISIBLE);
		mVideoView.setMediaController(mc);

		String path = "android.resource://com.qd.clock/" + R.raw.video1;
		// String path = Environment.getExternalStorageDirectory().getPath()
		// + "/Video1.mp4";

		Bitmap thumbAsBitmap = ThumbnailUtils.createVideoThumbnail(path,
				MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
		Bitmap image = this.getVideoThumbnailWithId(R.raw.video1);
		// mVideoView.setBackground(new BitmapDrawable(image));
		
		mVideoView.setBackgroundResource(R.drawable.img_48);
		
		return rootView;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			System.out.println("touch down");
			if (null != mVideoView && !mVideoView.isPlaying()) {
				mVideoView.setBackground(null);
				
				mVideoView.setVideoPath(CommonHelp
						.pathFromRawResourceId(R.raw.video1));
				mVideoView.start();
			}
			break;
		default:
			break;
		}

		return true;
	}

	@Override
	public void onCompletion(MediaPlayer player) {
		// 当MediaPlayer播放完成后触发
//		Log.v("Play Over:::", "onComletion called");
		mVideoView.setBackgroundResource(R.drawable.img_48);
	}
	
	private Bitmap getVideoThumbnailWithId(int rsid) {
		Bitmap imageBitmap = CommonHelp.getVideoThumbnail(
				CommonHelp.pathFromRawResourceId(rsid), 300, 300,
				Thumbnails.FULL_SCREEN_KIND);
		
		return imageBitmap;
	}

}
