package org.d3ifcool.dailyactivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.apache.http.conn.ConnectTimeoutException;

/**
 * Created by aldiand on 24/10/17.
 */

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    private String tabTitles[] = new String[] { "Jadwal", "Kegiatan"};
    private Context mContext;

    public CategoryFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CategoryFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new JadwalFragment();
        } else  {
            return new KegiatanFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Jadwal";
        } else {
            return "Kegiatan";
        }
    }
}
