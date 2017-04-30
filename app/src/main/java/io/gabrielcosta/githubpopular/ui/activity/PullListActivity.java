package io.gabrielcosta.githubpopular.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import io.gabrielcosta.githubpopular.R;
import io.gabrielcosta.githubpopular.contract.PullListContract;
import io.gabrielcosta.githubpopular.contract.PullListContract.PullListPresenter;
import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.model.RepositoryService;
import io.gabrielcosta.githubpopular.presenter.PullListPresenterImpl;
import io.gabrielcosta.githubpopular.ui.adapter.PullListAdapter;
import io.gabrielcosta.githubpopular.utils.HostConfig;
import java.util.List;

public class PullListActivity extends AppCompatActivity implements PullListContract.PullListView {

  private static final String TAG = PullListActivity.class.getName();
  public static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

  private RepositorieVO repositorieVO;
  private RecyclerView recyclerView;
  private PullListAdapter adapter;
  private LinearLayoutManager layoutManager;
  private PullListPresenter presenter;
  private ProgressBar progressBar;
  private View emptyStateView;
  private View rootView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pul_list);
    getExtras(getIntent().getExtras());
    init();
    configureRecyclerView();
    loadPullList();
    configureToolbar();
  }

  @Override
  public void setItems(List<PullRequestVO> items) {
    adapter.addItems(items);
    progressBar.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void setEmptyList() {
    emptyStateView.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void setError() {
    progressBar.setVisibility(View.GONE);
    Snackbar.make(rootView, R.string.pull_list_error, BaseTransientBottomBar.LENGTH_LONG)
        .show();
  }

  private void init() {
    rootView = findViewById(android.R.id.content);
    emptyStateView = findViewById(R.id.layout_empty_state);
    progressBar = (ProgressBar) findViewById(R.id.progressbar_pull);
    recyclerView = (RecyclerView) findViewById(R.id.rv_pull);
    layoutManager = new LinearLayoutManager(this);
    adapter = new PullListAdapter();
    presenter = new PullListPresenterImpl(this, new RepositoryService(HostConfig.HOST_URL),
        repositorieVO);
  }

  private void loadPullList() {
    presenter.load();
  }

  private void getExtras(final Bundle bundle) {
    if (bundle != null) {
      repositorieVO = (RepositorieVO) bundle.getSerializable(EXTRA_REPOSITORY);
    } else {
      Log.e(TAG, "Intent should have Repository info");
      throw new IllegalArgumentException("Intent should have Repository info");
    }
  }

  private void configureRecyclerView() {
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
  }

  private void configureToolbar() {
    setTitle(repositorieVO.getName());
    getSupportActionBar().setSubtitle(
        getString(R.string.pull_list_sub_title, repositorieVO.getForks(),
            repositorieVO.getStars()));
  }
}
