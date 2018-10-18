package com.example.zizoj.soleektask.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zizoj.soleektask.R;
import com.example.zizoj.soleektask.Network.model.Response;

import java.util.ArrayList;

public class CountriesListAdpter extends RecyclerView.Adapter<CountriesListAdpter.HolderView> {

    ArrayList<Response> CountriesList = new ArrayList<>();

    public CountriesListAdpter( ArrayList<Response> CountriesList) {

        this.CountriesList = CountriesList;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_list_item,parent,false);

        HolderView view = new HolderView(layout);

        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {

        holder.CountryName.setText(CountriesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return CountriesList.size();
    }

    class HolderView extends RecyclerView.ViewHolder  {

        TextView CountryName;

        public HolderView(View itemView) {
            super(itemView);

            CountryName = itemView.findViewById(R.id.CountryNameTxt);
        }
    }
}
