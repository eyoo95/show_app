package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.Posting;
import com.luvris2.publicperfomancedisplayapp.ui.PostingReadActivity;

import java.util.List;


// 자유게시판 - 게시글 어댑터
// 최지훈
public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder> {

    Context context;
    List<Posting> postingList;

    public PostingAdapter(Context context, List<Posting> postingList) {
        this.context = context;
        this.postingList = postingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posting_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Posting posting = postingList.get(position);
        holder.txtTitle.setText(posting.getTitle());
        holder.txtCreatedAt.setText(posting.getCreatedAt());
        holder.txtUpdatedAt.setText(posting.getUpdatedAt());
        holder.txtContent.setText(posting.getContent());
        holder.txtNickName.setText(posting.getNickname());
    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtCreatedAt;
        TextView txtUpdatedAt;
        TextView txtContent;
        TextView txtNickName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtUpdatedAt = itemView.findViewById(R.id.txtUpdatedAt);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtNickName = itemView.findViewById(R.id.txtNickName);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String title = txtTitle.getText().toString().trim();
                    String content = txtContent.getContext().toString().trim();

                    // 전체 글 중 선택한 글의 위치 정보를 가져온다
                    int index = getAdapterPosition();
                    Posting postingId = postingList.get(index);

                    // 전체 글 상세보기의 액티비티에 정보를 넘겨준다.
                    Intent intent = new Intent(context, PostingReadActivity.class);
                    intent.putExtra("postingId", postingId);
                    intent.putExtra("postingTitle", title);
                    intent.putExtra("postingContent", content);

                    context.startActivity(intent);
                }
            });
        }
    }
}
