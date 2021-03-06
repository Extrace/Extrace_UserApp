package com.track.ui.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.track.app.user.R;
import com.track.ui.minor.MyCenterTabFragment;
import com.track.ui.minor.TransNodeTabFragment;
import com.track.ui.minor.TransPackageTabFragment;

public class PlaceHolderFragment extends Fragment {

	private TransNodeTabFragment mTransNodeTabFragment;
	private TransPackageTabFragment mTransPackageTabFragment;
	private MyCenterTabFragment mCenterTabFragment;
	private static ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;
	@SuppressWarnings("unused")
	private static ImageView mTabLine;

	private TextView mPackageTextView;
	private TextView mNodeTextView;
	private TextView mCenterTextView;

	private ImageView mPackageImageView;
	private ImageView mNodeImageView;
	private ImageView mCenterImageView;

	@SuppressWarnings("unused")
	private int mCurrentIndex;
	@SuppressWarnings("unused")
	private int screenWidth1_3;

	private final static String ARG_SECTION_NUMBER = "section_number";

	// 获取选中item的参数，并传递给Bundle
	public static Fragment newInstance(int sectionNumber) {
		PlaceHolderFragment fragment = new PlaceHolderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		InitViewPager(rootView);
		// initTabLine(rootView);
		InitTextView(rootView);
		InitImageView(rootView);

		return rootView;
	}

	private void InitImageView(View rootView) {
		mPackageImageView = (ImageView) rootView
				.findViewById(R.id.id_iv_package);
		mNodeImageView = (ImageView) rootView.findViewById(R.id.id_iv_node);
		mCenterImageView = (ImageView) rootView
				.findViewById(R.id.id_iv_mycenter);
	}

	private void InitTextView(View rootView) {
		mPackageTextView = (TextView) rootView.findViewById(R.id.id_tv_package);
		mNodeTextView = (TextView) rootView.findViewById(R.id.id_tv_node);
		mCenterTextView = (TextView) rootView.findViewById(R.id.id_tv_mycenter);
		setClickListener();
	}

	private void InitViewPager(View rootView) {
		mViewPager = (ViewPager) rootView.findViewById(R.id.id_viewpager);
		mFragments = new ArrayList<Fragment>();

		mTransNodeTabFragment = new TransNodeTabFragment();
		mTransPackageTabFragment = new TransPackageTabFragment();
		mCenterTabFragment = new MyCenterTabFragment();

		mFragments.add(mTransPackageTabFragment);
		mFragments.add(mTransNodeTabFragment);
		mFragments.add(mCenterTabFragment);

		mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}
		};

		mViewPager.setAdapter(mAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				// 每当其中一个fragment被选中时，其他文本恢复为黑色
				resetTextColor();
				resetImage();
				// 当fragment被选中时，分别设置文本标题颜色
				switch (position) {
				case 0:
					mPackageTextView.setTextColor(Color.parseColor("#87CEEB"));
					mPackageImageView
							.setImageResource(R.drawable.id_title_package_selected);
					break;
				case 1:
					mNodeTextView.setTextColor(Color.parseColor("#87CEEB"));
					mNodeImageView
							.setImageResource(R.drawable.id_title_node_selected);
					break;
				case 2:
					mCenterTextView.setTextColor(Color.parseColor("#87CEEB"));
					mCenterImageView
							.setImageResource(R.drawable.id_title_mycenter_selected);
					break;
				}
				mCurrentIndex = position;
			}

			// 当fragment滑动时，需要设置的代码
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPx) {
				// LinearLayout.LayoutParams lp =
				// (android.widget.LinearLayout.LayoutParams) mTabLine
				// .getLayoutParams();
				// if (mCurrentIndex == position) {
				// lp.leftMargin = (int) (screenWidth1_3 * (mCurrentIndex +
				// positionOffset));
				// } else {
				// lp.leftMargin = (int) (screenWidth1_3 * (mCurrentIndex - 1 +
				// positionOffset));
				// }
				// mTabLine.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// 当回到MainActivity时，调用onSectionAttached
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}

	/**
	 * 初始化title2下面的tabline
	 */
	// private void initTabLine(View rootView) {
	// mTabLine = (ImageView) rootView.findViewById(R.id.id_iv_tabline);
	// Display display = getActivity().getWindowManager().getDefaultDisplay();
	// DisplayMetrics outMetrics = new DisplayMetrics();
	// display.getMetrics(outMetrics);
	// screenWidth1_3 = outMetrics.widthPixels / 3;
	// LayoutParams lp = mTabLine.getLayoutParams();
	// lp.width = screenWidth1_3;
	// mTabLine.setLayoutParams(lp);
	// }

	private void setClickListener() {
		mNodeTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1);
			}
		});
		mPackageTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0);
			}
		});

		mCenterTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(2);
			}
		});

	}

	OnClickListener mNoClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
		}
	};

	// 重置title2的文本颜色为黑色
	protected void resetTextColor() {
		mPackageTextView.setTextColor(Color.BLACK);
		mNodeTextView.setTextColor(Color.BLACK);
		mCenterTextView.setTextColor(Color.BLACK);
	}

	protected void resetImage() {
		mPackageImageView.setImageResource(R.drawable.title_express);
		mNodeImageView.setImageResource(R.drawable.id_title_node_normal);
		mCenterImageView.setImageResource(R.drawable.title_mycenter);
	}

}
