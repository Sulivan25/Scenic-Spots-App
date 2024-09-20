package com.example.doanthangcanh.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanthangcanh.Adapter.thanhphoAdapter;
import com.example.doanthangcanh.DetailActivity;
import com.example.doanthangcanh.MainActivity;
import com.example.doanthangcanh.R;
import com.example.doanthangcanh.thanhpho;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thanhphoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thanhphoFragment extends Fragment implements thanhphoAdapter.Listener{
    RecyclerView rvthanhpho;
    ArrayList<thanhpho> dsthanhpho;
    thanhphoAdapter ThanhphoAdapter;
   thanhphoAdapter.Listener listener;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public thanhphoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thanhphoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static thanhphoFragment newInstance(String param1, String param2) {
        thanhphoFragment fragment = new thanhphoFragment();
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
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_thanhpho, container, false);
        rvthanhpho = v.findViewById(R.id.rvthanhpho);

//        rvthanhpho.setLayoutManager (new LinearLayoutManager(container.getContext()));
        rvthanhpho.setLayoutManager(new GridLayoutManager(container.getContext(),2));

        dsthanhpho = new ArrayList<thanhpho>();
        ThanhphoAdapter = new thanhphoAdapter(this,dsthanhpho);

        rvthanhpho.setAdapter(ThanhphoAdapter);
        EventChangeListener();
        return v;
    }
    private void EventChangeListener() {
        db.collection("thanhpho")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            Log.e( "Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                dsthanhpho.add(dc.getDocument().toObject (thanhpho.class));
                            }
                            ThanhphoAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener= (thanhphoAdapter.Listener) getActivity();
    }
    @Override
    public void senId(String id) {
        Intent i= new Intent(getActivity(), MainActivity.class);
       i.putExtra("idtp",id);
       startActivity(i);
    }
}