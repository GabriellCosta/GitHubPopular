package io.gabrielcosta.githubpopular.contract;

import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import java.util.List;

/**
 * Created by gabrielcosta on 15/04/17.
 */

public class RepositoryListContract {

  public interface RepositoryListView {

    void setEmptyList();

    void setError();

    void setItems(final List<RepositorieVO> items);
  }

  public interface RepositoryListPresenter {

    void load();
  }

}
