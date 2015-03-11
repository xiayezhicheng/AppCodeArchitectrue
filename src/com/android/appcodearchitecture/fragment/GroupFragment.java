package com.android.appcodearchitecture.fragment;

import com.android.appcodearchitecture.adapter.BaseListAdapter;
import com.android.appcodearchitecture.adapter.GroupAdapter;
import com.android.appcodearchitecture.fragment.base.BaseRefreshListFragment;
import com.android.appcodearchitecture.model.Group;
import com.android.appcodearchitecture.vendor.API;


public class GroupFragment extends BaseRefreshListFragment<Group> {

	@Override
	protected BaseListAdapter<Group> getAdapter() {
		return new GroupAdapter(getActivity(), data);
	}

	@Override
	protected Class<Group[]> getDataType() {
		return Group[].class;
	}

	@Override
	protected String getUrlString() {
		return API.GROUP;
	}

	@Override
	protected Object getFragment() {
		return this;
	}

}
