package io.gabrielcosta.githubpopular.entity;

import com.google.gson.annotations.SerializedName;


/**
 * VO utilized in view to fill repositores list
 */
public final class RepositorieVO {

  @SerializedName("name")
  private final String name;
  @SerializedName("description")
  private final String description;
  @SerializedName("forks")
  private final int forks;
  @SerializedName("stargazers_count")
  private final int stars;
  @SerializedName("owner")
  private final OwnerVO owner;

  public RepositorieVO(String name, String description, int forks, int stars,
      OwnerVO ownerVO) {
    this.name = name;
    this.description = description;
    this.forks = forks;
    this.stars = stars;
    this.owner = ownerVO;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getForks() {
    return forks;
  }

  public int getStars() {
    return stars;
  }

  public OwnerVO getOwner() {
    return owner;
  }
}