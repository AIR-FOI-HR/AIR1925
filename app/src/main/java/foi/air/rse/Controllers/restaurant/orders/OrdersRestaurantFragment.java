package foi.air.rse.Controllers.restaurant.orders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.Model.Order;
import com.example.readysteadyeat.R;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class OrdersRestaurantFragment extends Fragment {

    private View OrdersView;
    private RecyclerView orderList;

    private DatabaseReference databaseReferenceGuest;
    private DatabaseReference databaseReferenceOrders;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;


    private String phone;
    private  String userName;
    private String prikazStanje;
    private String dateTime;

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

        prikazStanje="all";
        dateTime="all";
        final TextView sliderCircle=OrdersView.findViewById(R.id.sliderCircleRestaurant);
        final LinearLayout slider=OrdersView.findViewById(R.id.sliderButtonRestaurant);
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prikazStanje.equals("all")){
                    setMargins(sliderCircle,20 );
                    prikazStanje="0";
                }
                else if(prikazStanje.equals("0")){
                    setMargins(sliderCircle,40 );
                    prikazStanje="1";
                }else if(prikazStanje.equals("1")){
                    setMargins(sliderCircle,60 );
                    prikazStanje="3";
                }else{
                    setMargins(sliderCircle,0 );
                    prikazStanje="all";
                }
                populateRecycleView(dateTime, prikazStanje);
            }


        });

        final TextView calendar=OrdersView.findViewById(R.id.calendartxtRestaurant);
        final DatePickerDialog.OnDateSetListener datePickerListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selyear, int month, int seldayOfMonth) {
                String year= String.valueOf(selyear);
                String mon=String.valueOf(month+1);
                String day=String.valueOf(seldayOfMonth);
                dateTime =day+"/"+mon+"/"+year;
                calendar.setText(dateTime);
                OrdersView.findViewById(R.id.btnDltDateRestaurant).setVisibility(View.VISIBLE);
                populateRecycleView(dateTime, prikazStanje);
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
        OrdersView.findViewById(R.id.btnDltDateRestaurant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersView.findViewById(R.id.btnDltDateRestaurant).setVisibility(View.GONE);
                dateTime="all";
                calendar.setText("Pick date");
                populateRecycleView(dateTime, prikazStanje);
            }
        });


        populateRecycleView(dateTime,prikazStanje);
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
        OrdersView.findViewById(R.id.btnDltDateRestaurant).setVisibility(View.GONE);

        return OrdersView;
    }

    public void populateRecycleView(final String dateTime, final String prikazStanje){






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

                                final OrdersRestaurantInfoFragment fragment=new OrdersRestaurantInfoFragment();

                                final Bundle bundle=new Bundle();
                                String declined="2";

                                if(dataSnapshot.exists()){
                                    if(dataSnapshot.child("restaurantId").getValue().equals(firebaseAuth.getCurrentUser().getUid())
                                            && !dataSnapshot.child("status").getValue().toString().equals(declined)
                                            && dataSnapshot.hasChild("dateTime")
                                            && dataSnapshot.hasChild("status")
                                            && dataSnapshot.hasChild("time")
                                            && dataSnapshot.hasChild("price")
                                            && dataSnapshot.hasChild("persons")
                                            && dataSnapshot.hasChild("raiting")
                                            && dataSnapshot.hasChild("guestId")
                                            &&(dateTime.equals("all")|| dataSnapshot.child("dateTime").getValue().toString().equals(dateTime))
                                            && (prikazStanje.equals("all") || dataSnapshot.child("status").getValue().toString().equals(prikazStanje))
                                    ){
                                        String guestID= dataSnapshot.child("guestId").getValue().toString();
                                        String dateTime1= dataSnapshot.child("dateTime").getValue().toString();
                                        String time=dataSnapshot.child("time").getValue().toString();
                                        String personcount=dataSnapshot.child("persons").getValue().toString();
                                        String price=dataSnapshot.child("price").getValue().toString();
                                        String rating=dataSnapshot.child("raiting").getValue().toString();

                                        String status=dataSnapshot.child("status").getValue().toString();
                                        holder.itemView.setVisibility(View.VISIBLE);

                                        String notDecide="0";
                                        String accept="1";
                                        String rated="3";
                                        if(status.equals(notDecide)){
                                            holder.btnAccept.setVisibility(View.VISIBLE);
                                            holder.btnDenied.setVisibility(View.VISIBLE);
                                            holder.displayLayoutRestaurant.getBackground().setTint(getResources().getColor(R.color.white));

                                            holder.bullet.getBackground().setTint(getResources().getColor(R.color.yellow));
                                        }else{
                                            if(status.equals(accept)){
                                                holder.displayLayoutRestaurant.getBackground().setTint(getResources().getColor(R.color.white));
                                                holder.bullet.getBackground().setTint(getResources().getColor(R.color.apple_green));
                                            }else if(status.equals(rated)){
                                                holder.bullet.setBackgroundResource(R.drawable.ic_done_orange);
                                                holder.displayLayoutRestaurant.getBackground().setTint(getResources().getColor(R.color.grayLight));
                                                holder.restaurantRating.setVisibility(View.VISIBLE);
                                                holder.restaurantRating.setText(rating);
                                            }else if(status.equals("2")){
                                                holder.itemView.setVisibility(View.GONE);
                                                holder.itemView.getLayoutParams().height=0;
                                            }
                                            holder.btnAccept.setVisibility(View.GONE);
                                            holder.btnDenied.setVisibility(View.GONE);

                                        }

                                        databaseReferenceGuest=FirebaseDatabase.getInstance().getReference("User").child("Guest").child(guestID);
                                        databaseReferenceGuest.addValueEventListener((new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()){
                                                    String firstName=dataSnapshot.child("firstName").getValue().toString();
                                                    String lastName=dataSnapshot.child("lastName").getValue().toString();
                                                    phone=dataSnapshot.child("phone").getValue().toString();
                                                    userName=firstName+" "+lastName;
                                                    holder.userOrder.setText(firstName+' '+lastName);
                                                    bundle.putString("user_phone", phone);
                                                    bundle.putString("user_name", userName);
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
                                        holder.datetimeOrder.setText(dateTime1+"  "+time);
                                    }
                                    else{
                                        holder.itemView.setVisibility(View.GONE);
                                        holder.itemView.getLayoutParams().height=0;
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
                                            holder.itemView.setVisibility(View.GONE);
                                            holder.itemView.getLayoutParams().height=0;
                                            Toast.makeText(getActivity().getApplicationContext(), "Order is denied!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.btnInfo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            bundle.putString("dateTime", dataSnapshot.child("dateTime").getValue().toString());
                                            bundle.putString("order_id", IDs);
                                            bundle.putString("user_id", dataSnapshot.child("guestId").getValue().toString());
                                            bundle.putString("time", dataSnapshot.child("time").getValue().toString());
                                            bundle.putString("personNumber",dataSnapshot.child("persons").getValue().toString() );
                                            bundle.putString("price",dataSnapshot.child("price").getValue().toString());
                                            bundle.putString("status",dataSnapshot.child("status").getValue().toString());
                                            fragment.setArguments(bundle);

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
                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public int getItemViewType(int position) {
                        return position;
                    }
                };
        adapter.setHasStableIds(true);
        orderList.setAdapter(adapter);
        adapter.startListening();

    }


    private void updateOrderStatus(final String status, final String orderId){
        databaseReferenceOrders=FirebaseDatabase.getInstance().getReference("Order");
        databaseReferenceOrders.child(orderId).child("status").setValue(status);
        Toast.makeText(getActivity().getApplicationContext(), "Status is set to: "+status, Toast.LENGTH_SHORT).show();

    }

    public void notifyThis(String title, String message) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this.getContext());
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.foodicon)
                .setTicker("{your tiny message}")
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("INFO");

        NotificationManager nm = (NotificationManager) this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, b.build());
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
