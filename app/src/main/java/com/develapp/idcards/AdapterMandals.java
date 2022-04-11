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


public class AdapterMandals extends RecyclerView.Adapter<AdapterMandals.MyViewHolder> {
	Context context;
	ArrayList<DataMandals> items;
	int activity;
	ArrayList<DataMandals> items_all;
	PlanetFilter planetFilter;



	public AdapterMandals(Context context, ArrayList<DataMandals> items,int activity){
		this.context=context;
		this.items=items;
		this.items_all=items;
		this.activity=activity;
	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype){
		View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_designation
				,parent,false);
		MyViewHolder myViewHolder=new MyViewHolder(v);
		return myViewHolder;
	}


	private class PlanetFilter extends Filter {
		Boolean clear_all;

		private PlanetFilter() {
			this.clear_all = Boolean.valueOf(false);
		}

		protected FilterResults performFiltering(CharSequence constraint) {
			Log.e("FilterResults", "FilterResults");
			FilterResults results = new FilterResults();
			this.clear_all = Boolean.valueOf(false);

			if (constraint == null || constraint.length() == 0) {
				Log.e("FilterResults", "length=0");

				this.clear_all = Boolean.valueOf(true);
				results.values = AdapterMandals.this.items;
				results.count = AdapterMandals.this.items.size();
			} else {
				Log.e("FilterResults", "length=1");


				//constraint=constraint.toString().toLowerCase();
				//STORE OUR FILTERED PLAYERS

				List<DataMandals> temp = new ArrayList();
				for (DataMandals d : items) {
					//or use .equal(text) with you want equal match
					//use .toLowerCase() for better matches
					if (d.title.toLowerCase().contains(constraint)) {
						temp.add(d);
					}
					else if (d.title.contains(constraint)){
						temp.add(d);
					}
					else if (d.title.toUpperCase().contains(constraint)){
						temp.add(d);
					}
                  /*  if(d==null){
                        if (d.contains(constraint)){
                            temp.add(d);
                        }
                        // Toast.makeText(ChatActivity.this,"No results found",Toast.LENGTH_SHORT).show();
                    }
                    else if (d==null){
                        if (d.contains(constraint)){
                            temp.add(d);
                        }

                    }*/
				}

               /* for (int i=0;i<messages.size();i++)
                {



                    if(messages.get(i).getUr_message()==null){
                        if(messages.get(i).getMy_message().toUpperCase().contains(constraint))
                        {
                            //ADD PLAYER TO FILTERED PLAYERS
                            filteredPlayers.add(messages.get(i));
                        }
                        // Toast.makeText(ChatActivity.this,"No results found",Toast.LENGTH_SHORT).show();
                    }
                    else if (messages.get(i).getMy_message()==null){
                        if(messages.get(i).getUr_message().toUpperCase().contains(constraint))
                        {
                            //ADD PLAYER TO FILTERED PLAYERS
                            filteredPlayers.add(messages.get(i));
                        }

                    }
                    //CHECK

                }*/
              /*  while (it.hasNext()) {
                    MyCurrentChat.ResponseBean p = (MyCurrentChat.ResponseBean) it.next();
                    *//*if (Pattern.compile(Pattern.quote(constraint.toString()),2).matcher(p.getMy_message()).find()) {
                        nPlanetList.add(p);
                    }*//*

                    if(p.getUr_message()==null){
                        if (Pattern.compile(Pattern.quote(constraint.toString()),2).matcher(p.getMy_message()).find()) {
                            nPlanetList.add(p);
                        }
                        // Toast.makeText(ChatActivity.this,"No results found",Toast.LENGTH_SHORT).show();
                    }
                    else if (p.getMy_message()==null){
                        if (Pattern.compile(Pattern.quote(constraint.toString()),2).matcher(p.getUr_message()).find()) {
                            nPlanetList.add(p);
                        }

                    }
                }*/
				results.values = temp;
				results.count = temp.size();
			}
			return results;
		}



		protected void publishResults(CharSequence constraint, FilterResults results) {

			if (results.count == 0) {
				AdapterMandals.this.notifyDataSetChanged();
			} else if (this.clear_all.booleanValue()) {
				AdapterMandals.this.items = AdapterMandals.this.items_all;
				AdapterMandals.this.notifyDataSetChanged();
			} else {
				AdapterMandals.this.items = (ArrayList) results.values;
				AdapterMandals.this.notifyDataSetChanged();
			}
		}
	}

	//filter

	public Filter getFilter() {
		Log.e("getFilter", "getFilter");
		if (this.planetFilter == null) {
			this.planetFilter = new PlanetFilter();
		}
		return this.planetFilter;
	}

	public void updateList(ArrayList<DataMandals> list){
		items = list;
		notifyDataSetChanged();
	}


	@Override
	public void onBindViewHolder(final MyViewHolder holder,final int position){

		/*if (activity==1){
			((SHGFormActivity) context).mandal_tv.setText(items.get(position).id);
		}*/
		holder.tv_home_item.setText(items.get(position).title);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity==0){
					((RegisterActivity)context).mandals_popup_ll.setVisibility(View.GONE);
					((RegisterActivity)context).mandalId=items.get(position).id;
					((RegisterActivity)context).mandal_tv.setText(items.get(position).title);
					//((RegisterActivity)context).mandal_tv.setText(items.get(position).title);
					//mandals_popup_ll
					//Session.setMandal(context,items.get(position).id,items.get(position).title);
				}
				else if(activity==2){
					((NREGSFormActivity)context).mandals_popup_ll.setVisibility(View.GONE);
					((NREGSFormActivity)context).mandalId=items.get(position).id;
					((NREGSFormActivity)context).mandal_tv.setText(items.get(position).title);
				}
				else {
					((SHGFormActivity) context).mandals_popup_ll.setVisibility(View.GONE);
					((SHGFormActivity) context).mandalId = items.get(position).id;
					((SHGFormActivity) context).mandal_tv.setText(items.get(position).title);
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
