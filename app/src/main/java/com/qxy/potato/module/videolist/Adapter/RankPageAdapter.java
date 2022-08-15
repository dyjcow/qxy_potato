package com.qxy.potato.module.videolist.Adapter;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



import java.util.List;

/**
 * @author Soul Mate
 * @brief viewPager的Adapter
 * @date 2022-08-14 15:02
 */

public class RankPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragmentList;
	private List<String> mTitleList ;

	public RankPageAdapter(@NonNull FragmentManager fm) {
		super(fm);
	}

	/**
	 *
	 * @param fm
	 * @param fragmentList
	 * @param titleList 标题名
	 */
	public RankPageAdapter(@NonNull FragmentManager fm,List<Fragment> fragmentList,List<String> titleList){
		super(fm);
		mTitleList = titleList;
		mFragmentList = fragmentList;
	}


	@NonNull @Override public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override public int getCount() {
		return mFragmentList.size();
	}

	/**
	 * 显示标题
	 * @param position
	 * @return
	 */
	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return mTitleList.get(position);
	}

}
