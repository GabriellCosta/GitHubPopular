package io.gabrielcosta.githubpopular.ui.activity;

import io.gabrielcosta.githubpopular.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "./AndroidManifest.xml")
public class PullListActivityTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfNoExtraWasPassed() {
    Robolectric.buildActivity(PullListActivity.class)
        .create()
        .start();
  }

}