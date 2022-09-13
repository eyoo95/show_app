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

public class MyInterestingAdapter extends RecyclerView.Adapter<MyInterestingAdapter.ViewHolder> {

    Context context;
    List<KopisApiPerformance> performanceList;

    // 공연 포스터 url
    String imgUrl;
    String prfName;
    String prfPlace;
    String stEdDate;

    public MyInterestingAdapter(@NonNull Context context, List<KopisApiPerformance> interestingPerformanceList) {
        this.context = context;
        this.performanceList = interestingPerformanceList;
    }

    @NonNull
    @Override
    public MyInterestingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_my_interesting_adapter_row,parent,false);
        return new MyInterestingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KopisApiPerformance performance = performanceList.get(position);
        Log.i("My recomm position", position+"");

        // 멤버 변수화
        imgUrl = performance.getPosterUrl();
        prfName = performance.getPrfName();
        prfPlace = performance.getPrfPlace();
        stEdDate = performance.getPrfpdfrom() + " ~ " + performance.getPrfpdto();

        // 화면에 표시
        holder.txtPrfName.setText(prfName);
        holder.txtPrfPlace.setText(prfPlace);
        holder.txtStEdDate.setText(stEdDate);

        GlideUrl url = new GlideUrl(imgUrl,
                new LazyHeaders.Builder().addHeader("User-Agent", "Android").build());
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_not_supported).fitCenter().into(holder.imgPoster);

    }

    @Override
    public int getItemCount() {
        return performanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtPrfName;
        TextView txtPrfPlace;
        TextView txtStEdDate;
        ImageView imgPoster;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPrfPlace = itemView.findViewById(R.id.txtMyInterestingPlace);
            txtPrfName = itemView.findViewById(R.id.txtMyInterestingName);
            txtStEdDate = itemView.findViewById(R.id.txtMyInterestingDate);
            imgPoster = itemView.findViewById(R.id.imgMyInterestingPoster);
            cardView = itemView.findViewById(R.id.cardViewContent);

            cardView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 유저가 몇번째 행을 클릭했는지 인덱스 저장
                    int index = getAdapterPosition();

                    // 인덱스의 저장된 데이터 호출
                    KopisApiPerformance Performance = performanceList.get(index);

                    // 수정하는 액티비티로 데이터 전달 // 후에 상세정보 불러오기로 바꿔야함 // 우선 기본틀만
                    Intent intent = new Intent(context, PerformanceInfoActivity.class);
                    intent.putExtra("mt20id",Performance.getPrfId());

                    context.startActivity(intent);
                }
            });

        }
    }
}