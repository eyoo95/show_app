package com.luvris2.publicperfomancedisplayapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.publicperfomancedisplayapp.R;
import com.luvris2.publicperfomancedisplayapp.model.PartyRoom;
import com.luvris2.publicperfomancedisplayapp.ui.PartyActivity;

import java.util.List;

public class PartyRoomAdapter extends RecyclerView.Adapter<PartyRoomAdapter.ViewHolder> {

    Context context;
    List<PartyRoom> partyRoomList;

    public PartyRoomAdapter(Context context, List<PartyRoom> chatRoomList) {
        this.context = context;
        this.partyRoomList = chatRoomList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_room_row, parent, false);

        return new PartyRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PartyRoom partyRoom = partyRoomList.get(position);

        holder.txtTitle.setText(partyRoom.getTitle());
        holder.txtNickName.setText(partyRoom.getNickname());
        holder.txtPrfnm.setText(partyRoom.getPrfnm());
    }

    @Override
    public int getItemCount() {
        return partyRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView txtNickName;
        TextView txtTitle;
        TextView txtPrfnm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtNickName = itemView.findViewById(R.id.txtNickName);
            txtPrfnm = itemView.findViewById(R.id.txtPrfnm);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PartyRoom partyRoom = partyRoomList.get(getAdapterPosition());

                    Intent intent = new Intent(context, PartyActivity.class);
                    intent.putExtra("partyRoom", partyRoom);

                    context.startActivity(intent);
                }
            });

        }
    }
}