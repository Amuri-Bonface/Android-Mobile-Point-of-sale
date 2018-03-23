package com.ngaitai.ngaitaiafricaapp;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Shopping_list extends ArrayAdapter<ProductDescription>{
    private Activity context;
    private List<ProductDescription> productList;

    public Shopping_list(Activity context, List<ProductDescription> productList)
    {
        super(context,R.layout.shopping_cart_custom_list,productList);
        this.context=context;
        this.productList=productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.shopping_cart_custom_list, null, true);

        TextView itemPrice = (TextView) view.findViewById(R.id.textViewPrice);
        TextView itemName = (TextView) view.findViewById(R.id.textViewItem);
        TextView itemBV = (TextView) view.findViewById(R.id.textViewBV);
        TextView name = (TextView) view.findViewById(R.id.textViewName);
        TextView qty = (TextView) view.findViewById(R.id.textViewQty);

        TextView sumBv = (TextView) view.findViewById(R.id.textViewTotBV);
        TextView sumPrice = (TextView) view.findViewById(R.id.textViewTotPrice);



        ProductDescription product=productList.get(position);

        itemName.setText("Item Name: "+product.getDescription());
        itemPrice.setText("Selling Price: "+product.getSellingPrice());
        itemBV.setText("Points: "+product.getBV());
        name.setText("Cust Name: "+product.getDistributorName());
        qty.setText("Quantity: "+product.getQuantity());
        sumBv.setText("Total Points: "+String.valueOf(product.getTotBv()));
        sumPrice.setText("Total Cash: "+String.valueOf((int) product.getTotPrice()));

        return  view;

    }


}
