package com.android.appcodearchitecture.fragment.base;

import android.support.v4.app.Fragment;

import com.android.appcodearchitecture.activity.BaseFragmentActivity;
import com.android.appcodearchitecture.network.RequestManager;
import com.android.volley.Request;


public class BaseFragment extends Fragment {

	protected void executeRequest(Request request) {
	        RequestManager.addRequest(request, this);
    }
	
    @Override
    public void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }
   
    public void showDialogLoading() {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).showDialogLoading();
        }
    }

    public void hideProgressDialog() {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).hideProgressDialog();
        }
    }
}
