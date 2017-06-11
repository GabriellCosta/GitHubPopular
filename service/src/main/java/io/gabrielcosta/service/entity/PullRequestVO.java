package io.gabrielcosta.service.entity;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by gabrielcosta on 22/04/17.
 */

public class PullRequestVO {

  @SerializedName("title")
  private final String title;

  @SerializedName("body")
  private final String body;

  @SerializedName("created_at")
  private final Date createdDate;

  @SerializedName("html_url")
  private String htmlUrl;

  @SerializedName("user")
  private final OwnerVO user;

  private PullRequestVO(String title, String body, Date createdDate,
      OwnerVO user) {
    this.title = title;
    this.body = body;
    this.createdDate = createdDate;
    this.user = user;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public OwnerVO getUser() {
    return user;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }
}
