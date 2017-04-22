package io.gabrielcosta.githubpopular.contract;

import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import java.util.List;

/**
 * Created by gabrielcosta on 22/04/17.
 */

public final class PullListContract {

  public interface PullListView {

    void setItems(final List<PullRequestVO> items);

    void setEmptyList();

    void setError();

    void setForks(final int forks);

    void setStarts(final int stars);

  }

  public interface PullListPresenter {

    void init(final RepositorieVO repositorieVO);

    void load();

    void loadMore(final int page);
  }

}
