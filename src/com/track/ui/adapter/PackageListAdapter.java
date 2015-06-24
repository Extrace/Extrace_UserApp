package com.track.ui.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.track.app.user.R;
import com.track.misc.model.Package;
import com.track.net.IDataAdapter;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class PackageListAdapter extends ArrayAdapter<Package> implements
		IDataAdapter<List<Package>> {

	private List<Package> itemList;
	private Context context;

	public PackageListAdapter(List<Package> itemList, Context ctx) {
		super(ctx, R.layout.package_list_item, itemList);
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
	public Package getItem(int position) {
		if (itemList != null)
			return itemList.get(position);
		return null;
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
		hold hd = null;
		if (v == null) {
			hd = new hold();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.package_list_item, null);

			hd.id = (TextView) v.findViewById(R.id.id);
			hd.targetNode = (TextView) v.findViewById(R.id.target);
			hd.time = (TextView) v.findViewById(R.id.time);
			hd.status = (TextView) v.findViewById(R.id.st);

			v.setTag(hd);
		} else {
			hd = (hold) v.getTag();
		}

		Package pkg = getItem(position);
		if (pkg != null) {
			hd.id.setText(pkg.getId()); // 包裹id
			hd.targetNode.setText(pkg.getTargetTransNode().getNodename()); // 接收者
		}
		if (pkg.getCreatetime() != null) {
			hd.time.setText(DateFormat.format("MM月dd日 HH:mm",
					pkg.getCreatetime()));
		}

		String stText = "";
		switch (pkg.getStatus()) {
		case Package.STATUS.STATUS_CREATED:
			stText = "新建";
			break;
		case Package.STATUS.STATUS_DELIVED:
			stText = "揽收货篮";
			break;
		case Package.STATUS.STATUS_DISPACHED:
			stText = "派送货篮";
			break;
		case Package.STATUS.STATUS_PACKED:
			stText = "打包";
			break;
		case Package.STATUS.STATUS_PARTATION:
			stText = "分拣货篮";
			break;
		case Package.STATUS.STATUS_TRANSPORT:
			stText = "转运";
			break;
		}
		hd.status.setText(stText);
		return v;
	}

	@Override
	public List<Package> getData() {
		return itemList;
	}

	@Override
	public void setData(List<Package> data) {
		this.itemList = data;
	}

	private class hold {
		TextView id;
		TextView targetNode;
		TextView time;
		TextView status;
	}
}