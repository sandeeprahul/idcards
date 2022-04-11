package com.develapp.idcards;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterDesgination extends RecyclerView.Adapter<AdapterDesgination.MyViewHolder> {
	Context context;
	ArrayList<DataDesgnations> items;
	int i;

	public AdapterDesgination(Context context, ArrayList<DataDesgnations> items,int i){
		this.context=context;
		this.items=items;
		this.i=i;

	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_designation
				,parent,false);
		MyViewHolder myViewHolder=new MyViewHolder(v);

		return myViewHolder;
	}
	@Override
	public void onBindViewHolder(final MyViewHolder holder,final int position){

		holder.tv_home_item.setText(items.get(position).title);
		holder.tv_home_item.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (i==0) {


					Log.e("onClick: ", items.get(position).title);
					((SHGFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					//((SHGFormActivity)context).close_tv_popup.performClick();
					((SHGFormActivity) context).desg_tv.setText(items.get(position).title);
					((SHGFormActivity) context).desg = items.get(position).title;
				}
				else if (i==2){
					Log.e("onClick: ", items.get(position).title);
					((Emp_temp) context).mandals_popup_ll.setVisibility(View.GONE);
					//((SHGFormActivity)context).close_tv_popup.performClick();
					((Emp_temp) context).desg_tv.setText(items.get(position).title);
					((Emp_temp) context).desg = items.get(position).title;
				}

			}
		});

	}
	public int getItemCount(){
		return items.size();
	}

	public  class MyViewHolder extends RecyclerView.ViewHolder
	{
		TextView tv_home_item;

		public MyViewHolder(View itemView){
			super(itemView);
			tv_home_item = (TextView) itemView.findViewById(R.id.tv_desg_item);

		}
	}
}
