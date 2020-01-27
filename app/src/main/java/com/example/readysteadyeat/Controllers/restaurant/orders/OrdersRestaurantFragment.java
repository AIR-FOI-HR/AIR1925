package com.example.readysteadyeat.Controllers.restaurant.orders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.data.models.Order;
import com.example.readysteadyeat.ui.restaurant.orders.OrdersRestaurantInfoFragment;
import com.example.readysteadyeat.ui.restaurant.orders.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class OrdersRestaurantFragment extends Fragment {

    private View OrdersView;
    private RecyclerView orderList;

    private DatabaseReference databaseReferenceGuest;
    private DatabaseReference databaseReferenceOrders;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;


    private String phone;
    private  String userName;

    ValueEventListener listener;
    ArrayAdapter<String> adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrdersRestaurantFragment() {
    }

    public static OrdersRestaurantFragment newInstance(String param1, String param2) {
        OrdersRestaurantFragment fragment = new OrdersRestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        populateRecycleView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OrdersView= inflater.inflate(R.layout.fragment_orders_restaurant, container, false);
        orderList=(RecyclerView) OrdersView.findViewById(R.id.recyclerOrdersList);
        orderList.setLayoutManager((new LinearLayoutManager(getContext())));
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReferenceOrders= FirebaseDatabase.getInstance().getReference().child("Order");
        return OrdersView;
    }

    public void populateRecycleView(){
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(databaseReferenceOrders, Order.class)
                        .build();

        FirebaseRecyclerAdapter<Order, OrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Order, OrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final OrdersViewHolder holder, int position, @NonNull Order model) {
                        final String IDs= getRef(position).getKey();
                        databaseReferenceOrders.child(IDs).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists()){
                                    if(dataSnapshot.child("restaurantId").getValue().equals(firebaseAuth.getCurrentUser().getUid())){
                                        String guestID= dataSnapshot.child("userId").getValue().toString();
                                        String time= dataSnapshot.child("dateTime").getValue().toString();
                                        String personcount=dataSnapshot.child("persons").getValue().toString();
                                        String price=dataSnapshot.child("price").getValue().toString();

                                        String status=dataSnapshot.child("status").getValue().toString();

                                        String notDecide="0";
                                        String accept="1";
                                        if(status.equals(notDecide)){
                                            holder.btnAccept.setVisibility(View.VISIBLE);
                                            holder.btnDenied.setVisibility(View.VISIBLE);

                                            holder.bullet.getBackground().setTint(getResources().getColor(R.color.yellow));
                                        }else{
                                            if(status.equals(accept)){

                                                holder.bullet.getBackground().setTint(getResources().getColor(R.color.apple_green));
                                            }else{

                                                holder.bullet.getBackground().setTint(Color.RED);
                                            }
                                            holder.btnAccept.setVisibility(View.GONE);
                                            holder.btnDenied.setVisibility(View.GONE);

                                        }

                                        databaseReferenceGuest=FirebaseDatabase.getInstance().getReference("User").child("Guest").child(guestID).child("");
                                        databaseReferenceGuest.addValueEventListener((new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()){
                                                    String firstName=dataSnapshot.child("firstName").getValue().toString();
                                                    String lastName=dataSnapshot.child("lastName").getValue().toString();
                                                    phone=dataSnapshot.child("phone").getValue().toString();
                                                    userName=firstName+lastName;
                                                    holder.userOrder.setText(firstName+' '+lastName);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Activity activity = getActivity();
                                                Toast.makeText(activity, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                                            }
                                        }));
                                        holder.priceOrder.setText(price+" HRK");
                                        holder.numOfPersonsOrder.setText(personcount);
                                        holder.datetimeOrder.setText(time);

                                    }
                                    else{
                                        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                                        params.height = 0;
                                        holder.itemView.setLayoutParams(params);
                                        holder.itemView.setVisibility(View.GONE);
                                    }
                                    holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String status= "1";
                                            updateOrderStatus(status, IDs);
                                            Toast.makeText(getActivity().getApplicationContext(), "Order is accepted!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.btnDenied.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String status= "2";
                                            updateOrderStatus(status, IDs);
                                            Toast.makeText(getActivity().getApplicationContext(), "Order is denied!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.btnInfo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent orderDetailIntent= new Intent(getContext(), OrdersRestaurantInfoFragment.class);
                                            orderDetailIntent.putExtra("order_id", IDs);
                                            orderDetailIntent.putExtra("user_id", dataSnapshot.child("userId").getValue().toString());
                                            orderDetailIntent.putExtra("user_phone", phone);
                                            orderDetailIntent.putExtra("user_name", userName);
                                            orderDetailIntent.putExtra("dateTime", dataSnapshot.child("dateTime").getValue().toString());
                                            orderDetailIntent.putExtra("personNumber",dataSnapshot.child("persons").getValue().toString() );
                                            orderDetailIntent.putExtra("price",dataSnapshot.child("price").getValue().toString());
                                            orderDetailIntent.putExtra("status",dataSnapshot.child("status").getValue().toString());
                                            OrdersRestaurantInfoFragment fragment=new OrdersRestaurantInfoFragment();
                                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.container1, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                    });


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                    }

                    @NonNull
                    @Override
                    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_display_layout, parent, false);
                        OrdersViewHolder viewHolder = new OrdersViewHolder(view);
                        return viewHolder;
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();

    }


    private void updateOrderStatus(final String status, final String orderId){
        databaseReferenceOrders=FirebaseDatabase.getInstance().getReference("Order");
        databaseReferenceOrders.child(orderId).child("status").setValue(status);
        Toast.makeText(getActivity().getApplicationContext(), "Status is set to: "+status, Toast.LENGTH_SHORT).show();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
