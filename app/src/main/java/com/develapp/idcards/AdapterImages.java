package com.develapp.idcards;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AdapterImages extends RecyclerView.Adapter<AdapterImages.MyViewHolder> {
	Context context;
	ArrayList<DataImages> items;
	int activity;



	public AdapterImages(Context context, ArrayList<DataImages> items, int activity){
		this.context=context;
		this.items=items;

		this.activity=activity;
	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image
				,parent,false);
		MyViewHolder myViewHolder=new MyViewHolder(v);
		return myViewHolder;
	}



	@Override
	public void onBindViewHolder(final MyViewHolder holder,final int position){

		Picasso.get().load(items.get(position).id_image).into(holder.img_item);//.placeholder(R.drawable.logo)
		/*if (activity==1){
			((SHGFormActivity) context).mandal_tv.setText(items.get(position).id);
		}*/
	//	holder.tv_home_item.setText(items.get(position).title);
		/*holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity==0){
					((RegisterActivity)context).mandals_popup_ll.setVisibility(View.GONE);
					((RegisterActivity)context).mandalId=items.get(position).id;
					((RegisterActivity)context).mandal_tv.setText(items.get(position).title);
					//Session.setMandal(context,items.get(position).id,items.get(position).title);
				}
				else {
					((SHGFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((SHGFormActivity) context).mandalId = items.get(position).id;
					((SHGFormActivity) context).mandal_tv.setText(items.get(position).title);
				}
			}
		});*/

	}
	public int getItemCount(){
		return items.size();
	}

	public  class MyViewHolder extends RecyclerView.ViewHolder
	{
		ImageView img_item;

		public MyViewHolder(View itemView){
			super(itemView);
			img_item = (ImageView) itemView.findViewById(R.id.img_item);

		}
	}
}
