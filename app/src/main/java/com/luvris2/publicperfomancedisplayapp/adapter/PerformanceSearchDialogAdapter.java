package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class PerformanceSearchDialogAdapter extends RecyclerView.Adapter<PerformanceSearchDialogAdapter.ViewHolder> {
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

    public PerformanceSearchDialogAdapter(Context context, List<KopisApiPerformance> performanceList) {
        this.context = context;
        this.performanceList = performanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_row, parent, false);
        return new ViewHolder(view);
    }

    // 메모리에 있는 데이터를 화면에 표시하는 메소드
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        KopisApiPerformance performance = performanceList.get(position);
        // 멤버 변수화
        // 멤버 변수화
        mt20id = performance.getPrfId();
        imgUrl = performance.getPosterUrl();
        prfName = performance.getPrfName();
        prfPlace = performance.getPrfPlace();
        prfGenre = performance.getPrfGenre();
        prfState = performance.getPrfState();
        stEdDate = performance.getPrfpdfrom() + " ~ " + performance.getPrfpdto();
        // 화면에 표시
        holder.txtSearchResultTitle.setText(prfName);
        holder.txtSearchResultPlace.setText(prfPlace);
        holder.txtSearchResultDate.setText(stEdDate);

        // 글라이드 라이브러리 사용
        GlideUrl url = new GlideUrl(imgUrl,
                new LazyHeaders.Builder().addHeader("User-Agent", "Android").build());
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_not_supported).fitCenter().into(holder.imgResultPoster);
    }


    @Override
    public int getItemCount() {
        return performanceList.size();
    }

    // 화면에 있는 뷰를 연결 시키는 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSearchResultTitle, txtSearchResultPlace, txtSearchResultDate;
        ImageView imgResultPoster;
        CardView cardView;

        // 생성자에 뷰와 연결시키는 코드
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSearchResultPlace = itemView.findViewById(R.id.txtSearchResultPlace);
            txtSearchResultTitle = itemView.findViewById(R.id.txtSearchResultTitle);
            txtSearchResultDate = itemView.findViewById(R.id.txtSearchResultDate);
            imgResultPoster = itemView.findViewById(R.id.imgResultPoster);
            cardView = itemView.findViewById(R.id.cardView);

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
