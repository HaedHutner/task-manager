package dev.mvvasilev.taskmanager.ui.main;

import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import dev.mvvasilev.taskmanager.R;
import dev.mvvasilev.taskmanager.ui.main.fragment.CreateTaskFragment;
import dev.mvvasilev.taskmanager.ui.main.fragment.ListTasksFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};

    private final Context mContext;

    private SparseArray<Fragment> fragments = new SparseArray<>();

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragments.append(0, new ListTasksFragment());
        fragments.append(1, new CreateTaskFragment());
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}