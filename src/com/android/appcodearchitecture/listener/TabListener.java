package com.android.appcodearchitecture.listener;


import com.android.appcodearchitecture.R;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;


public class TabListener implements OnClickListener {
    
    private ViewPager viewPager = null;
    
    public TabListener(ViewPager v) {
        this.viewPager = v;
    }
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.perimeter_group:
            this.viewPager.setCurrentItem(0);
            break;
        case R.id.perimeter_rental:
            this.viewPager.setCurrentItem(1);
            break;
        }
    }
}