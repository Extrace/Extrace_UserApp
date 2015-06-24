package com.track.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.track.app.user.R;
import com.track.misc.model.TransHistory;
import com.track.net.IDataAdapter;

public class TransHistoryAdapter extends ArrayAdapter<TransHistory> implements
		IDataAdapter<List<TransHistory>> {

	private List<TransHistory> itemList;
	private Context context;

	public TransHistoryAdapter(List<TransHistory> itemList, Context ctx) {
		super(ctx, R.layout.transhistory_list_item, itemList);
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
	public TransHistory getItem(int position) {
		if (itemList != null)
			return itemList.get(position);
		return null;
	}

	public void setItem(TransHistory th, int position) {
		if (itemList != null)
			itemList.set(position, th);
	}

	@Override
	public long getItemId(int position) {
		if (itemList != null)
			return itemList.get(position).hashCode();
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		hold h = null;
		if (v == null) {
			h = new hold();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.transhistory_list_item, null);
			h.timeDay = (TextView) v.findViewById(R.id.time_tv1);
			h.timeHour = (TextView) v.findViewById(R.id.time_tv2);
			h.img = (ImageView) v.findViewById(R.id.transhistory_iv);
			h.textNode = (TextView) v.findViewById(R.id.his_node);
			// h.textNode2 = (TextView) v.findViewById(R.id.his_node2);
			h.textOpe = (TextView) v.findViewById(R.id.his_ope1);
			h.textUser = (TextView) v.findViewById(R.id.his_user1);
			h.textUserTp = (TextView) v.findViewById(R.id.user);
		}
		TransHistory his = itemList.get(position);
		SimpleDateFormat myFmtDay = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat myFmtHour = new SimpleDateFormat("HH:mm");
		Log.e("his", his.toString());
		if (his.getActtime() != null && myFmtDay != null && h != null) {
			h.timeDay.setText(myFmtDay.format(his.getActtime()));
			h.timeHour.setText(myFmtHour.format(his.getActtime()));
		}
		if (h != null) {
			switch (position) {
			case 0:
				h.textNode.setText("快件");
				h.textOpe.setText("已揽收");
				h.textNode.setTextSize(16);
				h.textNode.setTextColor(Color.BLACK);
				h.textOpe.setTextSize(16);
				h.textOpe.setTextColor(Color.BLACK);
				h.textUserTp.setText("快递员：");
				h.textUser.setText(his.getUserFrom().getUname() + "  电话： "
						+ his.getUserFrom().getTelcode());
				h.textUserTp.setTextSize(16);
				h.textUserTp.setTextColor(Color.BLACK);
				h.textUser.setTextSize(16);
				h.textUser.setTextColor(Color.BLACK);
				h.timeDay.setTextColor(Color.BLACK);
				break;
			case 1:
				if (his.getPackageid().getTargetTransNode() != null) {
					h.textNode.setText(his.getPackageid().getTargetTransNode()
							.getNodename());
				}
				h.textUserTp.setText("管理员：");
				h.textOpe.setText("已拆包");
				h.textUser.setText(his.getUserTo().getUname());
				break;
			default:
				if (his.getUserTo() != null) {
					// 当快件交给管理员，且uidfrom和uidto相等
					if (his.getUidfrom() == his.getUidto()
							&& his.getUserTo().getRole() == 1) {
						h.textNode.setText(his.getPackageid()
								.getSourceTransNode().getNodename());
						h.textOpe.setText("已打包，将发往"
								+ his.getPackageid().getTargetTransNode()
										.getNodename());
						h.textUserTp.setText("管理员：");
						h.textUser.setText(his.getUserFrom().getUname());
						// 当快件交给管理员
					} else if (his.getUidfrom() != his.getUidto()
							&& his.getUserTo().getRole() == 1) {
						h.textNode.setText("快件到达"
								+ his.getPackageid().getTargetTransNode()
										.getNodename());
						h.textOpe.setText("");
						h.textUserTp.setText("管理员：");
						h.textUser.setText(his.getUserTo().getUname());
						// 当快件交给司机
					} else if (his.getUserTo().getRole() == 2) {
						h.textNode.setText("快件已离开"
								+ his.getPackageid().getSourceTransNode()
										.getNodename());
						h.textOpe.setText("");
						h.textUserTp.setText("司机：");
						h.textUser.setText(his.getUserTo().getUname());
						// 当快件交给快递员
					} else if (his.getUserTo().getRole() == 0) {
						h.textNode.setText("快件");
						h.textOpe.setText("正在派送，请保持手机畅通");
						h.textUserTp.setText("快递员：");
						h.textUser.setText(his.getUserTo().getUname()
								+ "  电话： " + his.getUserTo().getTelcode());
					}
				} else if (his.getUidto() == 0) {
					h.textNode.setText("快件");
					h.textOpe.setText("已签收");
					h.textUserTp.setText("快递员：");
					h.textUser.setText(his.getUserFrom().getUname());
					h.img.setImageResource(R.drawable.icon_ok);
					h.textNode.setTextSize(18);
					h.textNode.setTextColor(Color.BLUE);
					h.textOpe.setTextSize(18);
					h.textOpe.setTextColor(Color.BLUE);
					h.timeDay.setTextColor(Color.BLUE);
					h.timeHour.setTextColor(Color.BLUE);
				}

				break;
			}
		}
		// 将position传进去
		// text.setTag(position);
		return v;
	}

	@Override
	public List<TransHistory> getData() {
		return itemList;
	}

	@Override
	public void setData(List<TransHistory> data) {
		this.itemList = data;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	private class hold {
		TextView timeDay;
		TextView timeHour;
		ImageView img;
		TextView textOpe;
		TextView textUser;
		TextView textNode;
		TextView textUserTp;
		TextView textNode2;
	}

}
