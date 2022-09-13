package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.Review;
import com.luvris2.publicperfomancedisplayapp.model.ReviewList;

import java.util.ArrayList;
import java.util.List;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder>{

    Context context;
    List<Review> reviewList;

    public MyReviewAdapter(Context context, ArrayList<Review> reviewList){
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);

        // todo: 꼭 워크벤치로 Review DB 확인하고 컬럼명 Model.Review 파일 수정 후, 아래 코드 수정할 것.
        holder.txtRatingTitle.setText(review.getMt20id());
        holder.txtRatingContent.setText(review.getId());
        holder.txtRatingCreatedAt.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        // RecyclerView 의 총 개수
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtRatingTitle;
        TextView txtRatingContent;
        TextView txtRatingCreatedAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRatingTitle = itemView.findViewById(R.id.txtRatingTitle);
            txtRatingContent = itemView.findViewById(R.id.txtRatingContent);
            txtRatingCreatedAt = itemView.findViewById(R.id.txtRatingCreatedAt);
        }
    }

}

