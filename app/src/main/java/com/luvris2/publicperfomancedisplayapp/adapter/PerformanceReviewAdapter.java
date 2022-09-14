package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.Review;

import java.util.ArrayList;
import java.util.List;

public class PerformanceReviewAdapter extends RecyclerView.Adapter<PerformanceReviewAdapter.ViewHolder>{
    Context context;
    List<Review> reviewList;

    public PerformanceReviewAdapter(Context context, ArrayList<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perfomance_info_review_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        // todo: 꼭 워크벤치로 Review DB 확인하고 컬럼명 Model.Review 파일 수정 후, 아래 코드 수정할 것.
        holder.txtPerfomInfoReviewTitle.setText(review.getTitle());
        holder.txtPerfomInfoReviewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPerfomInfoReviewTitle;
        TextView txtPerfomInfoReviewContent;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조
            txtPerfomInfoReviewTitle = itemView.findViewById(R.id.txtPerfomInfoReviewTitle);
            txtPerfomInfoReviewContent = itemView.findViewById(R.id.txtPerfomInfoReviewContent);
        }
    }
}
