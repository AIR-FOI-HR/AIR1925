package foi.air.rse.Controllers.guest.restaurants.modular;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.NavigationItem;
import com.example.readysteadyeat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import foi.air.rse.Controllers.guest.restaurants.DishsViewHolderGuest;
import com.example.core.OrderSummaryActivity;
import foi.air.rse.Controllers.restaurant.menu.MenuRestaurantFragment;
import com.example.core.Model.Dish;
import com.example.core.Model.Order;
import com.example.core.Model.OrderDetails;

public class RestaurantMenuFragment extends Fragment implements NavigationItem {

    private Spinner spinner;
    private View DishView;
    private RecyclerView dishList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private DatabaseReference databaseReferenceDish;
    private DatabaseReference databaseReferenceCategory;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private String selectedRestaurant;
    private Button btnOrder;
    public List<OrderDetails> orderDetailsList = new ArrayList<>();
    public float price=0;
    public float finalPrice;
    private DatabaseReference databaseReferenceOrder;
    private DatabaseReference databaseReferenceOrderDetails;
    private int numItems=0;
    private String id;
    private ValueEventListener listener;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> spinnerDataList;


    public RestaurantMenuFragment() {
    }

    public static MenuRestaurantFragment newInstance(String param1, String param2) {
        MenuRestaurantFragment fragment = new MenuRestaurantFragment();
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
        dishList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                iterateRcv();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DishView = inflater.inflate(R.layout.fragment_restaurant_menu_guestview, container, false);
        dishList = (RecyclerView) DishView.findViewById(R.id.rcvDishListGuest);
        dishList.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        databaseReferenceDish = FirebaseDatabase.getInstance().getReference().child("Dish");
        databaseReferenceOrder = FirebaseDatabase.getInstance().getReference().child("Order");
        databaseReferenceOrderDetails = FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        return DishView;
    }

    public void populateRecycleView() {
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Dish>()
                        .setQuery(databaseReferenceDish, Dish.class)
                        .build();

        FirebaseRecyclerAdapter<Dish, DishsViewHolderGuest> adapter
                = new FirebaseRecyclerAdapter<Dish, DishsViewHolderGuest>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DishsViewHolderGuest holder, int position, @NonNull Dish model) {
                final String IDs = getRef(position).getKey();
                databaseReferenceDish.child(IDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if (dataSnapshot.child("restaurantId").getValue().equals(id)) {
                                numItems++;

                                if (dataSnapshot.hasChild("imgUrl")) {

                                    String name = dataSnapshot.child("name").getValue().toString();
                                    String category = dataSnapshot.child("category").getValue().toString();
                                    String description = dataSnapshot.child("description").getValue().toString();
                                    String dairyFree = dataSnapshot.child("dairyFree").getValue().toString();
                                    String glutenFree = dataSnapshot.child("glutenFree").getValue().toString();
                                    String price = dataSnapshot.child("price").getValue().toString();
                                    String imgUrl = dataSnapshot.child("imgUrl").getValue().toString();


                                    holder.dishName.setText(name);
                                    holder.dishId = dataSnapshot.getKey();

                                    databaseReferenceCategory = FirebaseDatabase.getInstance().getReference("Category").child(category).child("name");
                                    databaseReferenceCategory.addValueEventListener((new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            holder.dishCategory.setText(dataSnapshot.getValue().toString());
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Activity activity = getActivity();
                                            Toast.makeText(activity, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                                        }
                                    }));

                                    holder.dishDescription.setText(description);
                                    if (dairyFree.equals("true")) {
                                        holder.dairyFree.setText("Dairy free");
                                    }
                                    if (dairyFree.equals("true")) {
                                        holder.glutenFree.setText("Gluten free");
                                    }
                                    holder.price.setText(price + " " + "HRK");
                                    Picasso.get().load(imgUrl).placeholder(R.drawable.common_google_signin_btn_icon_dark).into(holder.dishImage);

                                } else {
                                    String name = dataSnapshot.child("name").getValue().toString();
                                    String category = dataSnapshot.child("category").getValue().toString();
                                    String description = dataSnapshot.child("description").getValue().toString();
                                    String dairyFree = dataSnapshot.child("dairyFree").getValue().toString();
                                    String glutenFree = dataSnapshot.child("glutenFree").getValue().toString();
                                    String price = dataSnapshot.child("price").getValue().toString();

                                    holder.dishName.setText(name);
                                    holder.dishCategory.setText(category);
                                    holder.dishDescription.setText(description);
                                    holder.dairyFree.setText(dairyFree);
                                    holder.glutenFree.setText(glutenFree);
                                    holder.price.setText(price);
                                }

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
            public DishsViewHolderGuest onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_display_layout_guest, viewGroup, false);
                DishsViewHolderGuest viewHolder = new DishsViewHolderGuest(view);
                return  viewHolder;
            }
        };

        dishList.setAdapter(adapter);
        adapter.startListening();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnOrder = view.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterateRcv();
                makeOrder();
            }
        });
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

    @Override
    public void setData(String id) {
        this.id = id;
    }

    public void iterateRcv(){
        boolean contains;

        for (int i = 0; i < numItems; i++) {
            DishsViewHolderGuest holder = (DishsViewHolderGuest) dishList.findViewHolderForAdapterPosition(i);
            if(holder != null){
                if(holder.amountValue>0){
                    contains=false;
                    OrderDetails orderDetail = new OrderDetails(null, holder.dishId, Integer.toString(holder.amountValue));
                    for(int j=0; j<orderDetailsList.size(); j++){
                        if(orderDetailsList.get(j).dishId.equals(orderDetail.dishId)){
                            contains=true;
                        }
                    }
                    if(!contains){
                        orderDetailsList.add(orderDetail);
                    }
                }
            }
        }
    }

    public void makeOrder(){
        FirebaseDatabase.getInstance().getReference().child("Dish")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        price=0;
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

    public void updateOrderDetails(String key){
        for(int i=0; i<orderDetailsList.size(); i++){
            orderDetailsList.get(i).orderId = key;
            databaseReferenceOrderDetails.push().setValue(orderDetailsList.get(i));
        }
    }

    public void openTimeAndPersons(String key){
        Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

}
