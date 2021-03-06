package com.android.appcodearchitecture.adapter;

import android.util.SparseArray;
import android.view.View;

/** Viewholder的简化
  * @ClassName: ViewHolder
  * @Description: TODO
  * @author wanghao
  * @date 2014-10-2
  */
public class ViewHolder {
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
