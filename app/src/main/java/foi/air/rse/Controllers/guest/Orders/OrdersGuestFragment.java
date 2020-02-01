package foi.air.rse.Controllers.guest.Orders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.media.Rating;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import foi.air.rse.Controllers.restaurant.orders.OrdersRestaurantInfoFragment;
import foi.air.rse.Controllers.restaurant.orders.OrdersViewHolder;
import foi.air.rse.Model.Order;

public class OrdersGuestFragment extends Fragment {
    private OrdersGuestFragment ordersGuestFragment=this;
    private View OrdersView;
    private RecyclerView orderList;

    private DatabaseReference databaseReferenceRestaurant;
    private DatabaseReference databaseReferenceOrders;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;


    private String prikazStanje;
    private String dateTime;

    private String address;
    private  String restaurantName;

    ValueEventListener listener;
    ArrayAdapter<String> adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public OrdersGuestFragment() {

    }


    @Override
    public void onStart() {
        super.onStart();
        prikazStanje="all";
        dateTime="all";

        final TextView sliderCircle=OrdersView.findViewById(R.id.sliderCircle);
        final LinearLayout slider=OrdersView.findViewById(R.id.sliderButton);
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prikazStanje.equals("all")){
                    setMargins(sliderCircle,20 );
                    prikazStanje="0";
                }
                else if(prikazStanje.equals("0")){
                    setMargins(sliderCircle, 40);
                    prikazStanje="1";
                }else if(prikazStanje.equals("1")){
                    setMargins(sliderCircle, 60);
                    prikazStanje="2";
                }else{
                    setMargins(sliderCircle, 0);
                    prikazStanje="all";
                }
                populateRecyclerView(dateTime, prikazStanje);
            }
        });

        final TextView calendar=OrdersView.findViewById(R.id.calendartxt);
        final DatePickerDialog.OnDateSetListener datePickerListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selyear, int month, int seldayOfMonth) {
                String year= String.valueOf(selyear);
                String mon=String.valueOf(month+1);
                String day=String.valueOf(seldayOfMonth);
                dateTime =day+"/"+mon+"/"+year;
                calendar.setText(dateTime);
                OrdersView.findViewById(R.id.btnDltDate).setVisibility(View.VISIBLE);
                populateRecyclerView(dateTime, prikazStanje);
            }
        };
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr=Calendar.getInstance();
                int day=cldr.get(Calendar.DAY_OF_MONTH);
                int month=cldr.get(Calendar.MONTH);
                int year=cldr.get(Calendar.YEAR);
                DatePickerDialog picker=new DatePickerDialog(getActivity(), datePickerListener,
                        year, month, day);
                picker.setCancelable(false);
                picker.show();

            }
        });
        OrdersView.findViewById(R.id.btnDltDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersView.findViewById(R.id.btnDltDate).setVisibility(View.GONE);
                dateTime="all";
                calendar.setText("Pick date");
                populateRecyclerView(dateTime, prikazStanje);
            }
        });

        populateRecyclerView(dateTime, prikazStanje);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = new Bundle();
        args.putString("rating", "0");
        ordersGuestFragment.setArguments(args);
        OrdersView= inflater.inflate(R.layout.fragment_orders_guest, container, false);
        orderList=(RecyclerView) OrdersView.findViewById(R.id.recyclerOrdersListGuest);
        orderList.setLayoutManager((new LinearLayoutManager(getContext())));
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReferenceOrders= FirebaseDatabase.getInstance().getReference().child("Order");
        OrdersView.findViewById(R.id.btnDltDate).setVisibility(View.GONE);
        return OrdersView;
    }


    public void populateRecyclerView(final String dateTime, final String pokStatus){



        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(databaseReferenceOrders, Order.class)
                        .build();

        FirebaseRecyclerAdapter<Order, OrdersViewHolderGuest> adapter =
                new FirebaseRecyclerAdapter<Order, OrdersViewHolderGuest>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final OrdersViewHolderGuest holder, int position, @NonNull Order model) {
                        final String IDs= getRef(position).getKey();
                        databaseReferenceOrders.child(IDs).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                final OrderGuestInfoFragment fragment=new OrderGuestInfoFragment();

                                final Bundle bundle=new Bundle();
                                if(dataSnapshot.exists()){
                                    if(dataSnapshot.child("guestId").getValue().equals(firebaseAuth.getCurrentUser().getUid())
                                            &&( dataSnapshot.child("dateTime").getValue().toString().equals(dateTime)
                                            || dateTime.equals("all"))
                                            &&(dataSnapshot.child("status").getValue().toString().equals(pokStatus)
                                            || pokStatus.equals("all"))){

                                        String rating=dataSnapshot.child("raiting").getValue().toString();
                                        Log.i("rate", rating);
                                        if(!rating.equals("0")){
                                            holder.btnRateGuest.setVisibility(View.GONE);
                                        }
                                        String restaurantID= dataSnapshot.child("restaurantId").getValue().toString();
                                        String time= dataSnapshot.child("dateTime").getValue().toString();
                                        String personcount=dataSnapshot.child("persons").getValue().toString();
                                        String price=dataSnapshot.child("price").getValue().toString();

                                        String status=dataSnapshot.child("status").getValue().toString();
                                        String notDecide="0";
                                        String accept="1";
                                        String payed="3";
                                        if(status.equals(notDecide)){
                                            holder.btnRateGuest.setVisibility(View.GONE);
                                            holder.btnDeniedGuest.setVisibility(View.VISIBLE);
                                            holder.displayOrderGuest.getBackground().setTint(getResources().getColor(R.color.white));
                                            holder.btnPayOrder.setVisibility(View.GONE);
                                            holder.bulletGuest.getBackground().setTint(getResources().getColor(R.color.yellow));
                                        }else if(status.equals(payed)){
                                            holder.bulletGuest.setBackgroundResource(R.drawable.ic_done_orange);
                                            holder.displayOrderGuest.getBackground().setTint(getResources().getColor(R.color.white));

                                            holder.btnPayOrder.setVisibility(View.GONE);
                                            holder.btnDeniedGuest.setVisibility(View.GONE);
                                            if(!dataSnapshot.child("raiting").getValue().toString().equals(notDecide)){
                                                holder.btnRateGuest.setVisibility(View.GONE);
                                            }else{
                                                holder.btnRateGuest.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        else{
                                            if(status.equals(accept)){
                                                holder.btnPayOrder.setVisibility(View.VISIBLE);
                                                holder.displayOrderGuest.getBackground().setTint(getResources().getColor(R.color.white));
                                                holder.bulletGuest.getBackground().setTint(getResources().getColor(R.color.apple_green));
                                            }else{
                                                holder.displayOrderGuest.getBackground().setTint(getResources().getColor(R.color.grayLight));
                                                holder.btnPayOrder.setVisibility(View.GONE);
                                                holder.bulletGuest.getBackground().setTint(Color.RED);
                                            }
                                            holder.btnDeniedGuest.setVisibility(View.GONE);
                                            holder.btnRateGuest.setVisibility(View.GONE);

                                        }

                                        databaseReferenceRestaurant=FirebaseDatabase.getInstance().getReference("User").child("Restaurant").child(restaurantID).child("");
                                        databaseReferenceRestaurant.addValueEventListener((new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()){
                                                    String street=dataSnapshot.child("street").getValue().toString();
                                                    String houseNumber=dataSnapshot.child("houseNumber").getValue().toString();
                                                    String city=dataSnapshot.child("city").getValue().toString();
                                                    String state=dataSnapshot.child("state").getValue().toString();
                                                    address=houseNumber+", "+street+", "+city+", "+state;
                                                    restaurantName=dataSnapshot.child("name").getValue().toString();
                                                    holder.restaurantNameOrderGuest.setText(restaurantName);
                                                    holder.adressOrderGuest.setText(address);

                                                    bundle.putString("restaurant_name", restaurantName);
                                                    bundle.putString("restaurant_address", address);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Activity activity = getActivity();
                                                Toast.makeText(activity, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                                            }
                                        }));
                                        holder.priceOrderGuest.setText(price+" HRK");
                                        holder.numOfPersonsOrderGuest.setText(personcount);
                                        holder.dateTimeOrderGuest.setText(time);

                                    }
                                    else{
                                        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                                        params.height = 0;
                                        holder.itemView.setLayoutParams(params);
                                        holder.itemView.setVisibility(View.GONE);
                                    }
                                    holder.btnPayOrder.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String status= "3";
                                            updateOrderStatus(status, IDs);
                                            Toast.makeText(getActivity().getApplicationContext(), "Order is accepted!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.btnDeniedGuest.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String status= "2";
                                            updateOrderStatus(status, IDs);
                                            Toast.makeText(getActivity().getApplicationContext(), "Order is denied!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.btnInfoGuest.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bundle.putString("order_id", IDs);
                                            bundle.putString("dateTime", dataSnapshot.child("dateTime").getValue().toString());
                                            bundle.putString("personNumber",dataSnapshot.child("persons").getValue().toString() );
                                            bundle.putString("price",dataSnapshot.child("price").getValue().toString());
                                            bundle.putString("status",dataSnapshot.child("status").getValue().toString());
                                            bundle.putString("guest_id", dataSnapshot.child("guestId").getValue().toString());
                                            bundle.putString("restaurant_id", dataSnapshot.child("restaurantId").getValue().toString());
                                            fragment.setArguments(bundle);

                                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.container, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }
                                    });


                                    holder.btnRateGuest.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RatingDialog ratingDialog = new RatingDialog();
                                            ratingDialog.showDialog(getActivity(), ordersGuestFragment, IDs);
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
                    public OrdersViewHolderGuest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_guest_display_layout, parent, false);
                        OrdersViewHolderGuest viewHolder = new OrdersViewHolderGuest(view);
                        return viewHolder;
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }



    private void updateOrderStatus(final String status, final String orderId){
        databaseReferenceOrders=FirebaseDatabase.getInstance().getReference("Order");
        databaseReferenceOrders.child(orderId).child("status").setValue(status);

    }
    private void setMargins (View view, int left) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            int leftFinal= (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, left, getResources()
                            .getDisplayMetrics());
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(leftFinal, 0, 0, 0);
            view.requestLayout();
        }
    }


}
