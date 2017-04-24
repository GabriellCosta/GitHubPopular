package io.gabrielcosta.githubpopular.presenter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.gabrielcosta.githubpopular.contract.PullListContract.PullListPresenter;
import io.gabrielcosta.githubpopular.contract.PullListContract.PullListView;
import io.gabrielcosta.githubpopular.entity.OwnerVO;
import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.model.RepositoryService;
import io.gabrielcosta.githubpopular.utils.TestUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

/**
 * Created by gabrielcosta on 22/04/17.
 */
public class PullListPresenterImplTest {

  private static final long WAIT_TIME = TestUtils.DEF_SERVICE_ASYNC_WAIT + 300L;
  private static final String LOGIN = "elastic";
  private static final String REPOSITORY = "elasticsearch";
  public static final String URL = String.format("/repos/%s/%s/pulls", LOGIN, REPOSITORY);
  @Rule
  public WireMockRule wireMockRule = new WireMockRule(
      wireMockConfig().port(TestUtils.WIRE_MOCK_PORT));

  @Mock
  private PullListView view;
  @Mock
  private RepositorieVO repositorieVO;
  @Mock
  private OwnerVO ownerVO;
  private PullListPresenter presenter;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    presenter = new PullListPresenterImpl(view,
        new RepositoryService(TestUtils.WIRE_MOCK_FINAL_URL), repositorieVO);
    when(repositorieVO.getOwner()).thenReturn(ownerVO);
    when(ownerVO.getLogin()).thenReturn(LOGIN);
    when(repositorieVO.getName()).thenReturn(REPOSITORY);
  }

  @Test
  public void shouldSetListItems() {
    stubPullList();
    presenter.load();
    TestUtils.sleep(WAIT_TIME);
    verify(view, times(1)).setItems(ArgumentMatchers.<PullRequestVO>anyList());
  }

  @Test
  public void shouldSetError() {
    stubError();
    presenter.load();
    TestUtils.sleep(WAIT_TIME);
    verify(view, times(1)).setError();
  }

  @Test
  public void shouldSetEmptyListWhenRequestFail() {
    stubRequestFail();
    presenter.load();
    TestUtils.sleep(WAIT_TIME);
    verify(view, times(1)).setEmptyList();
  }

  private void stubPullList() {
    TestUtils.stubService(URL, "pull_list.json",
        HttpStatus.OK_200);
  }

  private void stubError() {
    WireMock.reset();
    stubFor(get(urlPathEqualTo(URL))
        .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
  }

  private void stubRequestFail() {
    WireMock.reset();
    TestUtils.stubService(URL, "validation_error.json",
        HttpStatus.UNPROCESSABLE_ENTITY_422);
  }

}