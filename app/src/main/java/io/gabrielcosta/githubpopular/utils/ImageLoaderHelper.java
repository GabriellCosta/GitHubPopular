package io.gabrielcosta.githubpopular.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by gabrielcosta on 18/04/17.
 */

public final class ImageLoaderHelper {

  private ImageLoaderHelper() {

  }

  public static void loadImage(final String url, final ImageView imageView) {
    Glide.with(imageView.getContext())
        .load(url)
        .into(imageView);
  }

}
