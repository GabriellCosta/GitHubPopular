package io.gabrielcosta.githubpopular.model;

import io.gabrielcosta.githubpopular.utils.TestUtils;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gabrielcosta on 15/04/17.
 */

public class RepositoryServiceTest {

  private RepositoryService service;

  @Before
  public void before() {
    service = new RepositoryService(TestUtils.WIRE_MOCK_FINAL_URL);
  }

  @Test
  public void shouldEvaluateTheCorrectUrlForSearchRepositories() {
    final String result = service.fetchRepositories("Java", "stars", 1).request().url()
        .toString();
    final String expected = String
        .format("%ssearch/repositories?q=language:Java&sort=stars&page=1",
            TestUtils.WIRE_MOCK_FINAL_URL);

    Assert.assertEquals("URL query isn't equal", expected, result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenLanguageIsNull() {
    service.fetchRepositories(null, "stars", 1).request().url();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenLanguageIsEmpty() {
    service.fetchRepositories("", "stars", 1).request().url();
  }

}
