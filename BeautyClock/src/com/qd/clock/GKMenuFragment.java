package com.qd.clock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class GKMenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] colors = getResources().getStringArray(R.array.color_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
//			newContent = new MediaPlayFragment();
			newContent = new GKVideoFragment();
			break;
		case 1:
			newContent = new GKContentFragment(R.color.green);
			break;
		case 2:
			newContent = new GKContentFragment(R.color.blue);
			break;
		case 3:
//			newContent = new GKContentFragment(android.R.color.white);
			break;
		case 4:
//			newContent = new ColorFragment(android.R.color.black);
//			newContent = new GKFeedBackFragment();
			break;
		}
		
//		if (newContent != null)
			switchFragment(newContent, position);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment, int position) {
		if (getActivity() == null)
			return;
		
		if (this.getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment, position);
		} 
		
	}


}
