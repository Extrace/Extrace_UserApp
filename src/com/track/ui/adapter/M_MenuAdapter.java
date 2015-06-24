package com.track.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.track.app.user.R;

public class M_MenuAdapter extends BaseAdapter {

	private Context context;

	public M_MenuAdapter(Context context) {

		this.context = context;

	}

	public void ChangeImg(int index, Integer dreawble)

	{

		images[index] = dreawble;

	}

	private Integer[] images = { R.drawable.menu_index,

	R.drawable.menu_pack, R.drawable.menu_pack, R.drawable.menu_pack,
			R.drawable.menu_customer2, R.drawable.menu_about

	};

	private Integer[] texts = { R.string.menu_index, R.string.menu_unpack,
			R.string.menu_pack, R.string.menu_receivepkg,
			R.string.menu_customermanage, R.string.menu_about };

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {

		return position;

	}

	// get the current selector's id number

	@Override
	public long getItemId(int position) {

		return position;

	}

	private class ImgTextWrapper {

		public ImageView imageView;
		public TextView textView;

	}

	@Override
	public View getView(int position, View view, ViewGroup viewgroup) {

		ImgTextWrapper wrapper;

		if (view == null) {

			wrapper = new ImgTextWrapper();
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.list_item, null);
			view.setTag(wrapper);
			view.setPadding(10, 10, 10, 10); // 每格的间距

		} else {
			wrapper = (ImgTextWrapper) view.getTag();
		}

		wrapper.imageView = (ImageView) view.findViewById(R.id.id_DrawerImage);
		wrapper.imageView.setBackgroundResource(images[position]);
		wrapper.textView = (TextView) view.findViewById(R.id.id_DrawerText);
		wrapper.textView.setTextColor(Color.BLACK);
		wrapper.textView.setText(texts[position]);

		return view;

	}

}
