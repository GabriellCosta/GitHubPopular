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
import java.util.List;

public class MainActivity extends AppCompatActivity implements RepositoryListView {

  RecyclerView recyclerView;
  private RepositoryListPresenter presenter;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    progressBar = (ProgressBar) findViewById(R.id.progressbar_repository);
    recyclerView = (RecyclerView) findViewById(R.id.rv_repository);
    presenter = new RepositoryListPresenterImpl(this,
        new RepositoryService(BuildConfig.HOST_NAME));
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
    configureRecyclerView(recyclerView, items);
  }

  private void configureRecyclerView(final RecyclerView recyclerView, List<RepositorieVO> items) {
    recyclerView.setVisibility(View.VISIBLE);
    recyclerView.setAdapter(new RepositoryListAdapter(items));
    final LinearLayoutManager layout = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layout);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, layout.getOrientation()));
  }
}
