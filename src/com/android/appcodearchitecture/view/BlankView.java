package com.android.appcodearchitecture.view;

import com.android.appcodearchitecture.R;
import com.android.appcodearchitecture.fragment.GroupFragment;
import com.android.appcodearchitecture.fragment.RentalFragment;

import android.view.View;
import android.widget.TextView;

public class BlankView {

	/**异常发生时显示的空白视图辅助类
	 * @author wanghao
	 * @since 2015-3-10 下午1:24:46
	 * @param itemSize 数据的数目，<code>itemSize == 0</code>时，不会显示空白视图
	 * @param fragment 当前的fragment，当request为true时，可以定义对应fragment显示的图片和文字
	 * @param request 为true时，会显示重试按钮，否则不会显示
	 * @param v 空白视图，一般是view_exception生成的
	 * @param onClick 重试按钮的点击动作
	 * @return void
	 */
	public static void setBlank(int itemSize, Object fragment, boolean request, View v, View.OnClickListener onClick) {
        View btn = v.findViewById(R.id.btnRetry);
        if (request) {
            btn.setVisibility(View.INVISIBLE);
        } else {
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(onClick);
        }

        setBlank(itemSize, fragment, request, v);
    }

    private static void setBlank(int itemSize, Object fragment, boolean request, View v) {
        boolean show = (itemSize == 0);
        if (!show) {
            v.setVisibility(View.GONE);
            return;
        }
        v.setVisibility(View.VISIBLE);

        int iconId = R.drawable.bg_exception;
        String text = "";

        if (request) {
            if (fragment instanceof GroupFragment) {
                iconId = R.drawable.bg_empty;
                text = "还没有团购\n您可以去其它地方看看哦";

            } else if (fragment instanceof RentalFragment) {
                iconId = R.drawable.bg_empty;
                text = "还没有出租房\n您可以去其它地方看看哦";

            }

        } else {
            iconId = R.drawable.bg_wifi;
            text = "获取数据失败\n请检查下网络是否通畅";
        }

        v.findViewById(R.id.icon).setBackgroundResource(iconId);
        ((TextView) v.findViewById(R.id.message)).setText(text);
    }
}
