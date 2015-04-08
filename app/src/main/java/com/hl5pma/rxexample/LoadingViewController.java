package com.hl5pma.rxexample;

import android.view.View;

public class LoadingViewController {

    public interface OnRetryListener {
        public void onRetry();
    }

    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mRetryButton;

    private OnRetryListener mRetryListener;

    public LoadingViewController(View contentView, View loadingView, View errorView) {
        mContentView = contentView;
        mLoadingView = loadingView;
        mErrorView = errorView;
        mErrorView.findViewById(R.id.retry_button).setOnClickListener(v -> {
            if (mRetryListener != null) {
                mRetryListener.onRetry();
            }
        });
    }

    public void startLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    public void showContent() {
        mContentView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    public void error() {
        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void setOnRetryListener(OnRetryListener retryListener) {
        mRetryListener = retryListener;
    }
}
