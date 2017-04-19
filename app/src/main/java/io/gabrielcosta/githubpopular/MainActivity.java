package io.gabrielcosta.githubpopular;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import io.gabrielcosta.githubpopular.adapter.RepositoryListAdapter;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListPresenter;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListView;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.model.RepositoryService;
import io.gabrielcosta.githubpopular.presenter.RepositoryListPresenterImpl;
import io.gabrielcosta.githubpopular.utils.EndlessRecyclerOnScrollListener;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RepositoryListView {

  RecyclerView recyclerView;
  private RepositoryListPresenter presenter;
  private ProgressBar progressBar;
  private LinearLayoutManager layout;
  private RepositoryListAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
    configureRecyclerView(recyclerView);
    presenter.load();
  }

  @Override
  public void setEmptyList() {

  }

  @Override
  public void setError() {

  }

  @Override
  public void setItems(List<RepositorieVO> items) {
    progressBar.setVisibility(View.GONE);
    adapter.addItems(items);
  }

  private void init() {
    progressBar = (ProgressBar) findViewById(R.id.progressbar_repository);
    recyclerView = (RecyclerView) findViewById(R.id.rv_repository);
    adapter = new RepositoryListAdapter();
    layout = new LinearLayoutManager(this);
    presenter = new RepositoryListPresenterImpl(this,
        new RepositoryService(BuildConfig.HOST_NAME));
  }

  private void configureRecyclerView(final RecyclerView recyclerView) {
    recyclerView.setVisibility(View.VISIBLE);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layout);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, layout.getOrientation()));
    recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout) {
      @Override
      protected void onLoadMore(int currentPage) {
        presenter.loadPage(currentPage);
        progressBar.setVisibility(View.VISIBLE);
      }
    });
  }
}
