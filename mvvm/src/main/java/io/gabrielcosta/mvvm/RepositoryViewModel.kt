package io.gabrielcosta.mvvm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.gabrielcosta.service.RepositoryService
import io.gabrielcosta.service.entity.RepositorieVO
import io.gabrielcosta.service.entity.RepositoriesDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RepositoryViewModel"

class RepositoryViewModel : ViewModel(), Callback<RepositoriesDTO> {

    private var repositories: MutableLiveData<List<RepositorieVO>> = MutableLiveData()
    private var error: MutableLiveData<String> = MutableLiveData()
    private val service by lazy { RepositoryService(BuildConfig.HOST_NAME) }
    
    fun fetchRepositories(): LiveData<List<RepositorieVO>> {

        service.fetchRepositories("java", "stars", 1)
                .enqueue(this)

        return repositories
    }

    fun registerForErrorEvent(): LiveData<String> {
        return error
    }

    override fun onResponse(call: Call<RepositoriesDTO>?, response: Response<RepositoriesDTO>?) {
        if (response?.isSuccessful != null) {
            repositories.value = response.body()?.items
        } else {
            error.value = response?.message()
        }
    }

    override fun onFailure(call: Call<RepositoriesDTO>, t: Throwable) {
        Log.d(TAG, "Fail to call ${call.request().url()}", t)
        error.value = "Fail to load RepositoryList"
    }
    
}