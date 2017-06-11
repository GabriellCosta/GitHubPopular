package io.gabrielcosta.service.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * VO for repositorie owner
 */
public class OwnerVO implements Parcelable {

  @SerializedName("avatar_url")
  private final String avatarUrl;
  @SerializedName("login")
  private final String login;

  private OwnerVO(Parcel in) {
    avatarUrl = in.readString();
    login = in.readString();
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getLogin() {
    return login;
  }

  public static final Creator<OwnerVO> CREATOR = new Creator<OwnerVO>() {
    @Override
    public OwnerVO createFromParcel(Parcel in) {
      return new OwnerVO(in);
    }

    @Override
    public OwnerVO[] newArray(int size) {
      return new OwnerVO[size];
    }
  };

  @Override
  public int describeContents() {
    return hashCode();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(avatarUrl);
    dest.writeString(login);
  }
}
