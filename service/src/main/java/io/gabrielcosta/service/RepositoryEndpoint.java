package io.gabrielcosta.service;

import io.gabrielcosta.service.entity.PullRequestVO;
import io.gabrielcosta.service.entity.RepositoriesDTO;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface RepositoryEndpoint {

  /**
   * search/repositories?q=language:Java&sort=stars&page=1
   *
   * @param language Programing language, should not be null, or 'll throw IllegalArgumentException
   * @param sort sort order
   * @param page page number
   * @return Future call with DTO with list of repositories
   */
  @GET("search/repositories")
  Call<RepositoriesDTO> search(@Query("q") final String language,
      @Query("sort") final String sort, @Query("page") final int page);

  @GET("repos/{user}/{repo}/pulls")
  Call<List<PullRequestVO>> fetchPullRequests(@Path("user") final String user,
      @Path("repo") final String repository);

}
