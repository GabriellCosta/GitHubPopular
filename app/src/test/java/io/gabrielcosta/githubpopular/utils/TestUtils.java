package io.gabrielcosta.githubpopular.utils;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.UrlPathPattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by gabrielcosta on 15/04/17.
 */

public final class TestUtils {

  public static final int WIRE_MOCK_PORT = 8089;
  public static final String WIRE_MOCK_URL = "http://localhost";
  public static final String WIRE_MOCK_FINAL_URL = String
      .format("%1$s:%2$s/", WIRE_MOCK_URL, WIRE_MOCK_PORT);
  public static final long DEF_SERVICE_ASYNC_WAIT = 100L;

  private static final String TEST_RESOURCE = "src/test/resources/";

  /**
   *  Stub a api call
   * @param url url to be stubed
   * @param file json to be returned from stubed service
   * @param status status code of HTTP method
   */
  public static void stubService(final String url, final String file, final int status) {
    WireMock.reset();
    stubService(urlPathEqualTo(url), file, status);
  }

  /**
   *  Stub a api call
   * @param urlPathPattern urlPathPattern to be stubed
   * @param file json to be returned from stubed service
   * @param status status code of HTTP method
   */
  public static void stubService(final UrlPathPattern urlPathPattern, final String file,
      final int status) {
    stubFor(get(urlPathPattern)
        .willReturn(aResponse()
            .withStatus(status)
            .withBody(TestUtils.readFile(file))));
  }

  /**
   * Read file from resource test
   * @param fileName file name
   * @return  resourceas string
   */
  public static String readFile(final String fileName) {
    ClassLoader classLoader = TestUtils.class.getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    try {
      return readFile(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private static String readFile(final File file) throws FileNotFoundException {
    return new Scanner(file).useDelimiter("\\Z").next();
  }

}
