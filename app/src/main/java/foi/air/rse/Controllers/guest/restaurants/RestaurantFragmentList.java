package foi.air.rse.Controllers.guest.restaurants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;

import foi.air.rse.Controllers.guest.restaurants.modular.RestaurantProfileActivity;
import foi.air.rse.Model.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

public class RestaurantFragmentList extends Fragment {

    private View RestaurantsView;
    private RecyclerView myRestaurantsList;
    private DatabaseReference RestaurantsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private SearchView searchRestaurants;


    private double rating=0;
    private double brojac=0;
    private DatabaseReference databaseReferenceOrder;

    public RestaurantFragmentList(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RestaurantsView= inflater.inflate(R.layout.fragment_restaurant_list_view, container, false);
        searchRestaurants = (SearchView) RestaurantsView.findViewById(R.id.searchRestaurants);


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

        searchRestaurants.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateRecycleView(s);
                return true;
            }
        });

        searchRestaurants.setQuery("", true);
    }

    public void populateRecycleView(final String s){
        FirebaseRecyclerOptions<Restaurant> options = new FirebaseRecyclerOptions.Builder<Restaurant>().setQuery(RestaurantsRef, Restaurant.class).build();

        FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder> adapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position, @NonNull Restaurant model) {
                final String IDs = getRef(position).getKey();
                RestaurantsRef.child(IDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if(s.equals("")){
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
                        else{
                            if(dataSnapshot.child("name").getValue().toString().contains(s)){
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
                            else{
                                //final RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(0,0);
                                //holder.itemView.setLayoutParams(params);
                                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                                params.height = 0;
                                holder.itemView.setLayoutParams(params);
                                holder.itemView.setVisibility(View.GONE);
                            }
                        }
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent profileIntent = new Intent(getContext(), RestaurantProfileActivity.class);
                                profileIntent.putExtra("restaurant_id", IDs);
                                profileIntent.putExtra("restaurant_name", dataSnapshot.child("name").getValue().toString());
                                profileIntent.putExtra("restaurant_street", dataSnapshot.child("street").getValue().toString());
                                profileIntent.putExtra("restaurant_houseNumber", dataSnapshot.child("houseNumber").getValue().toString());
                                profileIntent.putExtra("restaurant_city", dataSnapshot.child("city").getValue().toString());
                                profileIntent.putExtra("restaurant_state", dataSnapshot.child("state").getValue().toString());
                                profileIntent.putExtra("restaurant_email", dataSnapshot.child("email").getValue().toString());
                                if(dataSnapshot.hasChild("imgUrl")){
                                    profileIntent.putExtra("restaurant_imgUrl", dataSnapshot.child("imgUrl").getValue().toString());
                                }
                                else{
                                    profileIntent.putExtra("restaurant_imgUrl", "");
                                }

                                startActivity(profileIntent);
                            }
                        });
                        databaseReferenceOrder=FirebaseDatabase.getInstance().getReference("Order").child("");
                        databaseReferenceOrder.addValueEventListener((new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists() ){
                                    brojac=0;
                                    rating=0;
                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                        String id=postSnapshot.getKey();
                                        if(dataSnapshot.child(id).child("restaurantId").getValue().toString().equals(IDs)){
                                            String ratingOrder=dataSnapshot.child(id).child("raiting").getValue().toString();
                                            if(!ratingOrder.equals("0")){
                                                rating=rating+Double.parseDouble(ratingOrder);
                                                brojac=brojac+1;
                                            }
                                        }
                                    }
                                    if(brojac==0){
                                        holder.ratingRestaurant.setVisibility(View.GONE);
                                    }else {
                                        float finalRate = (float)(rating / brojac);
                                        finalRate = BigDecimal.valueOf(finalRate).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                                        holder.ratingRestaurant.setVisibility(View.VISIBLE);
                                        holder.ratingRestaurant.setText(String.valueOf(finalRate));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Activity activity = getActivity();
                                Toast.makeText(activity, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                            }
                        }));
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

