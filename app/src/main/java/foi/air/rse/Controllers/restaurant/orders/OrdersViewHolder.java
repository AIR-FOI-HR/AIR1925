package foi.air.rse.Controllers.restaurant.orders;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder {
    public TextView userOrder, datetimeOrder, numOfPersonsOrder, priceOrder, restaurantRating;
    public Button btnAccept, btnDenied, btnInfo;
    public LinearLayout bullet;
    public RelativeLayout displayLayoutRestaurant;

    public OrdersViewHolder(@NonNull View itemView){
        super(itemView);

        bullet=itemView.findViewById(R.id.status_bullet);

        btnAccept=itemView.findViewById(R.id.button_accept);
        btnDenied=itemView.findViewById(R.id.button_denied);
        btnInfo=itemView.findViewById(R.id.button_information);

        userOrder=itemView.findViewById(R.id.user_order);
        datetimeOrder=itemView.findViewById(R.id.order_datetime);
        numOfPersonsOrder=itemView.findViewById(R.id.number_of_persons);
        priceOrder=itemView.findViewById(R.id.order_price);
        restaurantRating=itemView.findViewById(R.id.restaurant_rating_restaurant_view);

        displayLayoutRestaurant=itemView.findViewById(R.id.display_layout_order_restaurant);


    }


}
