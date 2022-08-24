package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.Posting;

import java.util.List;


// 자유게시판 - 게시글 어댑터
// 최지훈
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    Context context;
    List<Posting> postingList;

    public BoardAdapter(Context context, List<Posting> postingList) {
        this.context = context;
        this.postingList = postingList;
    }

    @NonNull
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.board_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {
        Posting posting = postingList.get(position);
        holder.txtTitle.setText(posting.getTitle());
        holder.txtCreatedAt.setText(posting.getCreatedAt());
        holder.txtUpdatedAt.setText(posting.getUpdatedAt());
        holder.txtContent.setText(posting.getContent());
        holder.txtNickName.setText(posting.getNickname());
        holder.txtUserId.setText(posting.getUserId() + "");
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
        TextView txtUserId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtUpdatedAt = itemView.findViewById(R.id.txtUpdatedAt);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtNickName = itemView.findViewById(R.id.txtNickName);
            txtUserId = itemView.findViewById(R.id.txtUserId);
        }
    }
}
