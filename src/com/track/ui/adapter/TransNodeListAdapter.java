package com.track.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.track.app.user.R;
import com.track.misc.model.TransNode;
import com.track.net.IDataAdapter;

public class TransNodeListAdapter extends ArrayAdapter<TransNode> implements
		IDataAdapter<List<TransNode>> {

	private List<TransNode> itemList;
	private Context context;

	public TransNodeListAdapter(List<TransNode> itemList, Context ctx) {
		super(ctx, R.layout.transnode_select_list, itemList);

		this.itemList = itemList;
		this.context = ctx;
	}

	@Override
	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	@Override
	public TransNode getItem(int position) {
		if (itemList != null)
			return itemList.get(position);
		return null;
	}

	public void setItem(TransNode ci, int position) {
		if (itemList != null)
			itemList.set(position, ci);
	}

	@Override
	public long getItemId(int position) {
		if (itemList != null)
			return itemList.get(position).hashCode();
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.transnode_select_list, null);
		}

		TransNode c = itemList.get(position);
		TextView text = (TextView) v.findViewById(android.R.id.text1);
		text.setText(c.getNodename());
		// 将position传进去
		text.setTag(position);
		return v;
	}

	@Override
	public List<TransNode> getData() {
		return itemList;
	}

	@Override
	public void setData(List<TransNode> data) {
		// TODO Auto-generated method stub
		itemList = data;
	}

}