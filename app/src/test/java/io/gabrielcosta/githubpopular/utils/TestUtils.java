package io.gabrielcosta.githubpopular.utils;

/**
 * Created by gabrielcosta on 15/04/17.
 */

public final class TestUtils {

  public static final int WIRE_MOCK_PORT = 8089;
  public static final String WIRE_MOCK_URL = "http://localhost";
  public static final String WIRE_MOCK_FINAL_URL = String
      .format("%1$s:%2$s/", WIRE_MOCK_URL, WIRE_MOCK_PORT);
  public static final long DEF_SERVICE_ASYNC_WAIT = 100L;

}
