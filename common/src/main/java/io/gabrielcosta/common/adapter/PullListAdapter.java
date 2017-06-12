package io.gabrielcosta.common.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.gabrielcosta.common.R;
import io.gabrielcosta.common.adapter.PullListAdapter.PullListVH;
import io.gabrielcosta.common.utils.ImageLoaderHelper;
import io.gabrielcosta.service.entity.PullRequestVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PullListAdapter extends Adapter<PullListVH> {

  private final List<PullRequestVO> list = new ArrayList<>();
  private final OnClickListener clickListener;

  public PullListAdapter(OnClickListener clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  public PullListVH onCreateViewHolder(ViewGroup parent, int viewType) {
    final View inflate = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_list_pull, parent, Boolean.FALSE);
    inflate.setOnClickListener(clickListener);
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
