package com.develapp.idcards;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterTodayList extends RecyclerView.Adapter<AdapterTodayList.MyViewHolder> {
	Context context;
	ArrayList<DataImages> items;
	int activity;

	public AdapterTodayList(Context context, ArrayList<DataImages> items,int activity){
		this.context=context;
		this.items=items;
		this.activity = activity;

	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shg
				,parent,false);
		MyViewHolder myViewHolder=new MyViewHolder(v);

		return myViewHolder;
	}
	@Override
	public void onBindViewHolder(final MyViewHolder holder,final int position){

		holder.id_tv_item.setText(items.get(position).id);
		holder.name_tv_item.setText(items.get(position).member_name);
		holder.vo_name_item.setText(items.get(position).vo_name);
		holder.grp_name_item.setText(items.get(position).group_name);
		holder.village_tv_item.setText(items.get(position).village_name);
		/*holder.name_tv_item.setMovementMethod(new ScrollingMovementMethod());
		holder.vo_name_item.setMovementMethod(new ScrollingMovementMethod());
		holder.grp_name_item.setMovementMethod(new ScrollingMovementMethod());
		holder.village_tv_item.setMovementMethod(new ScrollingMovementMethod());*/


		if (items.get(position).image.equals("")){

		}
		else {
			Picasso.get().load(items.get(position).image).into(holder.usr_img_item);
		}

		/*if (activity==1){
			holder.submit_tv.setVisibility(View.GONE);
		}
		else {
			holder.submit_tv.setVisibility(View.VISIBLE);
		}*/

		//for editing
		holder.edit_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity==0){
					((SHGFormActivity)context).dataImages_.add(items.get(position));
					((SHGFormActivity)context).edit(position,items.get(position));
//					((SHGFormActivity)context).membername_edt.requestFocus();

					//((SHGFormActivity)context).calldelete(items.get(position).id);
				}
				else {
					((IDListActivity)context).editDetails(position,items.get(position));
					//((IDListActivity)context).calldelete(items.get(position).id);
				}
			}
		});

		holder.delete_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				showAlert("Confirm Delete?",position);
			}
		});
	}
	public int getItemCount(){
		return items.size();
	}

	public  class MyViewHolder extends RecyclerView.ViewHolder
	{
		TextView id_tv_item,name_tv_item,vo_name_item,grp_name_item,village_tv_item,edit_tv;
		TextView delete_tv;
		ImageView usr_img_item;
		LinearLayout item_rl;

		public MyViewHolder(View itemView){
			super(itemView);
			delete_tv = (TextView) itemView.findViewById(R.id.delete_tv);
			edit_tv = (TextView) itemView.findViewById(R.id.edit_tv);
			id_tv_item = (TextView) itemView.findViewById(R.id.id_tv_item);
			name_tv_item = (TextView) itemView.findViewById(R.id.name_tv_item);
			grp_name_item = (TextView) itemView.findViewById(R.id.grp_name_item);
			village_tv_item = (TextView) itemView.findViewById(R.id.village_tv_item);
			vo_name_item = (TextView) itemView.findViewById(R.id.vo_name_item);
			usr_img_item = (ImageView) itemView.findViewById(R.id.usr_img_item);
			item_rl = (LinearLayout) itemView.findViewById(R.id.item_rl);
		}
	}
	public void removeAt(int position) {
		items.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, items.size());
	}
	public void showAlert(String msg, final int pos){
		AlertDialog.Builder builder
				= new AlertDialog
				.Builder(context);

		builder.setTitle("Alert !");
		builder.setMessage(msg);
		// Set Cancelable false
		// for when the user clicks on the outside
		// the Dialog Box then it will remain show
		builder.setCancelable(true);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (activity==0){
					((SHGFormActivity)context).calldelete(items.get(pos).id);
					removeAt(pos);
				}
				else {
					((IDListActivity)context).calldelete(items.get(pos).id,0);
					removeAt(pos);
				}

				dialog.dismiss();

			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

			}
		});
		final AlertDialog alertDialog = builder.create();


		alertDialog.show();
	}

}
