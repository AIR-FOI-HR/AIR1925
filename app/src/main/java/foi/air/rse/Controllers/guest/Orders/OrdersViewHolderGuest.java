package foi.air.rse.Controllers.guest.Orders;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;


public class OrdersViewHolderGuest extends  RecyclerView.ViewHolder {

     TextView dateTimeOrderGuest, numOfPersonsOrderGuest, priceOrderGuest, restaurantNameOrderGuest, adressOrderGuest;
     Button btnPayOrder, btnDeniedGuest, btnInfoGuest, btnRateGuest;
     LinearLayout bulletGuest;
     RelativeLayout  displayOrderGuest;



    public OrdersViewHolderGuest(@NonNull View itemView) {
        super(itemView);

        bulletGuest=itemView.findViewById(R.id.status_bulletGuest);

        btnPayOrder=itemView.findViewById(R.id.button_payOrderGuest);
        btnDeniedGuest=itemView.findViewById(R.id.button_deniedGuest);
        btnInfoGuest=itemView.findViewById(R.id.button_informationGuest);
        btnRateGuest=itemView.findViewById(R.id.button_rateOrderGuest);

        restaurantNameOrderGuest=itemView.findViewById(R.id.restaurant_orderGuest);
        dateTimeOrderGuest=itemView.findViewById(R.id.order_datetimeGuest);
        numOfPersonsOrderGuest=itemView.findViewById(R.id.number_of_personsGuest);
        priceOrderGuest=itemView.findViewById(R.id.order_priceGuest);
        adressOrderGuest=itemView.findViewById(R.id.restaurant_adress_orderGuest);
        displayOrderGuest=itemView.findViewById(R.id.displayOrderGuest);
    }
}
