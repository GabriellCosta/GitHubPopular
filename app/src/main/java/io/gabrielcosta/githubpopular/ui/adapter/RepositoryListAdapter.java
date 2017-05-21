package io.gabrielcosta.githubpopular.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.gabrielcosta.githubpopular.R;
import io.gabrielcosta.githubpopular.entity.RepositorieVO;
import io.gabrielcosta.githubpopular.ui.activity.PullListActivity;
import io.gabrielcosta.githubpopular.ui.adapter.RepositoryListAdapter.RepositoryVH;
import io.gabrielcosta.githubpopular.utils.ImageLoaderHelper;
import java.util.ArrayList;
import java.util.List;

public class RepositoryListAdapter extends Adapter<RepositoryVH> {

  private final ArrayList<RepositorieVO> list;

  public RepositoryListAdapter() {
    this.list = new ArrayList<>();
  }

  @Override
  public RepositoryVH onCreateViewHolder(ViewGroup parent, int viewType) {
    final View inflate = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_list_repository, parent, Boolean.FALSE);
    return new RepositoryVH(inflate);
  }

  @Override
  public void onBindViewHolder(RepositoryVH holder, int position) {
    final RepositorieVO item = list.get(position);

    holder.name.setText(item.getName());
    holder.description.setText(item.getDescription());
    holder.authorFullName.setText(item.getOwner().getLogin());
    holder.author.setText(item.getOwner().getLogin());
    ImageLoaderHelper
        .loadImage(item.getOwner().getAvatarUrl(), holder.authorImage, R.drawable.ic_person);
    final Context context = holder.itemView.getContext();
    holder.stars.setText(context.getString(R.string.repository_list_stars, item.getStars()));
    holder.forks.setText(context.getString(R.string.repository_list_forks, item.getForks()));
    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), PullListActivity.class);
        intent.putExtra(PullListActivity.EXTRA_REPOSITORY, item);
        v.getContext().startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void addItems(final List<RepositorieVO> items) {
    list.addAll(items);
    notifyDataSetChanged();
  }

  public ArrayList<RepositorieVO> getItemList() {
    return list;
  }

  public static class RepositoryVH extends ViewHolder {

    final TextView name;
    final TextView description;
    final TextView forks;
    final TextView stars;
    final TextView author;
    final TextView authorFullName;
    final ImageView authorImage;

    private RepositoryVH(View itemView) {
      super(itemView);
      name = (TextView) itemView.findViewById(R.id.textView_repository_name);
      description = (TextView) itemView.findViewById(R.id.textView_repository_description);
      author = (TextView) itemView.findViewById(R.id.textView_repository_author_login);
      authorFullName = (TextView) itemView.findViewById(R.id.textView_repository_author_full_name);
      authorImage = (ImageView) itemView.findViewById(R.id.imageView_repository_author_photo);
      forks = (TextView) itemView.findViewById(R.id.textView_repository_forks);
      stars = (TextView) itemView.findViewById(R.id.textView_repository_star);
    }
  }
}
