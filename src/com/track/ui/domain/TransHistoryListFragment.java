package com.track.ui.domain;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.loader.TransHistoryListLoader;
import com.track.misc.model.TransHistory;
import com.track.ui.adapter.TransHistoryAdapter;

public class TransHistoryListFragment extends ListFragment {
	private static final String ARG_ES_ID = "EsId";
	private TransHistoryAdapter mAdapter;
	private String mEsId;
	private TransHistoryListLoader mLoader;

	public static TransHistoryListFragment newInstance(String id) {

		TransHistoryListFragment fragment = new TransHistoryListFragment();
		Bundle bundle = new Bundle();
		bundle.putString(ARG_ES_ID, id);
		fragment.setArguments(bundle);
		Log.e("expid2", id);
		return fragment;
	}

	public TransHistoryListFragment() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		mAdapter = new TransHistoryAdapter(new ArrayList<TransHistory>(),
				this.getActivity());

		setListAdapter(mAdapter);

		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		registerForContextMenu(getListView());

		setEmptyText("暂无物流信息");

		if (getArguments() != null) {
			mEsId = getArguments().getString(ARG_ES_ID);
		}

		RefreshList();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.transhistory_edit, menu);
		super.onCreateOptionsMenu(menu, inflater);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.action_refresh:

			break;
		case R.id.action_map:
			Toast.makeText(getActivity(), "map", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void RefreshList() {
		// TODO Auto-generated method stub
		String id = mEsId;
		mLoader = new TransHistoryListLoader(mAdapter, this.getActivity());
		mLoader.LoadExpressTransHistoryList(id);
	}
}
