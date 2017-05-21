package io.gabrielcosta.githubpopular.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
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
    final Context context = imageView.getContext();
    Glide.with(context)
        .load(url)
        .placeholder(ContextCompat.getDrawable(context, placeholder))
        .into(imageView);
  }

}
