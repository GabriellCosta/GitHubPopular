package io.gabrielcosta.githubpopular.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.gabrielcosta.githubpopular.entity.PullRequestVO;
import io.gabrielcosta.githubpopular.entity.RepositoriesDTO;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Service for repositories
 */
public class RepositoryService {

  private final RepositoryEndpoint service;

  public RepositoryService(String baseUrl) {
    Retrofit build = RetrofitBuilder.getInstance().build(baseUrl);
    service = build.create(RepositoryEndpoint.class);
  }

  public Call<RepositoriesDTO> fetchRepositories(@NonNull final String language,
      @Nullable final String sort,
      @Nullable final Integer page) {

    if (language != null && !language.isEmpty()) {
      return service.search(String.format("language:%s", language), sort, page);
    } else {
      throw new IllegalArgumentException("Language should not be null or empty");
    }
  }

  public Call<List<PullRequestVO>> fetchPullREquest(@NonNull final String user,
      @NonNull final String repository) {

    if (user.isEmpty() || repository.isEmpty()) {
      throw new IllegalArgumentException("User and repository should not be empty");
    }

    return service.fetchPullRequests(user, repository);
  }

}
