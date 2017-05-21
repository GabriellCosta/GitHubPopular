package io.gabrielcosta.githubpopular;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by gabrielcosta on 21/05/17.
 */

public final class CustomApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }
}
