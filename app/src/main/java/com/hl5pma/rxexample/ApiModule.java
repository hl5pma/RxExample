package com.hl5pma.rxexample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

@Module(library = true)
public class ApiModule {

    @Singleton @Provides Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint("http://apis.daum.net");
    }

    @Singleton @Provides Converter provideConverter() {
        Gson gson = new GsonBuilder().create();
        return new GsonConverter(gson);
    }

    @Singleton @Provides RestAdapter provideRestAdapter(Endpoint endpoint, Converter converter) {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setConverter(converter)
                .build();
    }

    @Singleton @Provides DaumOpenApi provideApi(RestAdapter restAdapter) {
        return restAdapter.create(DaumOpenApi.class);
    }
}
