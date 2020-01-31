package foi.air.rse.Controllers.guest.restaurants.modular;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import foi.air.rse.Controllers.guest.restaurants.DishsViewHolderGuest;
import foi.air.rse.Controllers.restaurant.menu.DishsViewHolder;
import foi.air.rse.Controllers.restaurant.menu.ManuRestaurantEditActivity;
import foi.air.rse.Controllers.restaurant.menu.MenuRestaurantAddActivity;
import foi.air.rse.Controllers.restaurant.menu.MenuRestaurantFragment;
import foi.air.rse.Model.Category;
import foi.air.rse.Model.Dish;

public class RestaurantMenuFragment extends Fragment implements NavigationItem {
    Spinner spinner;
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

    String id;

    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;

    /*public RestaurantMenuFragment(String SelectedRestaurant) {
        selectedRestaurant = SelectedRestaurant;
    }*/

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

                                if (dataSnapshot.hasChild("imgUrl")) {

                                    String name = dataSnapshot.child("name").getValue().toString();
                                    String category = dataSnapshot.child("category").getValue().toString();
                                    String description = dataSnapshot.child("description").getValue().toString();
                                    String dairyFree = dataSnapshot.child("dairyFree").getValue().toString();
                                    String glutenFree = dataSnapshot.child("glutenFree").getValue().toString();
                                    String price = dataSnapshot.child("price").getValue().toString();
                                    String imgUrl = dataSnapshot.child("imgUrl").getValue().toString();


                                    holder.dishName.setText(name);

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
    }

    @Override
    public Button getButton(Context context) {
        return null;
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
        tryToDisplayData();
    }

    private void tryToDisplayData() {
    }
}
