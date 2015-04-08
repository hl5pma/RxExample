package com.hl5pma.rxexample;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface DaumOpenApi {
    @GET("/search/image")
    Observable<ImageSearchResult> searchImage(@Query("output") String output,
                                        @Query("apikey") String apikey,
                                        @Query("q") String q,
                                        @Query("result") int result,
                                        @Query("pageno") int pageno);
}
