package com.hl5pma.rxexample;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.ReplaySubject;

@Singleton
public class ImageSearchManager {

    private static final String API_KEY = "12134b96690b90ec58897cb715d57a1e";
    private static final int ITEM_COUNT = 20;

    private DaumOpenApi mApi;

    private String mQuery;
    private ReplaySubject<ImageSearchResult> mSubject = ReplaySubject.create();
    private int mPageNumber;

    @Inject
    public ImageSearchManager(DaumOpenApi api) {
        mApi = api;
    }

    public Observable<ImageSearchResult> search(String query) {
        mQuery = query;
        mSubject = ReplaySubject.create();
        mPageNumber = 1;

        mApi.searchImage("json", API_KEY, query, ITEM_COUNT, mPageNumber)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubject::onNext);

        return mSubject;
    }

    public Observable<ImageSearchResult> loadMore() {
        mPageNumber++;

        mApi.searchImage("json", API_KEY, mQuery, ITEM_COUNT, mPageNumber)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubject::onNext);

        return mSubject;
    }

    public Observable<ImageSearchResult> getObservable() {
        return mSubject;
    }
}
