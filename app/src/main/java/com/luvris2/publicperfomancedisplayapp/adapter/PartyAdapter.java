package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    String myNickName;

    public PartyAdapter(Context context, List<PartyData> partyDataList, String myNickName) {
        this.context = context;
        this.partyDataList = partyDataList;
        this.myNickName = myNickName;
    }

    @NonNull
    @Override
    public PartyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_row, parent, false);
        return new PartyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyAdapter.ViewHolder holder, int position) {
        PartyData partyData = partyDataList.get(position);

        holder.txtNickName.setText(partyData.getNickname());
        holder.txtMsg.setText(partyData.getMsg());
        holder.txtCreatedAt.setText(partyData.getCreatedAt());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //채팅 친 사람의 닉네임이 나의 닉네임이면 오른쪽 정렬
        if(partyData.getNickname().equals(this.myNickName)) {
            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;
            holder.cardView.setLayoutParams(params);;
        }
        else {
            params.weight = 1.0f;
            params.gravity = Gravity.LEFT;
            holder.cardView.setLayoutParams(params);;
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
        notifyItemInserted(partyDataList.size()-1); //갱신
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
























//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public CardView cardView;
//        public TextView txtMsg;
//        public TextView txtNickName;
//        public TextView txtCreatedAt;
//        public View rootView;
//        public MyViewHolder(View v) {
//            super(v);
//            cardView = v.findViewById(R.id.cardView);
//            txtNickName = v.findViewById(R.id.txtNickName);
//            txtMsg = v.findViewById(R.id.txtMsg);
//            txtCreatedAt = v.findViewById(R.id.txtCreatedAt);
//            rootView = v;
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public PartyAdapter(List<PartyData> myDataset, Context context, String myNickName) {
//        //{"1","2"}
//        mDataset = myDataset;
//        this.myNickName = myNickName;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public PartyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                       int viewType) {
//        // create a new view
//        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.party_row, parent, false);
//
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        PartyData party = mDataset.get(position);
//
//        holder.txtMsg.setText(party.getMsg());
//        holder.txtNickName.setText(party.getNickname());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
//
//        //채팅 친 사람의 닉네임이 나의 닉네임이면 오른쪽 정렬
//        if(party.getNickname().equals(this.myNickName)) {
//            params.gravity = Gravity.END;
//            holder.cardView.setLayoutParams(params);
//        }
//        else {
//            params.gravity = Gravity.START;
//            holder.cardView.setLayoutParams(params);
//        }
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//
//        //삼항 연산자
//        return mDataset == null ? 0 :  mDataset.size();
//    }
//
//    public PartyData getChat(int position) {
//        return mDataset != null ? mDataset.get(position) : null;
//    }
//
//    public void addChat(PartyData party) {
//        mDataset.add(party);
//        notifyItemInserted(mDataset.size()-1); //갱신
//    }
//
//}
