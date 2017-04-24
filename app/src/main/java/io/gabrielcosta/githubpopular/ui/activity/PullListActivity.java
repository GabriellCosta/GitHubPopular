package io.gabrielcosta.githubpopular.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import io.gabrielcosta.githubpopular.R;
import io.gabrielcosta.githubpopular.contract.PullListContract;
import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.ui.adapter.PullListAdapter;
import java.util.List;

public class PullListActivity extends AppCompatActivity implements PullListContract.PullListView {

  private static final String TAG = PullListActivity.class.getName();
  public static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

  private RepositorieVO repositorieVO;
  private RecyclerView recyclerView;
  private PullListAdapter adapter;
  private LinearLayoutManager layoutManager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
    getExtras(getIntent().getExtras());
  }

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

  private void init() {
    recyclerView = (RecyclerView) findViewById(R.id.rv_pull);
    layoutManager = new LinearLayoutManager(this);
    adapter = new PullListAdapter();
  }

  private void getExtras(final Bundle bundle) {
    if (bundle != null) {
      repositorieVO = (RepositorieVO) bundle.getSerializable(EXTRA_REPOSITORY);
    } else {
      Log.e(TAG, "Intent should have Repository info");
      throw new IllegalArgumentException("Intent should have Repository info");
    }
  }
}
