package io.gabrielcosta.service.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;


/**
 * VO utilized in view to fill repositores list
 */
public class RepositorieVO implements Parcelable {

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

  private RepositorieVO(Parcel in) {
    name = in.readString();
    description = in.readString();
    forks = in.readInt();
    stars = in.readInt();
    owner = in.readParcelable(OwnerVO.class.getClassLoader());
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

  public static final Creator<RepositorieVO> CREATOR = new Creator<RepositorieVO>() {
    @Override
    public RepositorieVO createFromParcel(Parcel in) {
      return new RepositorieVO(in);
    }

    @Override
    public RepositorieVO[] newArray(int size) {
      return new RepositorieVO[size];
    }
  };

  @Override
  public int describeContents() {
    return hashCode();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(description);
    dest.writeInt(forks);
    dest.writeInt(stars);
    dest.writeParcelable(owner, flags);
  }
}
