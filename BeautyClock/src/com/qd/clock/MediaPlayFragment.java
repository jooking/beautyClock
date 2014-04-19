package com.qd.clock;

import java.io.IOException;

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
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v4.app.Fragment;
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

public class MediaPlayFragment extends Fragment implements
		OnCompletionListener, OnErrorListener, OnInfoListener,
		OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener,
		SurfaceHolder.Callback, OnTouchListener {
	private static final String TAG = "tag";
	private Display currDisplay;
	private SurfaceView surfaceView;
	private SurfaceHolder holder;
	private MediaPlayer mMediaPlayer;
	private int vWidth, vHeight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		currDisplay = getActivity().getWindowManager().getDefaultDisplay();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("Create:::", "onCreateView called");
		View rootView = inflater.inflate(R.layout.video_surface, container,
				false);
		surfaceView = (SurfaceView) rootView.findViewById(R.id.video_surface);
		// 给SurfaceView添加CallBack监听
		holder = surfaceView.getHolder();
		holder.addCallback(this);
		// 为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
		// holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.setOnTouchListener(this);
		// 下面开始实例化MediaPlayer对象
		return rootView;
	}
	
	/*
	 * @Override public void onSaveInstanceState(Bundle outState) {
	 * super.onSaveInstanceState(outState); //outState.putInt("mColorRes",
	 * mColorRes); }
	 */

	/*
	 * 初始化MediaPlayer： 设置监听 指定播放源路径 prepare()预处理
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void initPlayer() {
		String dataPath = Environment.getExternalStorageDirectory().getPath()
				+ "/Video1.mp4";
		AssetManager manager = getResources().getAssets();
		
		/*
		 * Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(dataPath,
		 * Thumbnails.FULL_SCREEN_KIND); Bitmap bm
		 * =ThumbnailUtils.extractThumbnail(bitmap, currDisplay.getWidth(),
		 * currDisplay.getHeight()); surfaceView.setBackground(new
		 * BitmapDrawable(bm));
		 */
		
		if (mMediaPlayer == null) {
//			mMediaPlayer = new MediaPlayer();
			mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.video1);
		}
//		mMediaPlayer.reset();
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(this);
		mMediaPlayer.setOnInfoListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setOnSeekCompleteListener(this);
		mMediaPlayer.setOnVideoSizeChangedListener(this);
		// 然后指定需要播放文件的路径，初始化MediaPlayer
		
//		try {
//			// Log.v("设置播放源::", dataPath);
////			mMediaPlayer.setDataSource(dataPath);
		
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		mMediaPlayer.setDisplay(holder);
//		mMediaPlayer.prepareAsync();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int format, int width,
			int height) {
		// 当Surface尺寸,像素格式等参数改变时触发
		Log.v("Surface Change:::", "呼叫surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v("Surface Created:::", "呼叫surfaceCreated");
		initPlayer();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("Surface Destory:::", "呼叫surfaceDestroyed");
		if (null != mMediaPlayer) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
		// 当video大小改变时触发
		// 这个方法在设置player的source后至少触发一次
		Log.v("Video Size Change", "onVideoSizeChanged called");

	}

	@Override
	public void onSeekComplete(MediaPlayer arg0) {
		// seek操作完成时触发
		Log.v("Seek Completion", "onSeekComplete called");

	}

	@Override
	public void onPrepared(MediaPlayer player) {
		// 当prepare完成后，该方法触发，在这里我们播放视频
		Log.v("mediaPayer ready", "onPrepared called");
		// 首先取得video的宽和高
		vWidth = player.getVideoWidth();
		vHeight = player.getVideoHeight();
		/*
		 * //大小的适配 :动态改变大小 if(vWidth > currDisplay.getWidth() || vHeight >
		 * currDisplay.getHeight()){ //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放 float
		 * wRatio = (float)vWidth/(float)currDisplay.getWidth(); float hRatio =
		 * (float)vHeight/(float)currDisplay.getHeight();
		 * 
		 * //选择大的一个进行缩放 float ratio = Math.max(wRatio, hRatio);
		 * 
		 * vWidth = (int)Math.ceil((float)vWidth/ratio); vHeight =
		 * (int)Math.ceil((float)vHeight/ratio); } //设置surfaceView的布局参数
		 * surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth,
		 * vHeight)); //然后开始播放视频
		 */
	}

	@Override
	public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {
		// 当一些特定信息出现或者警告时触发
		switch (whatInfo) {
		case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING: // 当文件中的音频和视频数据不正确的交错时，将触发如下操作。
			Log.v(TAG, "MEDIA_INFO_BAD_INTERLEAVING extar is :" + extra);
			break;
		case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
			Log.v(TAG, "MEDIA_INFO_METADATA_UPDATE extar is :" + extra);
			break;
		case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING: // 当无法播放视频时，可能是将要播放视频，但是视频太复杂
			Log.v(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING extar is :" + extra);
			break;
		case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE: // 媒体不能正确定位，意味着它可能是一个在线流
			Log.v(TAG, "MEDIA_INFO_NOT_SEEKABLE extar is :" + extra);
			break;
		}
		return false;
	}

	@Override
	public boolean onError(MediaPlayer player, int whatError, int extra) {
		Log.v("Play Error:::", "onError called");
		switch (whatError) {
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Log.v("Play Error:::", "MEDIA_ERROR_SERVER_DIED");
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Log.v("Play Error:::", "MEDIA_ERROR_UNKNOWN");
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer player) {
		// 当MediaPlayer播放完成后触发
		Log.v("Play Over:::", "onComletion called");
		
		// reset();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
				mMediaPlayer.start();
			}
			break;
		default:
			break;
		}
		
		return true;
	}
}
