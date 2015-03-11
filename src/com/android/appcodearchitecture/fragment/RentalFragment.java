package com.android.appcodearchitecture.fragment;


import android.content.Context;

import com.android.appcodearchitecture.adapter.BaseListAdapter;
import com.android.appcodearchitecture.adapter.RentalAdapter;
import com.android.appcodearchitecture.fragment.base.BaseRefreshListFragment;
import com.android.appcodearchitecture.model.Rental;
import com.android.appcodearchitecture.vendor.API;

public class RentalFragment extends BaseRefreshListFragment<Rental>{

	@Override
	protected BaseListAdapter<Rental> getAdapter() {
		return new RentalAdapter(getActivity(), data);
	}

	@Override
	protected Class<Rental[]> getDataType() {
		return Rental[].class;
	}

	@Override
	protected String getUrlString() {
		return API.RENTAL;
	}

	@Override
	protected Object getFragment() {
		return this;
	}
	
}
