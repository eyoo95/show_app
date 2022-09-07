package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
        // list_row
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Posting posting = postingList.get(position);

        //list_row
        holder.txtTitle.setText(posting.getTitle());
        holder.txtCreatedAt.setText(posting.getCreatedAt().substring(5));
        holder.txtRecommend.setText("추천 수 " + posting.getRecommend());
        holder.txtNickName.setText(posting.getNickname());
    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //list_row
        CardView cardView;
        TextView txtTitle;
        TextView txtCreatedAt;
        TextView txtRecommend;
        TextView txtNickName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //list_row
            cardView = itemView.findViewById(R.id.cardView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtRecommend = itemView.findViewById(R.id.txtRecommend);
            txtNickName = itemView.findViewById(R.id.txtNickName);

            // 카드뷰를 클릭하면 글의 위치 정보를 넘겨준다.
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 전체 글 중 선택한 글의 위치 정보를 가져온다
                    int index = getAdapterPosition();
                    Posting postingId = postingList.get(index);

                    // 전체 글 상세보기의 액티비티에 정보를 넘겨준다.
                    Intent intent = new Intent(context, PostingReadActivity.class);
                    intent.putExtra("postingId", postingId);

                    context.startActivity(intent);

                }
            });
        }
    }
}
