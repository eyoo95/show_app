package com.luvris2.publicperfomancedisplayapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.api.NetworkClient;
import com.luvris2.publicperfomancedisplayapp.api.UserApi;
import com.luvris2.publicperfomancedisplayapp.config.Config;
import com.luvris2.publicperfomancedisplayapp.model.PartyData;
import com.luvris2.publicperfomancedisplayapp.model.Posting;
import com.luvris2.publicperfomancedisplayapp.model.User;
import com.luvris2.publicperfomancedisplayapp.model.UserRes;
import com.luvris2.publicperfomancedisplayapp.ui.PartyActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder>{

    Context context;
    List<PartyData> partyDataList;

    public PartyAdapter(Context context, List<PartyData> partyDataList) {
        this.context = context;
        this.partyDataList = partyDataList;
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

        if (partyData.getNickname().equals(holder.txtNickName.getText().toString())) {
            holder.txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.txtNickName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            holder.txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.txtNickName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

        Log.i("Tag1", partyData.getNickname() + "A");
        Log.i("Tag1", holder.txtNickName.getText().toString().trim() + "B");
        //내가 메세지를 보냈다면 오른쪽 끝에서 보이고
        //남이 보냈다면 왼쪽 끝에서 보임
//        if(partyData.getNickname().equals(this.myNickName)) {
//            holder.txtMsg.setGravity(Gravity.END);
//            holder.txtNickName.setGravity(Gravity.END);
//        }
//        else {
//            holder.txtMsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//            holder.txtNickName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//        }
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
