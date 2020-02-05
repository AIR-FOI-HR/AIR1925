package com.example.qrscanning;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.core.Model.Dish;
import com.example.core.Model.Order;
import com.example.core.NavigationItem;
import com.example.core.Model.OrderDetails;
import com.example.core.OrderSummaryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class QrScanningFragment extends Fragment implements NavigationItem {

    String id;
    Button button;
    private String rezultat;
    public List<OrderDetails> orderDetailsList;
    private DatabaseReference databaseReferenceOrderDetails;
    private DatabaseReference databaseReferenceOrder;
    private DatabaseReference databaseReferenceDish;
    private FirebaseAuth firebaseAuth;
    public float price=0;
    public float finalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_scanning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(QrScanningFragment.this);
        integrator.setPrompt("Scan");
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

        //Button button = (Button) getView().findViewById(R.id.btnOrder);
        databaseReferenceOrder = FirebaseDatabase.getInstance().getReference().child("Order");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReferenceOrderDetails = FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        databaseReferenceDish = FirebaseDatabase.getInstance().getReference().child("Dish");
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        orderDetailsList= new ArrayList<>();
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                if (scanningResult.getContents() != null) {
                    rezultat = scanningResult.getContents().toString();
                }
                String[] details = rezultat.split(";");
                for(int i=0; i<details.length; i=i+2){
                    OrderDetails orderDetail = new OrderDetails(null, details[i], details[i+1]);
                    orderDetailsList.add(orderDetail);
                }
                makeOrder();
                getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Nothing scanned", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getName(Context context) {
        return "Qr ordering";
    }

    @Override
    public Drawable getIcon(Context context) {
        return context.getDrawable(android.R.drawable.ic_menu_agenda);
    }

    @Override
    public void setData(String id) {
        this.id = id;
    }

    @Override
    public void iterateRcv() {

    }

    @Override
    public void makeOrder() {
        price=0;
        FirebaseDatabase.getInstance().getReference().child("Dish")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //price=0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Dish dish = snapshot.getValue(Dish.class);
                            for(int i=0; i<orderDetailsList.size(); i++){
                                String id = orderDetailsList.get(i).dishId;
                                int quant = Integer.parseInt(orderDetailsList.get(i).quantity);
                                if(snapshot.getKey().equals(id)){
                                    price = price + quant * Float.parseFloat(dish.price);
                                }
                            }
                        }
                        finalPrice=price;
                        String key = databaseReferenceOrder.push().getKey();
                        Order order = new Order(null, null, Float.toString(finalPrice), "0", "0", id, firebaseAuth.getCurrentUser().getUid());
                        order.setKey(key);
                        databaseReferenceOrder.child(key).setValue(order);
                        updateOrderDetails(key);
                        openTimeAndPersons(key);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void updateOrderDetails(String key) {
        for(int i=0; i<orderDetailsList.size(); i++){
            orderDetailsList.get(i).orderId = key;
            databaseReferenceOrderDetails.push().setValue(orderDetailsList.get(i));
        }
    }

    @Override
    public void openTimeAndPersons(String key) {
        Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
        intent.putExtra("key", key);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

}
