package foi.air.rse.Controllers.guest.restaurants;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.core.NavigationItem;
import com.example.readysteadyeat.R;

import foi.air.rse.Controllers.guest.BottomMenuGuestActivity;

public class RestaurantDishList extends Fragment implements NavigationItem {

    @Override
    public Button getButton(Context context) {
        int radius = 20;
        int color = Color.parseColor("#FFC400");
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(color);
        Button button = new Button(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 65);
        layoutParams.setMargins(80, 80, 80, 0);


        //button.setLayoutParams(new LinearLayout.LayoutParams(400,65));
        button.setLayoutParams(layoutParams);

        button.setTextSize(15);
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setBackground(gd);
        button.setText("SEE MENU");
        return button;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getName(Context context) {
        return "See menu";
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(android.R.drawable.ic_menu_agenda);
    }

}
