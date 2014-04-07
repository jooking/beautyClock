package com.qd.clock.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

public class GKStatics {
	public static String event_main = "event_main";
	
	public static void onEvent(Context context, String event) {
		MobclickAgent.onEvent(context, event);
	}
	
	
	
}
