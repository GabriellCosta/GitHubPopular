package io.gabrielcosta.githubpopular.model;

import io.gabrielcosta.githubpopular.entity.RepositoriesDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RepositoryEndpoint {

  /**
   * search/repositories?q=language:Java&sort=stars&page=1
   *
   * @param language Programing language
   * @param sort sort order
   * @param page page number
   * @return Future call with DTO with list of repositories
   */
  @GET("search/repositories")
  Call<RepositoriesDTO> search(@Query("q") final String language,
      @Query("sort") final String sort, @Query("page") final int page);

}
