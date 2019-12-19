package com.example.readysteadyeat.ui.restaurant.menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readysteadyeat.R;
import com.example.readysteadyeat.data.models.restaurant.DishMenuListRestaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View MenuFragmetnView;
    private RecyclerView menuFragmetnList;

    DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();

    private DatabaseReference MenuReference, UserRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    private OnFragmentInteractionListener mListener;



    



    public RestaurantMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragmetn.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantMenuFragment newInstance(String param1, String param2) {
        RestaurantMenuFragment fragment = new RestaurantMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        MenuFragmetnView = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        MenuReference = FirebaseDatabase.getInstance().getReference().child("Dish");

        menuFragmetnList=(RecyclerView) MenuFragmetnView.findViewById(R.id.recyclerview);
        menuFragmetnList.setLayoutManager(new LinearLayoutManager(getContext()));
        UserRef=FirebaseDatabase.getInstance().getReference().child("User");

        // Inflate the layout for this fragment
        return MenuFragmetnView;



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static class BottomMenuActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bottom_menu_restaurant);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<DishMenuListRestaurant>()
                        .setQuery(MenuReference, DishMenuListRestaurant.class).build();

        final FirebaseRecyclerAdapter<DishMenuListRestaurant, RequestViewHolder> adapter =
                new FirebaseRecyclerAdapter<DishMenuListRestaurant, RequestViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final RequestViewHolder holder, int position, @NonNull DishMenuListRestaurant model) {
                        final String list_user_id=getRef(position).getKey();


                        UserRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //String RestaurantId=dataSnapshot.child("restaurantId").getValue(String.class);

                                //DatabaseReference ref = rootRef.child("User").child(RestaurantId);
                                ValueEventListener eventListener= new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String restaurantId=null;
                                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                                              restaurantId= ds.child("userId").getValue(String.class);

                                        }
                                        if(restaurantId==currentUserID){

                                            //DatabaseReference fetch= rootRef.child("Dish").child("restaurantId");

                                            String dishname=dataSnapshot.child("name").getValue().toString();
                                            String dishprice=dataSnapshot.child("price").getValue().toString();
                                            String dishdescription=dataSnapshot.child("description").getValue().toString();

                                            holder.etName.setText(dishname);
                                            holder.etPrice.setText(dishprice);
                                            holder.etDescription.setText(dishdescription);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                };

                            //if(dataSnapshot.getKey().contains(currentUserID)){

                                   // for(int i=0; i>= MenuReference.child("Id").,)
                                   //String dishname=dataSnapshot.child("name").getValue().toString();
                                   //String dishprice=dataSnapshot.child("price").getValue().toString();
                                   //String dishdescription=dataSnapshot.child("description").getValue().toString();
                                   //
                                   //holder.etName.setText(dishname);
                                   //holder.etPrice.setText(dishprice);
                                   //holder.etDescription.setText(dishdescription);
                              // }
                              // else{
                              //     holder.etName.setText("Menu list is empty");
                              //     holder.etPrice.setText("Empty");
                              //     holder.etDescription.setText("Empty");
                              // }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_row, parent, false);
                        RequestViewHolder holder=new RequestViewHolder(view);
                        return holder;
                    }
                };
       //UserRef.child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
       //    @Override
       //    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       //
       //    }
       //
       //    @Override
       //    public void onCancelled(@NonNull DatabaseError databaseError) {
       //
       //    }
       //});
        menuFragmetnList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class RequestViewHolder extends  RecyclerView.ViewHolder{
        EditText etName;
        EditText etPrice;
        EditText etDescription;
        MaterialButton mbEdit;
        MaterialButton mbDelete;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            etName=(EditText) itemView.findViewById(R.id.name_menu_list);
            etPrice=(EditText) itemView.findViewById(R.id.price_menu_list);
            etDescription=(EditText) itemView.findViewById(R.id.items_menu_list);

            mbEdit=(MaterialButton) itemView.findViewById(R.id.btn_edit_menu_list);
            mbDelete=(MaterialButton) itemView.findViewById(R.id.btn_delete_menu_list);
        }
    }
}
