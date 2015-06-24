package com.track.ui.adapter;

import java.text.SimpleDateFormat;
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
import com.track.misc.model.ExpressSheet;
import com.track.net.IDataAdapter;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class ExpressListAdapter extends ArrayAdapter<ExpressSheet> implements
		IDataAdapter<List<ExpressSheet>> {

	private List<ExpressSheet> itemList;
	private Context context;
	private String ex_type;

	public ExpressListAdapter(List<ExpressSheet> itemList, Context ctx,
			String ex_type) {
		super(ctx, R.layout.express_list_item, itemList);

		this.itemList = itemList;
		this.context = ctx;
		this.ex_type = ex_type;
	}

	@Override
	public int getCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}

	@Override
	public ExpressSheet getItem(int position) {
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
			v = inflater.inflate(R.layout.express_list_item, null);

			hd.name = (TextView) v.findViewById(R.id.name);
			hd.telCode = (TextView) v.findViewById(R.id.tel);
			hd.address = (TextView) v.findViewById(R.id.addr);
			hd.time = (TextView) v.findViewById(R.id.time);
			hd.status = (TextView) v.findViewById(R.id.st);

			v.setTag(hd);
		} else {
			hd = (hold) v.getTag();
		}

		ExpressSheet es = getItem(position);
		switch (ex_type) {
		case "ExDLV": // 派送
			if (es.getReceiver() != null) {
				hd.name.setText(es.getReceiver().getCname()); // 接收者姓名
				hd.telCode.setText(es.getReceiver().getTelcode()); // 接收者电话
				hd.address.setText(es.getReceiver().getAddress()); // 接收者
			}
			if (es.getAcceptetime() != null) {
				hd.time.setText(DateFormat.format("MM月dd日 HH:mm",
						es.getAcceptetime()));
			}
			break;
		case "ExRCV": // 揽收
			if (es.getSender() != null) {
				hd.name.setText(es.getSender().getCname()); // 发送者姓名
				hd.telCode.setText(es.getSender().getTelcode());
				hd.address.setText(es.getSender().getAddress());
			}
			if (es.getAcceptetime() != null) {
				hd.time.setText(DateFormat.format("MM月dd日 HH:mm",
						es.getAcceptetime()));
			}
			break;
		case "ExTAN": // 这个需要改
			if (es.getReceiver() != null) {
				hd.name.setText(es.getReceiver().getCname()); // 接收者姓名
				hd.telCode.setText(es.getReceiver().getTelcode()); // 接收者电话
				hd.address.setText(es.getReceiver().getAddress()); // 接收者
			}
			if (es.getAcceptetime() != null) {
				SimpleDateFormat myFmt = new SimpleDateFormat("MM月dd日 HH:mm");
				hd.time.setText(myFmt.format(es.getAcceptetime()));
			}
			break;
		case "UnPkg": // 这个需要改
			if (es.getReceiver() != null) {
				hd.name.setText(es.getReceiver().getCname()); // 接收者姓名
				hd.telCode.setText(es.getReceiver().getTelcode()); // 接收者电话
				hd.address.setText(es.getReceiver().getAddress()); // 接收者
			}
			if (es.getAcceptetime() != null) {
				SimpleDateFormat myFmt = new SimpleDateFormat("MM月dd日 HH:mm");
				hd.time.setText(myFmt.format(es.getAcceptetime()));
			}
			break;
		case "NPkg": // 这个需要改
			if (es.getReceiver() != null) {
				hd.name.setText(es.getReceiver().getCname()); // 接收者姓名
				hd.telCode.setText(es.getReceiver().getTelcode()); // 接收者电话
				hd.address.setText(es.getReceiver().getAddress()); // 接收者
			}
			if (es.getAcceptetime() != null) {
				SimpleDateFormat myFmt = new SimpleDateFormat("MM月dd日 HH:mm");
				hd.time.setText(myFmt.format(es.getAcceptetime()));
			}
			break;
		}

		String stText = "";
		switch (es.getStatus()) {
		case ExpressSheet.STATUS.STATUS_CREATED:
			stText = "新建";
			break;
		case ExpressSheet.STATUS.STATUS_RECEIVED:
			stText = "已揽收";
			break;
		case ExpressSheet.STATUS.STATUS_DELIVERIED:
			stText = "已交付";
			break;
		case ExpressSheet.STATUS.STATUS_DISPATCHED:
			stText = "派送中";
			break;
		case ExpressSheet.STATUS.STATUS_PARTATION:
			stText = "正在分拣";
			break;
		case ExpressSheet.STATUS.STATUS_TRANSPORT:
			stText = "转运";
			break;
		}
		hd.status.setText(stText);
		return v;
	}

	@Override
	public List<ExpressSheet> getData() {
		return itemList;
	}

	@Override
	public void setData(List<ExpressSheet> data) {
		this.itemList = data;
	}

	private class hold {
		TextView name;
		TextView telCode;
		TextView address;
		TextView time;
		TextView status;
	}
}