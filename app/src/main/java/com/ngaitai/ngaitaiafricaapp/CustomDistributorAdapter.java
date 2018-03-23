package com.ngaitai.ngaitaiafricaapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomDistributorAdapter extends ArrayAdapter<DistributorModel> {

    ArrayList<DistributorModel> distributors;
    Context context;
    int resource;

    public CustomDistributorAdapter(Context context, int resource, ArrayList<DistributorModel> distributors) {
        super(context, resource, distributors);
        this.distributors = distributors;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_distributor_layout, null, true);

        }
        DistributorModel distributors = getItem(position);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtName.setText(distributors.getDistibutorName());

        TextView txtDiscode = (TextView) convertView.findViewById(R.id.txtDiscode);
        txtDiscode.setText(distributors.getDistributorCode());

        TextView txtDisphone = (TextView) convertView.findViewById(R.id.txtphone);
        txtDisphone.setText(distributors.getDistributorphone());

        return convertView;
    }
}
