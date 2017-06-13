package io.gabrielcosta.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.gabrielcosta.service.RepositoryService
import io.gabrielcosta.service.entity.PullRequestVO
import io.gabrielcosta.service.entity.RepositorieVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RepositoryViewModel"

class PullListViewModel: ViewModel(), Callback<MutableList<PullRequestVO>> {

    private var pullList: MutableLiveData<List<PullRequestVO>> = MutableLiveData()
    private val service by lazy { RepositoryService(BuildConfig.HOST_NAME) }
    private var error: MutableLiveData<String> = MutableLiveData()

    fun fetchPullRequest(repository: RepositorieVO): LiveData<List<PullRequestVO>> {

        service.fetchPullREquest(repository.owner.login, repository.name)
                .enqueue(this)

        return pullList
    }

    override fun onResponse(call: Call<MutableList<PullRequestVO>>?, response: Response<MutableList<PullRequestVO>>?) {
        if (response?.isSuccessful != null) {
            pullList.value = response.body()
        } else {
            error.value = response?.message()
        }
    }

    override fun onFailure(call: Call<MutableList<PullRequestVO>>, t: Throwable?) {
        Log.d(TAG, "Fail to call ${call.request().url()}", t)
        error.value = "Fail to load RepositoryList"
    }

}