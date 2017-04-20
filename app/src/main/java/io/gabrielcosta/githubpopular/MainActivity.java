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

  private static final String ITEM_LIST = "ITEM_LIST";
  private static final String LAYOUT_POSITION = "LAYOUT_POSITION";
  private static final String PAGE_NUMBER = "PAGE_NUMBER";

  private RecyclerView recyclerView;
  private RepositoryListPresenter presenter;
  private ProgressBar progressBar;
  private LinearLayoutManager layout;
  private RepositoryListAdapter adapter;
  private EndlessRecyclerOnScrollListener listener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
    configureRecyclerView(recyclerView);
    if (savedInstanceState == null) {
      presenter.load();

    }
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

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(ITEM_LIST, new SaveListStateDTO(adapter.getItemList()));
    outState.putParcelable(LAYOUT_POSITION, layout.onSaveInstanceState());
    outState.putInt(PAGE_NUMBER, listener.getCurrentPage());
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (savedInstanceState != null) {
      layout.onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_POSITION));
      SaveListStateDTO serializable = (SaveListStateDTO) savedInstanceState
          .getSerializable(ITEM_LIST);
      adapter.addItems((List<RepositorieVO>) serializable.getList());
      listener.setCurrentPage(savedInstanceState.getInt(PAGE_NUMBER));
      progressBar.setVisibility(View.GONE);
    }
  }

  private void configureRecyclerView(final RecyclerView recyclerView) {
    recyclerView.setVisibility(View.VISIBLE);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(layout);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, layout.getOrientation()));
    listener = new EndlessRecyclerOnScrollListener(layout) {
      @Override
      protected void onLoadMore(int currentPage) {
        presenter.loadPage(currentPage);
        progressBar.setVisibility(View.VISIBLE);
      }
    };
    recyclerView.addOnScrollListener(listener);
  }
}
