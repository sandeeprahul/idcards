package com.develapp.idcards;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AdapterRelations extends RecyclerView.Adapter<AdapterRelations.MyViewHolder> {
	Context context;
	ArrayList<String> items;

	int activity;


	public AdapterRelations(Context context, ArrayList<String> items,int activity){
		this.context=context;
		this.items=items;

		this.activity = activity;
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

		holder.tv_home_item.setText(items.get(position));
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity==0) {
					((SHGFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((SHGFormActivity) context).relations = items.get(position);
					((SHGFormActivity) context).relation_tv.setText(items.get(position));
				}
				else if (activity==1){
					((SHGFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((SHGFormActivity) context).desg = items.get(position);
					((SHGFormActivity) context).desg_tv.setText(items.get(position));
				}
				else if (activity==2){
					((StudentFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((StudentFormActivity) context).inst_name = items.get(position);
					((StudentFormActivity) context).inst_name_tv.setText(items.get(position));
				}
				else if (activity==3){
					((StudentFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((StudentFormActivity) context).class_name = items.get(position);
					((StudentFormActivity) context).class_edt.setText(items.get(position));
				}

				else if (activity==4){
					((StudentFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((StudentFormActivity) context).section = items.get(position);
					((StudentFormActivity) context).section_edt.setText(items.get(position));
				}
				else if (activity==5){
					((EmployeeFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((EmployeeFormActivity) context).inst_name = items.get(position);
					((EmployeeFormActivity) context).inst_name_tv.setText(items.get(position));
				}
				else if (activity==6){
					((EmployeeFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((EmployeeFormActivity) context).relations = items.get(position);
					((EmployeeFormActivity) context).relation_tv.setText(items.get(position));
				}
				else if (activity==7){
					((EmployeeFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((EmployeeFormActivity) context).desg = items.get(position);
					((EmployeeFormActivity) context).desg_tv.setText(items.get(position));
				}

				else if (activity==8){
					((Emp_temp) context).mandals_popup_ll.setVisibility(View.GONE);
					((Emp_temp) context).relations = items.get(position);
					((Emp_temp) context).relation_tv.setText(items.get(position));
				}
				else if (activity==9){
					((Emp_temp) context).mandals_popup_ll.setVisibility(View.GONE);
					((Emp_temp) context).desg = items.get(position);
					((Emp_temp) context).desg_tv.setText(items.get(position));
				}
				else if (activity==10){
					((Emp_temp) context).mandals_popup_ll.setVisibility(View.GONE);
					((Emp_temp) context).inst_name = items.get(position);
					((Emp_temp) context).inst_name_tv.setText(items.get(position));
				}
				else if (activity==11){
					((StudentFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((StudentFormActivity) context).relations = items.get(position);
					((StudentFormActivity) context).relation_tv.setText(items.get(position));
				}
				else if (activity == 12){
					((NREGSFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((NREGSFormActivity) context).relations = items.get(position);
					((NREGSFormActivity) context).relation_tv.setText(items.get(position));
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
