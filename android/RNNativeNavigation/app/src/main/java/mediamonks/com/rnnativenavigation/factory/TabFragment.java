package mediamonks.com.rnnativenavigation.factory;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import mediamonks.com.rnnativenavigation.data.TabNode;
import mediamonks.com.rnnativenavigation.data.TitleNode;

public class TabFragment extends BaseFragment<TabNode> implements BottomNavigationView.OnNavigationItemSelectedListener
{
	/**
	 * Created by erik on 10/08/2017.
	 * RNNativeNavigation 2017
	 */
	private static class TabPagerAdapter extends FragmentPagerAdapter
	{
		private ArrayList<TitleNode> _items;
		private SparseArray<BaseFragment> _fragments;

		TabPagerAdapter(FragmentManager fm, ArrayList<TitleNode> items)
		{
			super(fm);

			this._items = items;
			this._fragments = new SparseArray<>(this._items.size());
		}

		@Override
		public Fragment getItem(int position)
		{
			try
			{
				if (_fragments.get(position) == null)
				{
					_fragments.put(position, _items.get(position).getFragment());
				}
				return _fragments.get(position);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public int getCount()
		{
			return _items.size();
		}

		SparseArray<BaseFragment> getFragments()
		{
			return _fragments;
		}
	}

	private ViewPager _viewPager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		TabPagerAdapter adapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), getNode().getTabs());

		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		_viewPager = new ViewPager(getContext());
		_viewPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
		_viewPager.setId(View.generateViewId());
		_viewPager.setAdapter(adapter);
		_viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			int page;

			@Override
			public void onPageSelected(int position)
			{
				super.onPageSelected(position);

//				TabPagerAdapter adapter = (TabPagerAdapter) _viewPager.getAdapter();
//				BaseFragment fragment = adapter.getFragments().get(_viewPager.getCurrentItem());
//				FragmentTransaction transition = getActivity().getSupportFragmentManager().beginTransaction();
//				transition.detach(fragment);
//				transition.commit();

				page = position;
			}
		});
		linearLayout.addView(_viewPager);

		BottomNavigationView bottomNavigationView = new BottomNavigationView(getContext());
		bottomNavigationView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		int[][] states = new int[][]{
				new int[]{android.R.attr.state_enabled}, // enabled
				new int[]{android.R.attr.state_checkable}, // disabled
		};
		int[] colors = new int[]{
				Color.WHITE,
				Color.GRAY,
		};
		for (TitleNode node : getNode().getTabs())
		{
			String title = node.getTitle();
			int i = getNode().getTabs().indexOf(node);
			bottomNavigationView.getMenu().add(0, i, i, title);
		}
		bottomNavigationView.setItemTextColor(new ColorStateList(states, colors));
		bottomNavigationView.setOnNavigationItemSelectedListener(this);
		linearLayout.addView(bottomNavigationView);

		bottomNavigationView.setSelectedItemId(getNode().getSelectedTab());

		return linearLayout;
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item)
	{
		_viewPager.setCurrentItem(item.getItemId(), true);
		return true;
	}

	@Override
	public boolean onBackPressed()
	{
		TabPagerAdapter adapter = (TabPagerAdapter) _viewPager.getAdapter();
		BaseFragment fragment = adapter.getFragments().get(_viewPager.getCurrentItem());
		return fragment.onBackPressed();
	}
}