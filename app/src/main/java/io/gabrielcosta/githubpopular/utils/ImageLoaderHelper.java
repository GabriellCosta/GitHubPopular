package io.gabrielcosta.githubpopular.utils;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by gabrielcosta on 18/04/17.
 */

public final class ImageLoaderHelper {

  private ImageLoaderHelper() {

  }

  public static void loadImage(final String url, final ImageView imageView,
      final @DrawableRes int placeholder) {
    Glide.with(imageView.getContext())
        .load(url)
        .placeholder(placeholder)
        .into(imageView);
  }

}
