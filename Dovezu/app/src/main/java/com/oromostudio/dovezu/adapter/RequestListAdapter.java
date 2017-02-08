package com.oromostudio.dovezu.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oromostudio.dovezu.R;
import com.oromostudio.dovezu.models.RequestModel;

import java.util.List;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewHolder> {

    private List<RequestModel> data;

    public RequestListAdapter(List<RequestModel> data) {
        this.data = data;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        holder.statusTV.setText(data.get(position).getStatus());
        holder.startPointTV.setText(data.get(position).getStartPoint());
        holder.endPointTV.setText(data.get(position).getEndPoint());
        holder.freeSeatsTV.setText(data.get(position).getFreeSeats());
        holder.timeTV.setText(data.get(position).getTime());
        holder.waitTimeTV.setText(data.get(position).getWaitTime());
        holder.minPriceTV.setText(data.get(position).getMinPrice());
        holder.maxPriceTV.setText(data.get(position).getMaxPrice());
        holder.commentTV.setText(data.get(position).getComment());
        holder.acceptedTV.setText(data.get(position).getAccepted().toString());
        holder.partnerTV.setText(data.get(position).getPartnerID());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView statusTV;
        TextView startPointTV;
        TextView endPointTV;
        TextView freeSeatsTV;
        TextView timeTV;
        TextView waitTimeTV;
        TextView minPriceTV;
        TextView maxPriceTV;
        TextView commentTV;
        TextView acceptedTV;
        TextView partnerTV;


        public RequestViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);

            statusTV     = (TextView) itemView.findViewById(R.id.reqStatus);
            startPointTV = (TextView) itemView.findViewById(R.id.reqStartPoint);
            endPointTV   = (TextView) itemView.findViewById(R.id.reqEndPoint);
            freeSeatsTV  = (TextView) itemView.findViewById(R.id.reqFreeSeats);
            timeTV       = (TextView) itemView.findViewById(R.id.reqTime);
            waitTimeTV   = (TextView) itemView.findViewById(R.id.reqWaitTime);
            minPriceTV   = (TextView) itemView.findViewById(R.id.reqMinPrice);
            maxPriceTV   = (TextView) itemView.findViewById(R.id.reqMaxPrice);
            commentTV    = (TextView) itemView.findViewById(R.id.reqComment);
            acceptedTV   = (TextView) itemView.findViewById(R.id.reqAccepted);
            partnerTV    = (TextView) itemView.findViewById(R.id.reqPartner);
        }
    }
}
