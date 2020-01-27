package com.example.readysteadyeat.ui.restaurant.orders;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.data.models.Order;
import com.example.readysteadyeat.data.models.OrderDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.getIntent;

public class OrdersRestaurantInfoFragment extends Fragment {


    private View OrderDetailsView;
    private RecyclerView orderDetailsList;

    private DatabaseReference databaseReferenceDish;
    private DatabaseReference databaseReferenceOrderDetails;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;

    private String orderID;
    private String userId;
    private String  phoneNumber;
    private  String userName;
    private String dateTime;
    private String numberPersons;
    private String price;
    private String status;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrdersRestaurantInfoFragment() {
        // Required empty public constructor
    }

    public static OrdersRestaurantInfoFragment newInstance(String param1, String param2) {
        OrdersRestaurantInfoFragment fragment = new OrdersRestaurantInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        populateItems();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OrderDetailsView= inflater.inflate(R.layout.fragment_orders_restaurant_info, container, false);
        orderDetailsList=(RecyclerView) OrderDetailsView.findViewById(R.id.rcvOrderDetails);
        orderDetailsList.setLayoutManager((new LinearLayoutManager(getContext())));
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReferenceOrderDetails= FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        databaseReferenceDish=FirebaseDatabase.getInstance().getReference().child("Dish");

        orderID=getActivity().getIntent().getExtras().get("order_id").toString();
        phoneNumber=getActivity().getIntent().getExtras().get("user_phone").toString();
        userName=getActivity().getIntent().getExtras().get("user_name").toString();
        dateTime=getActivity().getIntent().getExtras().get("dateTime").toString();
        numberPersons=getActivity().getIntent().getExtras().get("persons").toString();
        price=getActivity().getIntent().getExtras().get("price").toString();
        status=getActivity().getIntent().getExtras().get("status").toString();


        TextView phone_Number=(TextView)getView().findViewById(R.id.phone_number);
        TextView user_Name=(TextView)getView().findViewById(R.id.user_order);
        TextView date_Time=(TextView)getView().findViewById(R.id.order_datetime);
        TextView number_Persons=(TextView)getView().findViewById(R.id.number_of_persons);
        TextView price_order=(TextView)getView().findViewById(R.id.order_price);
        LinearLayout status_order=(LinearLayout) getView().findViewById(R.id.status_bullet);

        phone_Number.setText(phoneNumber);
        user_Name.setText(userName);
        date_Time.setText(dateTime);
        number_Persons.setText(numberPersons);
        price_order.setText(price);
        if(status.equals("0")){
            status_order.getBackground().setTint(getResources().getColor(R.color.yellow));
        }else{
            if(status.equals("1")){

                status_order.getBackground().setTint(getResources().getColor(R.color.apple_green));
            }else{

                status_order.getBackground().setTint(Color.RED);
            }
        }
        return OrderDetailsView;
    }



    public void populateItems(){










        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<OrderDetails>()
                .setQuery(databaseReferenceOrderDetails, OrderDetails.class)
                .build();
        FirebaseRecyclerAdapter<OrderDetails, OrderDetailsViewHolder> adapter=
                new FirebaseRecyclerAdapter<OrderDetails, OrderDetailsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final OrderDetailsViewHolder holder, int position, @NonNull OrderDetails model) {
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
                                                    holder.price.setText(String.valueOf(finalPrice));

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                    }));
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
                    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_display_layout, parent, false);
                        OrderDetailsViewHolder viewHolder = new OrderDetailsViewHolder(view);
                        return viewHolder;
                    }
                };
        orderDetailsList.setAdapter(adapter);
        adapter.startListening();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
