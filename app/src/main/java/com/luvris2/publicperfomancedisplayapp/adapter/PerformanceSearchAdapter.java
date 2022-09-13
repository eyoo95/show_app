package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.KopisApiPerformance;
import com.luvris2.publicperfomancedisplayapp.ui.PerformanceInfoActivity;

import java.util.List;

public class PerformanceSearchAdapter extends RecyclerView.Adapter<PerformanceSearchAdapter.ViewHolder>{
    Context context;
    List<KopisApiPerformance> performanceList;

    // 공연 포스터 url
    String imgUrl;
    String prfName;
    String prfPlace;
    String prfGenre;
    String stEdDate;
    String prfState;
    String mt20id;

    public PerformanceSearchAdapter(Context context, List<KopisApiPerformance> performanceList) {
        this.context = context;
        this.performanceList = performanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.performance_search_adapter_row, parent, false);
        return new ViewHolder(view);
    }

    // 메모리에 있는 데이터를 화면에 표시하는 메소드
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KopisApiPerformance performance = performanceList.get(position);

        // 멤버변수화
        mt20id = performance.getPrfId();
        imgUrl = performance.getPosterUrl();
        prfName = performance.getPrfName();
        prfPlace = performance.getPrfPlace();
        prfGenre = performance.getPrfGenre();
        prfState = performance.getPrfState();
        stEdDate = performance.getPrfpdfrom() + " ~ " + performance.getPrfpdto();

        // 화면에 표시
        holder.txtPrfName.setText(prfName);
        holder.txtPrfPlace.setText(prfPlace);
        holder.txtPrfGenre.setText(prfGenre);
        holder.txtPrfState.setText(prfState);
        holder.txtStEdDate.setText(stEdDate);
        //holder.ratingBar.setRating(performance.getRating);

        GlideUrl url = new GlideUrl(imgUrl,
                new LazyHeaders.Builder().addHeader("User-Agent", "Android").build());
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_not_supported).fitCenter().into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return performanceList.size();
    }

    // 화면에 있는 뷰를 연결 시키는 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPrfName, txtPrfPlace, txtPrfGenre, txtPrfState, txtStEdDate;
        ImageView imgPoster;
        CardView cardView;
        RatingBar ratingBar;

        // 생성자에 뷰와 연결시키는 코드
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrfPlace = itemView.findViewById(R.id.txtPrfPlace);
            txtPrfName = itemView.findViewById(R.id.txtPrfName);
            txtPrfGenre = itemView.findViewById(R.id.txtPrfGenre);
            txtPrfState = itemView.findViewById(R.id.txtPrfState);
            txtStEdDate = itemView.findViewById(R.id.txtStEdDate);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            cardView = itemView.findViewById(R.id.cardView);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            // 카드뷰 클릭 이벤트
            cardView.setOnClickListener(view -> {
                // 유저가 몇번째 행을 클릭했는지 인덱스 저장
                int index = getAdapterPosition();

                // 인덱스의 저장된 데이터 호출
                KopisApiPerformance Performance = performanceList.get(index);
                Log.i("recyclerView Adapter", "recycler Adapter index : " + index, null);

                // 수정하는 액티비티로 데이터 전달 // 후에 상세정보 불러오기로 바꿔야함 // 우선 기본틀만
                Intent intent = new Intent(context, PerformanceInfoActivity.class);
                intent.putExtra("mt20id",Performance.getPrfId());

                Log.i("recyclerView Adapter 2", "Performance.getPrfId() : " + Performance.getPrfId(), null);

                context.startActivity(intent);
            });
        }


    }
}