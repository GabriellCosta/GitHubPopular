package io.gabrielcosta.githubpopular.presenter;

import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListPresenter;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListView;
import io.gabrielcosta.githubpopular.entity.RepositoriesDTO;
import io.gabrielcosta.githubpopular.model.RepositoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gabrielcosta on 15/04/17.
 */

public final class RepositoryListPresenterImpl implements
    RepositoryListPresenter, Callback<RepositoriesDTO> {

  private final RepositoryListView view;
  private final RepositoryService service;

  public RepositoryListPresenterImpl(
      RepositoryListView view, RepositoryService service) {
    this.view = view;
    this.service = service;
  }

  @Override
  public void load() {
    service.fetchRepositories("java", "stars", 1).enqueue(this);
  }

  @Override
  public void onResponse(Call<RepositoriesDTO> call, Response<RepositoriesDTO> response) {
    if (response.isSuccessful() && !response.body().getItems().isEmpty()) {
      view.setItems(response.body().getItems());
    }
  }

  @Override
  public void onFailure(Call<RepositoriesDTO> call, Throwable t) {
    view.setEmptyList();
  }
}
