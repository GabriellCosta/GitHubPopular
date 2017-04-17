package io.gabrielcosta.githubpopular.presenter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListPresenter;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListView;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.model.RepositoryService;
import io.gabrielcosta.githubpopular.utils.TestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

public class RepositoryListPresenterImplTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(
      wireMockConfig().port(TestUtils.WIRE_MOCK_PORT));

  @Mock
  private RepositoryListView view;
  private RepositoryListPresenter presenter;
  @Mock
  RepositoryService service;

  @Before
  public void before() {
    initMocks(this);
    presenter = new RepositoryListPresenterImpl(view,
        new RepositoryService(TestUtils.WIRE_MOCK_FINAL_URL));
  }

  @Test
  public void shouldCallSetItemsListOnView() {
    stubRepositoriesService();
    presenter.load();
    TestUtils.sleep(TestUtils.DEF_SERVICE_ASYNC_WAIT);
    verify(view, times(1)).setItems(ArgumentMatchers.<RepositorieVO>anyList());
    verify(view, never()).setEmptyList();
    verify(view, never()).setError();
  }

  @Test
  public void shouldCallEmptyListOnError() {
    stubRepositoriesError();
    presenter.load();
    TestUtils.sleep(TestUtils.DEF_SERVICE_ASYNC_WAIT);
    verify(view, never()).setItems(ArgumentMatchers.<RepositorieVO>anyList());
    verify(view, never()).setError();
    verify(view, times(1)).setEmptyList();
  }

  @Test
  public void shouldCallSetError() {
    stubError();
    presenter.load();
    TestUtils.sleep(TestUtils.DEF_SERVICE_ASYNC_WAIT);
    verify(view, never()).setItems(ArgumentMatchers.<RepositorieVO>anyList());
    verify(view, times(1)).setError();
    verify(view, never()).setEmptyList();
  }

  private void stubRepositoriesService() {
    TestUtils.stubService("/search/repositories", "java_repositories_sort_starts_page_1.json",
        HttpStatus.OK_200);
  }

  private void stubRepositoriesError() {
    TestUtils.stubService("/search/repositories", "validation_error.json",
        HttpStatus.UNPROCESSABLE_ENTITY_422);
  }

  private void stubError() {
    WireMock.reset();
    stubFor(get(urlPathEqualTo("/search/repositories"))
        .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
  }

}
