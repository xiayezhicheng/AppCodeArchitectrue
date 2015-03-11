package com.android.appcodearchitecture.listener;

import java.util.ArrayList;

import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Button;

public class PagerListener implements OnPageChangeListener {
    
    private final static String TAG = PagerListener.class.getSimpleName();
    private final ArrayList<Button> tabArray;
    private final int initPos;
    private final int unSelectedBgColor;
    private final int selectedBgColor;
    
    public static class Builder{
    	//必须的参数
    	private final ArrayList<Button> tabArray;
    	private final int initPos;//initPos是指初 始化的位置
    	
    	//可选择的参数
    	private int unSelectedBgColor = Color.parseColor("#FFFFFF");
        private int selectedBgColor = Color.parseColor("#1ABC9C");
        
        public Builder(ArrayList<Button> tabArray, int initPos){
        	this.tabArray = tabArray;
        	this.initPos = initPos;
        }
        
        public Builder unSelectedBgColor(int unSelectedBgColor){
        	this.unSelectedBgColor = unSelectedBgColor;
        	return this;
        }
        
        public Builder selectedBgColor(int selectedBgColor){
        	this.selectedBgColor = selectedBgColor;
        	return this;
        }
        
        public PagerListener build(){
        	return new PagerListener(this);
        }
    }
    
    private PagerListener(Builder builder){
    	this.tabArray = builder.tabArray;
    	this.initPos = builder.initPos;
    	this.unSelectedBgColor = builder.unSelectedBgColor;
    	this.selectedBgColor = builder.selectedBgColor;
    	this.onPageSelected(initPos);
    }
    
    @Override
    //有三种状态（0，1，2）。arg0 ==1的时默示正在滑动，arg0==2的时默示滑动完毕了，arg0==0的时默示什么都没做。
    //当页面开始滑动的时候，三种状态的变化顺序为（1，2，0）
    public void onPageScrollStateChanged(int arg0) {
        
    }

    @Override
    //这个方法是页面滚动的时候被调用，我们将在这个方法里完成对激活条的操作
    //arg0 :当前可见的第一个页的position
    //arg1:当前页面偏移的百分比
    //arg2:当前页面偏移的像素位置   230 126 34   211 84 0

    public void onPageScrolled(int arg0, float arg1, int arg2) {
        
    }

    @Override
    //页面跳转完后得到调用，arg0是你当前选中的页面的Position（位置编号）
    public void onPageSelected(int arg0) {
        
    	for(int page = 0;page<tabArray.size();page++){
    		if (arg0==page) {
    			tabArray.get(page).setBackgroundColor(selectedBgColor);
    			tabArray.get(page).setTextColor(unSelectedBgColor);
			}else {
				tabArray.get(page).setBackgroundColor(unSelectedBgColor);
				tabArray.get(page).setTextColor(selectedBgColor);
			}
    	}
    }
}
