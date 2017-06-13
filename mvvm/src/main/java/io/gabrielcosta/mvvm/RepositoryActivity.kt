package io.gabrielcosta.mvvm

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import io.gabrielcosta.common.adapter.RepositoryListAdapter
import io.gabrielcosta.service.entity.RepositorieVO

private const val LAYOUT_POSITION = "LAYOUT_POSITION"

class RepositoryActivity : LifecycleActivity(), View.OnClickListener {

    private val layoutManager = LinearLayoutManager(this)
    private val recyclerView: RecyclerView by lazy {
       val field = findViewById(R.id.rv_repository) as RecyclerView
        field.adapter = adapter
        field.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        field
    }

    private val liveData: LiveData<List<RepositorieVO>> by lazy { repositoryVM.fetchRepositories() }

    private val progress: ProgressBar by lazy {
        findViewById(R.id.progressbar_repository) as ProgressBar
    }

    private val adapter: RepositoryListAdapter by lazy {
        RepositoryListAdapter(this)
    }

    private val repositoryVM by lazy {
        ViewModelProviders.of(this).get(RepositoryViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAYOUT_POSITION, layoutManager.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            layoutManager.onRestoreInstanceState(
                    savedInstanceState.getParcelable<Parcelable>(LAYOUT_POSITION))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        recyclerView.layoutManager = layoutManager
        liveData.observe(this, Observer {
                    adapter.addItems(it)
                    progress.visibility = GONE
                    recyclerView.visibility = VISIBLE
                })
        repositoryVM.registerForErrorEvent()
                .observe(this, Observer {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                })
    }

    override fun onClick(v: View?) {
        val position = recyclerView.getChildAdapterPosition(v)
        val get = liveData.value?.get(position)
        val intent = Intent(this, PullRequestActivity::class.java)
                .putExtra(PullRequestActivity.EXTRA_REPOSITORY, get)
        startActivity(intent)
    }
}
