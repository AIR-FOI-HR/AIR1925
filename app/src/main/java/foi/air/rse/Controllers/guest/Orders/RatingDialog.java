package foi.air.rse.Controllers.guest.Orders;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.readysteadyeat.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RatingDialog {

    private RatingBar ratingBar;
    private Button buttonSubmit;
    private Button buttonBack;
    private String rate="0";
    public void showDialog(Activity activity, final OrdersGuestFragment ordersGuestFragment, final String IDs){
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);//istraziti
        View inflate=activity.getLayoutInflater().inflate(R.layout.rate_dialog, null);
        dialog.setContentView(inflate);
        ratingBar=(RatingBar) dialog.findViewById(R.id.ratingBar);
        final Bundle bundle=new Bundle();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rate=String.valueOf(rating);

            }
        });
        buttonSubmit = (Button) dialog.findViewById(R.id.button_SubmitRate);
        buttonBack=(Button) dialog.findViewById(R.id.button_BackRate);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rate.equals("0")){
                    updateOrderRate(rate, IDs);
                }
                dialog.dismiss();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate="0";
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void updateOrderRate(final String rate, final String orderId){
        DatabaseReference databaseReferenceOrders = FirebaseDatabase.getInstance().getReference("Order");
        databaseReferenceOrders.child(orderId).child("raiting").setValue(rate);

    }
}
