package foi.air.rse.Controllers.guest.restaurants;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    TextView restaurantName, restaurantAdress, ratingRestaurant;
    CircleImageView profileImage;
    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        restaurantName = itemView.findViewById(R.id.restaurant_name);
        restaurantAdress = itemView.findViewById(R.id.restaurant_adress);
        profileImage = itemView.findViewById(R.id.restaurant_image);
        ratingRestaurant=itemView.findViewById(R.id.restaurant_rating);
    }
}

