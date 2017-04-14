package io.gabrielcosta.githubpopular.model;

import android.support.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final class RetrofitBuilder {

  private static final RetrofitBuilder ourInstance = new RetrofitBuilder();

  public static RetrofitBuilder getInstance() {
    return ourInstance;
  }

  private RetrofitBuilder() {
  }

  @NonNull
  public Retrofit build(final String baseUrl) {
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

}
