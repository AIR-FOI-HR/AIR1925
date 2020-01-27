package com.example.readysteadyeat.ui.restaurant.orders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;

public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
    TextView dishName, quantity, price;


    public OrderDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        dishName=itemView.findViewById(R.id.txtDishNameDetails);
        quantity=itemView.findViewById(R.id.txtDishQuantityDetails);
        price=itemView.findViewById(R.id.txtPriceDetails);

    }
}
