package io.gabrielcosta.githubpopular.presenter;

import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListPresenter;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListView;
import io.gabrielcosta.service.RepositoryService;
import io.gabrielcosta.service.entity.RepositoriesDTO;
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
  public void loadPage(int pageToLoad) {
    service.fetchRepositories("java", "stars", pageToLoad).enqueue(new Callback<RepositoriesDTO>() {
      @Override
      public void onResponse(Call<RepositoriesDTO> call, Response<RepositoriesDTO> response) {
        if (response.isSuccessful() && !response.body().getItems().isEmpty()) {
          view.setItems(response.body().getItems());
        } else {
          view.setError();
        }
      }

      @Override
      public void onFailure(Call<RepositoriesDTO> call, Throwable t) {
          view.setError();
      }
    });
  }

  @Override
  public void onResponse(Call<RepositoriesDTO> call, Response<RepositoriesDTO> response) {
    if (response.isSuccessful() && !response.body().getItems().isEmpty()) {
      view.setItems(response.body().getItems());
    } else {
      view.setEmptyList();
    }
  }

  @Override
  public void onFailure(Call<RepositoriesDTO> call, Throwable t) {
    view.setError();
  }
}
