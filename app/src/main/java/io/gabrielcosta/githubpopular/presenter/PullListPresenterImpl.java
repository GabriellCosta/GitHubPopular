package io.gabrielcosta.githubpopular.presenter;

import io.gabrielcosta.githubpopular.contract.PullListContract.PullListPresenter;
import io.gabrielcosta.githubpopular.contract.PullListContract.PullListView;
import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.model.RepositoryService;
import java.util.List;

import io.gabrielcosta.githubpopular.utils.RemainRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class PullListPresenterImpl implements PullListPresenter {

  private final PullListView view;
  private final RepositoryService service;
  private final RepositorieVO repositorieVO;

  public PullListPresenterImpl(final PullListView view, final RepositoryService service,
      final RepositorieVO repositorieVO) {
    this.view = view;
    this.service = service;
    this.repositorieVO = repositorieVO;
  }

  @Override
  public void load() {
    service.fetchPullREquest(repositorieVO.getOwner().getLogin(), repositorieVO.getName()).enqueue(
        new Callback<List<PullRequestVO>>() {
          @Override
          public void onResponse(Call<List<PullRequestVO>> call,
              Response<List<PullRequestVO>> response) {
            if (response.isSuccessful() && !response.body().isEmpty()) {
              view.setItems(response.body());
            } else {
                if (RemainRequest.hasRequestToMake(response.headers())) {
                    view.setMaxRequestError();
                } else {
                    view.setError();
                }
            }
          }

          @Override
          public void onFailure(Call<List<PullRequestVO>> call, Throwable t) {
            view.setError();
          }
        });
  }

  @Override
  public void loadMore(int page) {

  }
}
