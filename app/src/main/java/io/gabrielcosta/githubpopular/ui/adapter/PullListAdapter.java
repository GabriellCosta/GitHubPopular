package io.gabrielcosta.githubpopular.ui.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.gabrielcosta.common.utils.ImageLoaderHelper;
import io.gabrielcosta.githubpopular.R;
import io.gabrielcosta.githubpopular.ui.adapter.PullListAdapter.PullListVH;
import io.gabrielcosta.service.entity.PullRequestVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gabrielcosta on 23/04/17.
 */

public class PullListAdapter extends Adapter<PullListVH> {

  private final List<PullRequestVO> list = new ArrayList<>();

  @Override
  public PullListVH onCreateViewHolder(ViewGroup parent, int viewType) {
    final View inflate = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_list_pull, parent, Boolean.FALSE);
    return new PullListVH(inflate);
  }

  @Override
  public void onBindViewHolder(PullListVH holder, int position) {
    final PullRequestVO item = list.get(position);

    holder.name.setText(item.getTitle());
    holder.description.setText(item.getBody());
    holder.authorFullName.setText(item.getUser().getLogin());
    holder.author.setText(item.getUser().getLogin());
    ImageLoaderHelper
        .loadImage(item.getUser().getAvatarUrl(), holder.authorImage, R.drawable.ic_person);
    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(final View v) {

        new Builder(v.getContext())
            .setMessage(R.string.pull_list_dialog_message_exit_app)
            .setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                final Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(item.getHtmlUrl()));
                v.getContext().startActivity(intent);
              }
            })
            .setNegativeButton(R.string.all_cancel, null)
            .show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void addItems(final List<PullRequestVO> items) {
    list.addAll(items);
    notifyDataSetChanged();
  }

  public List<PullRequestVO> getItemList() {
    return Collections.unmodifiableList(list);
  }

  static class PullListVH extends ViewHolder {

    final TextView name;
    final TextView description;
    final TextView author;
    final TextView authorFullName;
    final ImageView authorImage;

    private PullListVH(View itemView) {
      super(itemView);
      name = (TextView) itemView.findViewById(R.id.textView_pull_name);
      description = (TextView) itemView.findViewById(R.id.textView_pull_description);
      author = (TextView) itemView.findViewById(R.id.textView_pull_author_login);
      authorFullName = (TextView) itemView.findViewById(R.id.textView_pull_author_full_name);
      authorImage = (ImageView) itemView.findViewById(R.id.imageView_pull_author_photo);
    }
  }

}
