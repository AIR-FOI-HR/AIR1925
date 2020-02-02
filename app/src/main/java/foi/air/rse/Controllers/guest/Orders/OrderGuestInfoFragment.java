package foi.air.rse.Controllers.guest.Orders;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.readysteadyeat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import foi.air.rse.Controllers.restaurant.orders.OrderDetailsViewHolder;
import foi.air.rse.Model.OrderDetails;

public class OrderGuestInfoFragment extends Fragment {

    private View OrderDetailsView;
    private View BottomActivity;
    private RecyclerView orderDetailsList;

    private DatabaseReference databaseReferenceDish;
    private DatabaseReference databaseReferenceOrderDetails;
    private FirebaseAuth firebaseAuth;

    private String orderID;
    private String address;
    private String restaurantName;
    private String dateTime;
    private String numberPersons;
    private String price;
    private String status;



    public OrderGuestInfoFragment() {
    }


    @Override
    public void onStart() {
        super.onStart();

        populateItems();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OrderDetailsView= inflater.inflate(R.layout.fragment_order_guest_info, container, false);
        orderDetailsList=(RecyclerView) OrderDetailsView.findViewById(R.id.rcvOrderDetailsInfoGuest);
        orderDetailsList.setLayoutManager((new LinearLayoutManager(getContext())));
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReferenceOrderDetails= FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        databaseReferenceDish=FirebaseDatabase.getInstance().getReference().child("Dish");

        orderID=this.getArguments().getString("order_id");
        restaurantName=this.getArguments().getString("restaurant_name");
        address=this.getArguments().getString("restaurant_address");
        dateTime=this.getArguments().getString("dateTime");
        numberPersons=this.getArguments().getString("personNumber");
        price=this.getArguments().getString("price");
        status=this.getArguments().getString("status");

        LinearLayout status_orderL=(LinearLayout) OrderDetailsView.findViewById(R.id.status_bulletInfoGuest);
        TextView nameRestaurantL=(TextView)OrderDetailsView.findViewById(R.id.restaurant_name_orderInfoGuest);
        TextView addressL=(TextView)OrderDetailsView.findViewById(R.id.restaurant_adressInfoGuest);
        TextView date_TimeL=(TextView)OrderDetailsView.findViewById(R.id.order_datetimeInfoGuest);
        TextView number_PersonsL=(TextView)OrderDetailsView.findViewById(R.id.number_of_personsInfoGuest);
        TextView price_orderL=(TextView)OrderDetailsView.findViewById(R.id.order_priceInfoGuest);

        nameRestaurantL.setText(restaurantName);
        addressL.setText(address);
        date_TimeL.setText(dateTime);
        number_PersonsL.setText(numberPersons);
        price_orderL.setText(price);
        if(status.equals("0")){
            status_orderL.getBackground().setTint(getResources().getColor(R.color.yellow));
        }else{
            if(status.equals("1")){

                status_orderL.getBackground().setTint(getResources().getColor(R.color.apple_green));
            }else{

                status_orderL.getBackground().setTint(Color.RED);
            }
        }
        return OrderDetailsView;
    }




    public void populateItems(){
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<OrderDetails>()
                        .setQuery(databaseReferenceOrderDetails, OrderDetails.class)
                        .build();
        FirebaseRecyclerAdapter<OrderDetails, OrderDetailsViewHolderGuest> adapter=
                new FirebaseRecyclerAdapter<OrderDetails, OrderDetailsViewHolderGuest>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final OrderDetailsViewHolderGuest holder, int position, @NonNull OrderDetails model) {
                        final String IDs=getRef(position).getKey();
                        databaseReferenceOrderDetails.child(IDs).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    if(dataSnapshot.child("orderId").getValue().toString().equals(orderID)){
                                        String dishId=dataSnapshot.child("dishId").getValue().toString();
                                        final String quantity=dataSnapshot.child("quantity").getValue().toString();
                                        holder.quantity.setText(quantity);
                                        databaseReferenceDish=FirebaseDatabase.getInstance().getReference("Dish").child(dishId);
                                        databaseReferenceDish.addValueEventListener((new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()){
                                                    String name=dataSnapshot.child("name").getValue().toString();
                                                    String price=dataSnapshot.child("price").getValue().toString();
                                                    holder.dishName.setText(name);
                                                    int finalPrice=Integer.parseInt(price)*Integer.parseInt(quantity);
                                                    holder.price.setText(String.valueOf(finalPrice)+" HRK");
                                                    holder.details.setText(dataSnapshot.child("description").getValue().toString());

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        }));
                                    }
                                    else{
                                        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                                        params.height = 0;
                                        holder.itemView.setLayoutParams(params);
                                        holder.itemView.setVisibility(View.GONE);
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public OrderDetailsViewHolderGuest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_guest_display_layout, parent, false);
                        OrderDetailsViewHolderGuest viewHolder = new OrderDetailsViewHolderGuest(view);
                        return viewHolder;
                    }
                };
        orderDetailsList.setAdapter(adapter);
        adapter.startListening();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }






}
