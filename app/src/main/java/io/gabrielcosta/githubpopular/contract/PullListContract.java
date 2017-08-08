package io.gabrielcosta.githubpopular.contract;

import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import java.util.List;

/**
 * Created by gabrielcosta on 22/04/17.
 */

public final class PullListContract {

  public interface PullListView {

    void setItems(final List<PullRequestVO> items);

    void setEmptyList();

    void setMaxRequestError();

    void setError();

  }

  public interface PullListPresenter {

    void load();

    void loadMore(final int page);
  }

}
