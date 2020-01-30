package foi.air.rse.Controllers.restaurant.menu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishsViewHolder extends RecyclerView.ViewHolder {

    public TextView dishName, dishDescription, dishCategory, dairyFree, glutenFree, category, price;
    public CircleImageView dishImage;

    public DishsViewHolder(@NonNull View itemView) {

        super(itemView);

        dishImage = itemView.findViewById(R.id.imgvDishImage);
        dishName = itemView.findViewById(R.id.txtDishName);
        dishDescription = itemView.findViewById(R.id.txtDishDescription);
        dishCategory = itemView.findViewById(R.id.txtDishCategory);
        dairyFree = itemView.findViewById(R.id.txtDairyFree);
        glutenFree = itemView.findViewById(R.id.txtGlutenFree);
        category = itemView.findViewById(R.id.txtDishCategory);
        price = itemView.findViewById(R.id.txtPrice);
    }
}
