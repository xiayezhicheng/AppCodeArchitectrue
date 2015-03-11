package com.android.appcodearchitecture.fragment.base;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.appcodearchitecture.R;
import com.android.appcodearchitecture.adapter.BaseListAdapter;
import com.android.appcodearchitecture.network.GsonRequest;
import com.android.appcodearchitecture.thirdparty.pulltorefresh.PtrClassicFrameLayout;
import com.android.appcodearchitecture.thirdparty.pulltorefresh.PtrDefaultHandler;
import com.android.appcodearchitecture.thirdparty.pulltorefresh.PtrFrameLayout;
import com.android.appcodearchitecture.thirdparty.pulltorefresh.PtrHandler;
import com.android.appcodearchitecture.util.ListViewUtils;
import com.android.appcodearchitecture.util.NetUtils;
import com.android.appcodearchitecture.view.BlankView;
import com.android.appcodearchitecture.view.LoadingFooter;
import com.android.appcodearchitecture.view.LoadingFooter.onClickLoadListener;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

//Todo 1,卡通风格的下拉刷新
public abstract class BaseRefreshListFragment<T> extends BaseFragment{

	private Context context;
	private PtrClassicFrameLayout mPtrFrame;
	private LoadingFooter mLoadingFooter;
	private ListView mListView;
	protected LinkedList<T> data;
	private BaseListAdapter<T> adapter;
	private View blankLayout;
	private boolean isInitLoad = true;//是否第一次加载
	private int mPage = 0;
	private int mCount = 10;
	private boolean isVisible ;
	private boolean isInit ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.ptr_list, null);

		//空白视图展示
		blankLayout = view.findViewById(R.id.blankLayout);
		
		mListView = (ListView) view.findViewById(R.id.recyclerView);
		mLoadingFooter = new LoadingFooter(context);
		mLoadingFooter.setOnClickLoadListener(new onClickLoadListener() {
			
			@Override
			public void onClick() {
				loadNextPage();
			}
		});
		mListView.addFooterView(mLoadingFooter.getView());
		
		data = new LinkedList<T>();
		adapter = getAdapter();
		mListView.setAdapter(adapter);
		
		mPtrFrame = (PtrClassicFrameLayout)view.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            	isInitLoad = false;
            	loadFirstPage();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false(release to refresh )true is mean pulling to refresh
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);

		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (mLoadingFooter.getState() == LoadingFooter.State.Idle) {
					if (firstVisibleItem + visibleItemCount >= totalItemCount
							&& totalItemCount != 0
							&& totalItemCount != mListView.getHeaderViewsCount()+mListView.getFooterViewsCount()
							&& adapter.getCount() > 0) {
						loadNextPage();
					}
				}else if (mLoadingFooter.getState() == LoadingFooter.State.InvalidateNet) {
					if (!mLoadingFooter.getView().isShown()) {
						mLoadingFooter.setState(LoadingFooter.State.Idle);
					}
				}

			}
		});
		
		//延迟加载
		isInit = true;
		onFirstLoad();
		return view;
	}

	private void loadData(final int page) {
		final boolean isRefreshFromTop = page == 0;
		
		GsonRequest<T[]> request = new GsonRequest<T[]>(String.format(getUrlString(), page, mCount), getDataType(),
				new Listener<T[]>() {

					@Override
					public void onResponse(final T[] response) {

						NetUtils.executeAsyncTask(new AsyncTask<Object, Object, LinkedList<T>>(){

							@Override
							protected LinkedList<T> doInBackground(Object... params) {
								LinkedList<T> temp = new LinkedList<T>();
								for (T object:response) {
									temp.add(object);
								}
								return temp;
							}

							@Override
							protected void onPostExecute(LinkedList<T> result) {
								super.onPostExecute(result);
								if (isRefreshFromTop) {
									data.clear();
									if(isInitLoad){
										setShowLoading(false);
									}else{
										mPtrFrame.refreshComplete();
									}
								}
								if(result.size()<mCount){
									mLoadingFooter.setState(LoadingFooter.State.TheEnd);
								}else{
									mLoadingFooter.setState(LoadingFooter.State.Idle);
								}
								mPage = page;
								data.addAll(result);
								adapter.notifyDataSetChanged();
								BlankView.setBlank(data.size(), getFragment(), true, blankLayout, onClickRetry);
							}

						});
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						if (isRefreshFromTop) {
							if(isInitLoad){
								setShowLoading(false);
							}else{
								mPtrFrame.refreshComplete();
							}
						} else {
							mLoadingFooter.setState(LoadingFooter.State.Idle);
						}
						if (!NetUtils.isConnect(context)) {
							if(isRefreshFromTop){
								if (!data.isEmpty()) {
									Toast.makeText(context, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
								}
							}else{
								mLoadingFooter.setState(LoadingFooter.State.InvalidateNet);
							}
						
						}
						BlankView.setBlank(data.size(), getFragment(), false, blankLayout, onClickRetry);
					}
				}
		);
		
		request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		executeRequest(request);
		
	}

	private void loadNextPage() {
		mLoadingFooter.setState(LoadingFooter.State.Loading);
		loadData(mPage+1);
	}

	protected void loadFirstPage() {
		BlankView.setBlank(1, getFragment(), true, blankLayout, onClickRetry);//隐藏空白视图
		if(isInitLoad) setShowLoading(true);
		mPage = 0;
		loadData(mPage);
	}

	View.OnClickListener onClickRetry = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	isInitLoad = true;
			loadFirstPage();
        }
    };
	
	private void setShowLoading(boolean isShowLoading){
		if(isShowLoading){
			mPtrFrame.setVisibility(View.GONE);
			showDialogLoading();
		}else{
			mPtrFrame.setVisibility(View.VISIBLE);
			hideProgressDialog();
		}
	}
	
	public void loadFirstPageAndScrollToTop() {
		ListViewUtils.smoothScrollListViewToTop(mListView);
		loadFirstPage();
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isVisible = isVisibleToUser;
		//显示该fragment之前载入activity的时候就已经初始化完成
		onFirstLoad();
	}
	
	public void onFirstLoad() {
		//延迟加载(懒加载)
		if (data != null && data.size() == 0) {
			if(isVisible&&isInit){
				loadFirstPage();
			}
		}
	}
	
	protected abstract Object getFragment();
	
	protected abstract BaseListAdapter<T> getAdapter();
	
	protected abstract Class<T[]> getDataType();
	
	protected abstract String getUrlString();
}