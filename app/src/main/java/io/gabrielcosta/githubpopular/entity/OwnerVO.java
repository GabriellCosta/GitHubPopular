package io.gabrielcosta.githubpopular.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * VO for repositorie owner
 */
public class OwnerVO implements Serializable {

  @SerializedName("avatar_url")
  private final String avatarUrl;
  @SerializedName("login")
  private final String login;

  private OwnerVO(String avatarUrl, String login) {
    this.avatarUrl = avatarUrl;
    this.login = login;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getLogin() {
    return login;
  }
}
