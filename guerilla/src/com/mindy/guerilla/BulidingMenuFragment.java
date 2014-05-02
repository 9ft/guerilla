package com.mindy.guerilla;

import com.mindy.guerilla.BulidingFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BulidingMenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// String[] buildings =
		// getResources().getStringArray(R.array.class_names);
		String[] buildings = getResources().getStringArray(R.array.building_pos_name);

		ArrayAdapter<String> classesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, buildings);

		setListAdapter(classesAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = new BulidingFragment(position);
		// Fragment newContent = null;
		// switch (position) {
		// case 0:
		// newContent = new ClassFragment(R.color.red);
		// break;
		// case 1:
		// newContent = new ClassFragment(R.color.green);
		// break;
		// case 2:
		// newContent = new ClassFragment(R.color.blue);
		// break;
		// case 3:
		// newContent = new ClassFragment(android.R.color.white);
		// break;
		// case 4:
		// newContent = new ClassFragment(android.R.color.black);
		// break;
		// }
		if (newContent != null)
			switchFragment(newContent);
	}

	// 切换Fragment视图内ring
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		}
	}

}
