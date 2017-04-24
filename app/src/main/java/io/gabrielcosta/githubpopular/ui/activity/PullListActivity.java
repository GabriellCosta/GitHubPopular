package io.gabrielcosta.githubpopular.ui.activity;

import android.support.v7.app.AppCompatActivity;
import io.gabrielcosta.githubpopular.contract.PullListContract;
import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import java.util.List;

public class PullListActivity extends AppCompatActivity implements PullListContract.PullListView {

  public static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

  private RepositorieVO repositorieVO;

  @Override
  public void setItems(List<PullRequestVO> items) {

  }

  @Override
  public void setEmptyList() {

  }

  @Override
  public void setError() {

  }

  @Override
  public void setForks(int forks) {

  }

  @Override
  public void setStarts(int stars) {

  }
}
