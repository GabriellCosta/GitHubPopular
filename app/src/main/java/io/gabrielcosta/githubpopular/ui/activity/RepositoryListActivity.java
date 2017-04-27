package io.gabrielcosta.githubpopular.ui.activity;

import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import io.gabrielcosta.githubpopular.BuildConfig;
import io.gabrielcosta.githubpopular.R;
import io.gabrielcosta.githubpopular.entity.SaveListStateDTO;
import io.gabrielcosta.githubpopular.ui.adapter.RepositoryListAdapter;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListPresenter;
import io.gabrielcosta.githubpopular.contract.RepositoryListContract.RepositoryListView;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.model.RepositoryService;
import io.gabrielcosta.githubpopular.presenter.RepositoryListPresenterImpl;
import io.gabrielcosta.githubpopular.utils.EndlessRecyclerOnScrollListener;
import io.gabrielcosta.githubpopular.utils.NetworkUtils;
import java.util.List;

public class RepositoryListActivity extends AppCompatActivity implements RepositoryListView {

  private static final String ITEM_LIST = "ITEM_LIST";
  private static final String LAYOUT_POSITION = "LAYOUT_POSITION";
  private static final String PAGE_NUMBER = "PAGE_NUMBER";

  private RecyclerView recyclerView;
  private RepositoryListPresenter presenter;
  private ProgressBar progressBar;
  private LinearLayoutManager layout;
  private RepositoryListAdapter adapter;
  private EndlessRecyclerOnScrollListener listener;
  private View rootView;
  private View emptyStateView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_repository_list);
    init();
    configureRecyclerView(recyclerView);

    if (!NetworkUtils.hasConnection(this)) {
      noInternetConnection();
    } else if (savedInstanceState == null) {
      presenter.load();
    }
  }

  @Override
  public void setEmptyList() {
    emptyStateView.setVisibility(View.VISIBLE);
  }

  @Override
  public void setError() {
    progressBar.setVisibility(View.GONE);
    Snackbar.make(rootView, R.string.repository_list_error, BaseTransientBottomBar.LENGTH_LONG)
        .show();
    listener.wasLoaded(Boolean.FALSE);
  }

  @Override
  public void setItems(List<RepositorieVO> items) {
    emptyStateView.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
    adapter.addItems(items);
    listener.wasLoaded(Boolean.TRUE);
  }

  private void noInternetConnection() {
    progressBar.setVisibility(View.GONE);
    setEmptyList();
    Snackbar.make(rootView, R.string.all_error_network, BaseTransientBottomBar.LENGTH_INDEFINITE)
        .setAction(R.string.all_try_again, new OnClickListener() {
          @Override
          public void onClick(View v) {
            if (NetworkUtils.hasConnection(getBaseContext())) {
              progressBar.setVisibility(View.VISIBLE);
              presenter.load();
            } else {
              noInternetConnection();
            }
          }
        })
        .show();
  }

  private void init() {
    emptyStateView = findViewById(R.id.layout_empty_state);
    progressBar = (ProgressBar) findViewById(R.id.progressbar_repository);
    recyclerView = (RecyclerView) findViewById(R.id.rv_repository);
    rootView = findViewById(android.R.id.content);
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
      recyclerView.setVisibility(View.VISIBLE);
    }
  }

  private void configureRecyclerView(final RecyclerView recyclerView) {
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
