package foi.air.rse.Controllers.guest.restaurants;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishsViewHolderGuest extends RecyclerView.ViewHolder {
    public TextView dishName, dishDescription, dishCategory, dairyFree, glutenFree, category, price, amount;
    public CircleImageView dishImage;
    private Button btnAdd, btnRemove;
    private int amountValue;

    public DishsViewHolderGuest(@NonNull View itemView) {
        super(itemView);

        btnAdd = itemView.findViewById(R.id.btnAddDish);
        btnRemove = itemView.findViewById(R.id.btnRemoveDish);

        dishImage = itemView.findViewById(R.id.imgvDishImage);
        dishName = itemView.findViewById(R.id.txtDishName);
        dishDescription = itemView.findViewById(R.id.txtDishDescription);
        dishCategory = itemView.findViewById(R.id.txtDishCategory);
        dairyFree = itemView.findViewById(R.id.txtDairyFree);
        glutenFree = itemView.findViewById(R.id.txtGlutenFree);
        category = itemView.findViewById(R.id.txtDishCategory);
        price = itemView.findViewById(R.id.txtPrice);
        amount = itemView.findViewById(R.id.txtAmount);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountValue= Integer.parseInt(amount.getText().toString());
                amountValue++;
                amount.setText(Integer.toString(amountValue));
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountValue= Integer.parseInt(amount.getText().toString());
                if(amountValue>0){
                    amountValue--;
                    amount.setText(Integer.toString(amountValue));
                }
            }
        });
    }
}
