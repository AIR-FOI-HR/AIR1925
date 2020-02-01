package foi.air.rse.Controllers.guest.restaurants;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import foi.air.rse.Model.Category;
import foi.air.rse.Model.Dish;
import foi.air.rse.Model.Order;
import foi.air.rse.Model.OrderDetails;


public class OrderSummaryActivity extends AppCompatActivity {
    public List<OrderDetails> orderDetailsList = new ArrayList<>();
    public List<String> popisCijena;
    public DatabaseReference databaseReferenceDish;
    public Order order;
    public float price=0;

    public OrderSummaryActivity(){

    }

    // kreirati narudzbu - id usera, id restorana, price
        //proletimo kroz listu detalja narudzbe i ažuriramo null vrijednot narudžbe
        //ponovno proletimo i redom spremamo detalj po detalj u firebase
    //prebacimo se na ekran određivanje termina - id narudzbe
    //na tom ekranu samo ažuriramo kreiranu narzudžbu

    // LOOP kroz tu listu - povuci cijene u listu cijena


    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceDish = FirebaseDatabase.getInstance().getReference().child("Dish");
        for(int i =0; i< orderDetailsList.size(); i++){

        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderDetailsList = getIntent().getParcelableArrayListExtra("orderList");
        //databaseReferenceCategory = FirebaseDatabase.getInstance().getReference("Order");

    }


}
