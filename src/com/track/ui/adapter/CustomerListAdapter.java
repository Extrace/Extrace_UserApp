package com.track.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.track.app.user.R;
import com.track.misc.model.Customer;
import com.track.net.IDataAdapter;

public class CustomerListAdapter extends ArrayAdapter<Customer> implements
		IDataAdapter<List<Customer>> {

	private List<Customer> itemList;
	private Context context;

	// private GenFilter filter;

	public CustomerListAdapter(List<Customer> itemList, Context ctx) {
		super(ctx, R.layout.customer_select_list, itemList);
		// super(ctx, android.R.layout.simple_list_item_single_choice,
		// itemList);

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
	public Customer getItem(int position) {
		if (itemList != null)
			return itemList.get(position);
		return null;
	}

	public void setItem(Customer ci, int position) {
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
			v = inflater.inflate(R.layout.customer_select_list, null);
		}

		Customer c = itemList.get(position);
		TextView text = (TextView) v.findViewById(android.R.id.text1);
		text.setText(c.getCname());
		// 将position传进去
		text.setTag(position);
		return v;
	}

	@Override
	public List<Customer> getData() {
		return itemList;
	}

	@Override
	public void setData(List<Customer> data) {
		this.itemList = data;
	}
}