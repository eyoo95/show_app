package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.PartyData;

import java.util.List;


public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> {

    Context context;
    List<PartyData> partyDataList;
    int userId;

    public PartyAdapter(Context context, List<PartyData> partyDataList, int userId) {
        this.context = context;
        this.partyDataList = partyDataList;
        this.userId = userId;

    }

    @NonNull
    @Override
    public PartyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_row, parent, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull PartyAdapter.ViewHolder holder, int position) {
        PartyData partyData = partyDataList.get(position);

        holder.txtNickName.setText(partyData.getNickname());
        holder.txtMsg.setText(partyData.getMsg());
        holder.txtCreatedAt.setText(partyData.getCreatedAt());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        Log.i("userIDLog", partyData.getUserId() + "");
        Log.i("userIDLog2", this.userId + "");

        //채팅 친 사람의 닉네임이 나의 닉네임이면 오른쪽 정렬
        if(partyData.getUserId() == this.userId) {
            params.weight = 1.0f;
            params.gravity = Gravity.END;
            holder.txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.txtNickName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.txtCreatedAt.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.cardView.setLayoutParams(params);
        }
        else {
            params.weight = 1.0f;
            params.gravity = Gravity.START;
            holder.txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.txtNickName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.txtCreatedAt.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.cardView.setLayoutParams(params);

        }

    }

    @Override
    public int getItemCount() {

        //삼항 연산자
        return partyDataList == null ? 0 :  partyDataList.size();
    }

    public PartyData getChat(int position) {
        return partyDataList != null ? partyDataList.get(position) : null;
    }


    public void addChat(PartyData party) {
        partyDataList.add(party);
        notifyItemInserted(partyDataList.size() - 1); //갱신
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView txtNickName;
        TextView txtMsg;
        TextView txtCreatedAt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtNickName = itemView.findViewById(R.id.txtNickName);
            txtMsg = itemView.findViewById(R.id.txtMsg);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);

        }
    }
}
