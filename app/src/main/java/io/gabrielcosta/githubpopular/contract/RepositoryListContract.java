package io.gabrielcosta.githubpopular.contract;

import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import java.util.List;

public class RepositoryListContract {

  public interface RepositoryListView {

    void setEmptyList();

    void setError();

    void setMaxRequestError();

    void setItems(final List<RepositorieVO> items);

  }

  public interface RepositoryListPresenter {

    void load();

    void loadPage(int pageToLoad);
  }

}
