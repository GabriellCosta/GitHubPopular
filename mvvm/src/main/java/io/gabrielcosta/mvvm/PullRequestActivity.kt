package io.gabrielcosta.mvvm

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import io.gabrielcosta.common.adapter.PullListAdapter
import io.gabrielcosta.service.entity.PullRequestVO
import io.gabrielcosta.service.entity.RepositorieVO

class PullRequestActivity : LifecycleActivity(), View.OnClickListener {

    companion object {
        val EXTRA_REPOSITORY = "EXTRA_REPOSITORY"
    }

    private lateinit var repository: RepositorieVO

    private val layoutManager = LinearLayoutManager(this)
    private val recyclerView: RecyclerView by lazy {
        val field = findViewById(R.id.rv_pull) as RecyclerView
        field.adapter = adapter
        field.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        field
    }

    private val liveData: LiveData<List<PullRequestVO>> by lazy {
        repositoryVM.fetchPullRequest(repository)
    }

    private val progress: ProgressBar by lazy {
        findViewById(R.id.progressbar_pull) as ProgressBar
    }

    private val adapter: PullListAdapter by lazy {
        PullListAdapter(this)
    }

    private val repositoryVM by lazy {
        ViewModelProviders.of(this).get(PullListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = intent.extras.getParcelable<RepositorieVO>(EXTRA_REPOSITORY)
        setContentView(R.layout.activity_pul_list)
        recyclerView.layoutManager = layoutManager
        liveData.observe(this, Observer {
            progress.visibility = GONE
            recyclerView.visibility = VISIBLE
            adapter.addItems(it)
        })
    }

    override fun onClick(v: View) {
        val position = recyclerView.getChildAdapterPosition(v)
        val get = liveData.value?.get(position)
        AlertDialog.Builder(this)
                .setMessage(R.string.pull_list_dialog_message_exit_app)
                .setPositiveButton(R.string.all_ok) { dialog, which ->
                    val intent = Intent(Intent.ACTION_VIEW,
                            Uri.parse(get?.htmlUrl))
                    startActivity(intent)
                }
                .setNegativeButton(R.string.all_cancel, null)
                .show()
    }

}