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
import com.luvris2.publicperfomancedisplayapp.ui.PostingEditActivity;

import java.util.List;

public class PostingMyAdapter extends RecyclerView.Adapter<PostingMyAdapter.ViewHolder> {

    Context context;
    List<Posting> postingList;

    public PostingMyAdapter(Context context, List<Posting> postingList) {
        this.context = context;
        this.postingList = postingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posting_my_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Posting posting = postingList.get(position);
        holder.txtTitle.setText(posting.getTitle());
        holder.txtCreatedAt.setText(posting.getCreatedAt());
        holder.txtContent.setText(posting.getContent());
    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtTitle;
        TextView txtCreatedAt;
        TextView txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtContent = itemView.findViewById(R.id.txtContent);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 내가 쓴 글의 위치 정보를 가져온다
                    int index = getAdapterPosition();
                    Posting postingId = postingList.get(index);

                    // 내가 쓴글을 수정할수있는 액티비티에, 내가 쓴글 정보를 넘겨준다.
                    Intent intent = new Intent(context, PostingEditActivity.class);
                    intent.putExtra("postingId", postingId);

                    context.startActivity(intent);
                }
            });
        }
    }
}
