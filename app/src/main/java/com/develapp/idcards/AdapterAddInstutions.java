package com.develapp.idcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterAddInstutions extends RecyclerView.Adapter<AdapterAddInstutions.MyViewHolder> {
	Context context;
	ArrayList<String> items;

	public AdapterAddInstutions(Context context, ArrayList<String> items){
		this.context=context;
		this.items=items;

	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add
				,parent,false);
		MyViewHolder myViewHolder=new MyViewHolder(v);

		return myViewHolder;
	}
	@Override
	public void onBindViewHolder(final MyViewHolder holder,final int position){

		holder.tv_add_item.setText(items.get(position));
		holder.minus_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//((SHGFormActivity)context).mandals_popup_ll.setVisibility(View.GONE);

				removeAt(position);
			}
		});

	}
	public int getItemCount(){
		return items.size();
	}

	public  class MyViewHolder extends RecyclerView.ViewHolder
	{
		TextView tv_add_item;
		ImageView minus_img;

		public MyViewHolder(View itemView){
			super(itemView);
			tv_add_item = (TextView) itemView.findViewById(R.id.tv_add_item);
			minus_img = (ImageView) itemView.findViewById(R.id.minus_img);
		}
	}
	public void removeAt(int position) {
		items.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, items.size());
	}
}
