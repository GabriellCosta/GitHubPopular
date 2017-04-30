package io.gabrielcosta.githubpopular.util;

import android.support.test.InstrumentationRegistry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by gabrielcosta on 29/04/17.
 */

public final class TestUtil {

  private TestUtil() {

  }

  /**
   * Read file from src/test/resource
   * @param path relative path from src/test/resource
   * @return resource as String
   */
  public static String read(final String path) {
    final StringBuilder builder = new StringBuilder();
    try {
      InputStream json = InstrumentationRegistry.getContext().getAssets().open(path);
      BufferedReader in =
          new BufferedReader(new InputStreamReader(json, "UTF-8"));
      String str;

      while ((str = in.readLine()) != null) {
        builder.append(str);
      }

      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return builder.toString();
  }

}
