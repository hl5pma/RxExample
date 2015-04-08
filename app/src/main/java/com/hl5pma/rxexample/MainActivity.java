package com.hl5pma.rxexample;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.Subscriptions;


public class MainActivity extends BaseActivity {

    private Subscription mSearchImageSubscribe = Subscriptions.empty();

    @Inject ImageSearchManager mImageSearchManager;

    @InjectView(R.id.search_edit) EditText mSearchEdit;
    @InjectView(R.id.recycler_view) RecyclerView mRecyclerView;

    private ImageAdapter mAdapter;

    private LoadingViewController mLoadingViewController;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mLoadingViewController = new LoadingViewController(
                findViewById(R.id.content_view), findViewById(R.id.loading_view), findViewById(R.id.error_view));
        mLoadingViewController.setOnRetryListener(() -> search(mSearchEdit.getText().toString()));
        mLoadingViewController.showContent();

        mSearchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mAdapter.clear();
                search(v.getText().toString());
                Utils.hideSoftKeyboard(this, v.getWindowToken());
                return true;
            }
            return false;
        });

        final int columnCount = getResources().getInteger(R.integer.column_count);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ImageAdapter(this);
        mAdapter.setLoadMoreListener(mImageSearchManager::loadMore);
        mRecyclerView.setAdapter(mAdapter);

        mSearchImageSubscribe = mImageSearchManager.getObservable()
                .subscribe(this::updateSearchImage,
                        t -> {
                            mLoadingViewController.error();
                            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        });
    }

    private void search(String query) {
        mLoadingViewController.startLoading();
        mSearchImageSubscribe = mImageSearchManager.search(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateSearchImage);
    }

    private void updateSearchImage(ImageSearchResult imageSearchResult) {
        mLoadingViewController.showContent();
        mAdapter.addItems(imageSearchResult.channel.item);
        if (imageSearchResult.channel.totalCount > mAdapter.getItemCount()) {
            mAdapter.setHasMore(true);
        } else {
            mAdapter.setHasMore(false);
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mSearchImageSubscribe.unsubscribe();
    }
}
