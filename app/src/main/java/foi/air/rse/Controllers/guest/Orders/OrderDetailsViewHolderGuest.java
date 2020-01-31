package foi.air.rse.Controllers.guest.Orders;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;

public class OrderDetailsViewHolderGuest extends RecyclerView.ViewHolder {
    TextView dishName, quantity, price, details;


    public OrderDetailsViewHolderGuest(@NonNull View itemView) {
        super(itemView);

        dishName=itemView.findViewById(R.id.txtDishNameInfoGuest);
        quantity=itemView.findViewById(R.id.txtDishQuantityInfoGuest);
        price=itemView.findViewById(R.id.txtPriceInfoGuest);
        details=itemView.findViewById(R.id.txtDishDetailsInfoGuest);
    }
}
