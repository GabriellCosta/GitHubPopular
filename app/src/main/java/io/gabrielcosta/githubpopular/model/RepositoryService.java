package io.gabrielcosta.githubpopular.model;

import io.gabrielcosta.githubpopular.entity.RepositoriesDTO;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Service for repositories
 */
public class RepositoryService {

  private final RepositoryEndpoint service;

  public RepositoryService(Retrofit retrofit) {
    service = retrofit.create(RepositoryEndpoint.class);
  }

  public Call<RepositoriesDTO> fetchRepositories(final String language, final String sort,
      final int page) {
    return service.search(language, sort, page);
  }

}
