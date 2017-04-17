package io.gabrielcosta.githubpopular.model;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
        .client(buildOkHttpClient())
        .build();
  }

  private OkHttpClient buildOkHttpClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder httpClient =
        new OkHttpClient.Builder();
    httpClient.addInterceptor(logging);

    return httpClient.build();
  }

}
