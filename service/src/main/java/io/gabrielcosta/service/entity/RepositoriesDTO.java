package io.gabrielcosta.service.entity;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;


/**
 * DTO containing the list of repositories
 */
public final class RepositoriesDTO {

  @SerializedName("total_count")
  private final int totalCount;
  @SerializedName("incomplete_results")
  private final boolean incompleteResults;
  @SerializedName("items")
  private final List<RepositorieVO> items;

  private RepositoriesDTO(int totalCount, boolean incompleteResults,
      List<RepositorieVO> items) {
    this.totalCount = totalCount;
    this.incompleteResults = incompleteResults;
    this.items = items;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public boolean isIncompleteResults() {
    return incompleteResults;
  }

  public List<RepositorieVO> getItems() {
    return Collections.unmodifiableList(items);
  }
}
