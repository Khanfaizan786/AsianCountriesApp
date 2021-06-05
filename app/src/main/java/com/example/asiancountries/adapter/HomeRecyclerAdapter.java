package com.example.asiancountries.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asiancountries.R;
import com.example.asiancountries.model.Country;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyHolder>{
    Context context;
    List<Country> country;

    class MyHolder extends RecyclerView.ViewHolder{

        TextView txtCountryName;
        TextView txtCapital;
        TextView txtRegion;
        TextView txtSubregion;
        TextView txtPopulation;
        TextView txtBorders;
        TextView txtLanguages;
        ImageView imgFlag;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtCountryName = itemView.findViewById(R.id.txtCountryName);
            txtCapital = itemView.findViewById(R.id.txtCapital);
            txtRegion = itemView.findViewById(R.id.txtRegion);
            txtSubregion = itemView.findViewById(R.id.txtSubregion);
            txtPopulation = itemView.findViewById(R.id.txtPopulation);
            txtBorders = itemView.findViewById(R.id.txtBorders);
            txtLanguages = itemView.findViewById(R.id.txtLanguages);
            imgFlag = itemView.findViewById(R.id.imgCountryFlag);
        }
    }

    public HomeRecyclerAdapter(Context context, List<Country> country) {
        this.context = context;
        this.country = country;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.country_info_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String countryName = country.get(position).getName();
        String capital = country.get(position).getCapital();
        String region = country.get(position).getRegion();
        String subregion = country.get(position).getSubregion();
        int population = country.get(position).getPopulation();
        String borders = country.get(position).getBorders();
        String languages = country.get(position).getLanguages();
        String flag = country.get(position).getFlag();

        holder.txtCountryName.setText(countryName);
        holder.txtCapital.setText("Capital - "+capital);
        holder.txtRegion.setText("Region - "+region);
        holder.txtSubregion.setText("Subregion - "+subregion);
        holder.txtPopulation.setText("Population - "+String.valueOf(population));
        holder.txtBorders.setText("Borders - "+borders);
        holder.txtLanguages.setText("Languages - "+languages);

        try {
            Picasso.with(context).load(Uri.parse(flag)).placeholder(R.mipmap.ic_launcher).fit().into(holder.imgFlag);
        } catch(Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return country.size();
    }
}
