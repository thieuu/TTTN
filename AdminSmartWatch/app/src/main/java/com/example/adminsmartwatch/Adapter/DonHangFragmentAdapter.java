package com.example.adminsmartwatch.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.adminsmartwatch.Activity.Fragment.ChoDuyetFragment;
import com.example.adminsmartwatch.Activity.Fragment.DaGiaoFragment;
import com.example.adminsmartwatch.Activity.Fragment.DaHuyFragment;
import com.example.adminsmartwatch.Activity.Fragment.DangGiaoFragment;

public class DonHangFragmentAdapter extends FragmentStatePagerAdapter {
    public DonHangFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChoDuyetFragment();
            case 1:
                return new DangGiaoFragment();
            case 2:
                return new DaGiaoFragment();
            case 3:
                return new DaHuyFragment();
            default:
                return new ChoDuyetFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Chờ Duyệt";
                break;
            case 1:
                title = "Đang Giao";
                break;
            case 2:
                title = "  Đã Giao  ";
                break;
            case 3:
                title = "   Đã Hủy  ";
                break;
        }
        return title;
    }
}
