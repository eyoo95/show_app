package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.PartyData;
import com.luvris2.publicperfomancedisplayapp.model.Posting;
import com.luvris2.publicperfomancedisplayapp.ui.PartyActivity;

import java.util.ArrayList;
import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder>{

    Context context;
    List<PartyData> partyDataList;

    String myNickName;

    public PartyAdapter(Context context, List<PartyData> partydataList, String myNickName) {
        this.context = context;
        this.partyDataList = partydataList;

        //닉네임을 받아넣는 파라미터
        this.myNickName = myNickName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_row, parent, false);
        return new PartyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PartyData partyData = partyDataList.get(position);

        holder.txtNickName.setText(partyData.getNickname());
        holder.txtMsg.setText(partyData.getMsg());

        //내가 메세지를 보냈다면 오른쪽 끝에서 보이고
        //남이 보냈다면 왼쪽 끝에서 보임
        if(partyData.getNickname().equals(this.myNickName)) {
            holder.txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.txtNickName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        else {
            holder.txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.txtNickName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
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

    public void addChat(PartyData chat) {
        partyDataList.add(chat);
        notifyItemInserted(partyDataList.size()-1); //갱신
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNickName;
        TextView txtMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNickName = itemView.findViewById(R.id.txtNickName);
            txtMsg = itemView.findViewById(R.id.txtMsg);
        }
    }
}
