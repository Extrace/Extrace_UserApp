package com.track.app.user.fragment;

import com.track.app.user.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private Context context;

	public MenuAdapter(Context context) {

		this.context = context;

	}

	public void ChangeImg(int index, Integer dreawble)

	{

		images[index] = dreawble;

	}

	private Integer[] images = { R.drawable.menu_add_icon,
			R.drawable.menu_add_icon, R.drawable.menu_add_icon,
			R.drawable.menu_add_icon, R.drawable.menu_add_icon,

	};

	private Integer[] texts = { R.string.title_section1,
			R.string.title_section2, R.string.title_section3,
			R.string.title_section4, R.string.about, };

	@Override
	public int getCount() {

		// TODO Auto-generated method stub

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
