package com.qd.clock;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.qd.clock.utils.GKStatics;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class MainActivity extends BaseActivity {

	private Fragment mContent;
	
	public MainActivity() {
		super(R.string.changing_fragments);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 版本更新自动敬爱呢
		UmengUpdateAgent.setUpdateOnlyWifi(false);
	    UmengUpdateAgent.update(this);

	    // oline config params
		String onlineParams= MobclickAgent.getConfigParams(getApplicationContext(), "hello_test");//the demo param's key is 'abc'
		System.out.println("======" +onlineParams);
		
		// 统计测试
		GKStatics.onEvent(this, GKStatics.event_main);;
		
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new GKContentFragment(R.color.red);	
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new GKMenuFragment())
		.commit();
		
		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment, int position) {
		if (fragment != null) {
			mContent = fragment;	
			
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.content_frame, fragment)
			.commit();
		}
		getSlidingMenu().showContent();
		
		if (position == 3) {
		    UmengUpdateAgent.forceUpdate(this);
		}else if (position == 4) {
			FeedbackAgent agent = new FeedbackAgent(this);
			agent.sync();
			agent.startFeedbackActivity();
		}
	}
}
