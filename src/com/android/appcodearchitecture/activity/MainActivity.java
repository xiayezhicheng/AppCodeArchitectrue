package com.android.appcodearchitecture.activity;

import java.util.ArrayList;

import com.android.appcodearchitecture.R;
import com.android.appcodearchitecture.adapter.FragmentAdapter;
import com.android.appcodearchitecture.fragment.GroupFragment;
import com.android.appcodearchitecture.fragment.RentalFragment;
import com.android.appcodearchitecture.listener.PagerListener;
import com.android.appcodearchitecture.listener.TabListener;
import com.android.appcodearchitecture.view.ScrollViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public  class MainActivity extends BaseFragmentActivity{

    private ScrollViewPager viewPager = null;
    private boolean canScroll;
    private Button tab1 = null;
    private Button tab2 = null;
    //存放tab的数组
    private ArrayList<Button> tabArray = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        initTab();
        setListener();
    
    }
    private void initTab() {
        tab1 = (Button)findViewById(R.id.perimeter_group);
        tab2 = (Button)findViewById(R.id.perimeter_rental);
        
        tabArray = new ArrayList<Button>();
        tabArray.add(tab1);
        tabArray.add(tab2);
    }
    
    private void initViewPager() {
        //获取到ViewPager的实例
        viewPager = (ScrollViewPager)findViewById(R.id.fragment_container);
        viewPager.setCanScroll(true);
        //构造好存放Fragment的数组
        ArrayList<Fragment> fragmentArray = new ArrayList<Fragment>();
        fragmentArray.add(new GroupFragment());
        fragmentArray.add(new RentalFragment());
        
        //为ViewPager设置适配器
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentArray));
        //设置当前显示的页面
        viewPager.setCurrentItem(0);
    }
    
    private void setListener() {
        TabListener tl = new TabListener(this.viewPager);
        
        tab1.setOnClickListener(tl);
        tab2.setOnClickListener(tl);
        PagerListener pagerListener = new PagerListener.Builder(tabArray, 0).selectedBgColor(Color.parseColor("#1ABC9C"))
        		.unSelectedBgColor(Color.parseColor("#FFFFFF")).build();
        viewPager.setOnPageChangeListener(pagerListener);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.action_iscanscroll:
				if (viewPager.getCanScroll()) {
					viewPager.setCanScroll(false);
				}else {
					viewPager.setCanScroll(true);
				}
				return true;
	
		}
		
		return false;
	}

    
    
}

