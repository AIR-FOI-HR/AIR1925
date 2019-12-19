package com.example.readysteadyeat.ui.guest.restaurants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.data.models.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantFragmentList extends Fragment {

    private View RestaurantsView;
    private RecyclerView myRestaurantsList;
    private DatabaseReference RestaurantsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;


    public RestaurantFragmentList(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RestaurantsView= inflater.inflate(R.layout.fragment_restaurant_list_view, container, false);


        myRestaurantsList = (RecyclerView) RestaurantsView.findViewById(R.id.restaurants_list);
        myRestaurantsList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        //currentUserID = mAuth.getCurrentUser().getUid();
        RestaurantsRef = FirebaseDatabase.getInstance().getReference().child("User").child("Restaurant");

        return RestaurantsView;
    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Restaurant> options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(RestaurantsRef, Restaurant.class).build();

        FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder> adapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position, @NonNull Restaurant model) {
                String IDs = getRef(position).getKey();
                RestaurantsRef.child(IDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("imgUrl")){
                            String profileImage = dataSnapshot.child("imgUrl").getValue().toString();
                            String restaurantName = dataSnapshot.child("name").getValue().toString();
                            String restaurantStreet = dataSnapshot.child("street").getValue().toString();
                            String restaurantHouseNumber = dataSnapshot.child("houseNumber").getValue().toString();

                            holder.restaurantName.setText(restaurantName);
                            holder.restaurantStreet.setText(restaurantStreet);
                            holder.restaurantHouseNumber.setText(restaurantHouseNumber);
                            Picasso.get().load(profileImage).placeholder(R.drawable.common_google_signin_btn_icon_dark).into(holder.profileImage);

                        }
                        else{
                            String restaurantName = dataSnapshot.child("name").getValue().toString();
                            String restaurantStreet = dataSnapshot.child("street").getValue().toString();
                            String restaurantHouseNumber = dataSnapshot.child("houseNumber").getValue().toString();

                            holder.restaurantName.setText(restaurantName);
                            holder.restaurantStreet.setText(restaurantStreet);
                            holder.restaurantHouseNumber.setText(restaurantHouseNumber);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            }

            @NonNull
            @Override
            public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_display_layout, parent, false);
                RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
                return viewHolder;
            }

        };

        myRestaurantsList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        myRestaurantsList.setAdapter(adapter);


        adapter.startListening();
    }
}

