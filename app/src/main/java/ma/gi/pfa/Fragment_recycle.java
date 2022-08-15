package ma.gi.pfa;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_recycle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_recycle extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<Theme> themeArrayList;
    Adapter_Recycler_Theme myAdapter;
    FirebaseFirestore db;

    public Fragment_recycle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_recycle.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_recycle newInstance(String param1, String param2) {
        Fragment_recycle fragment = new Fragment_recycle();
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
        View view= inflater.inflate(R.layout.fragment_recycle, container, false);
        recyclerView= view.findViewById(R.id.recycle);
        //database
        db = FirebaseFirestore.getInstance();
        themeArrayList = new ArrayList<Theme>();
        myAdapter = new Adapter_Recycler_Theme(getContext(),themeArrayList);
        recyclerView.setAdapter(myAdapter);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //mouvement update

        ItemTouchHelper touchHelper= new ItemTouchHelper(new TouchHelper(myAdapter));
        touchHelper.attachToRecyclerView(recyclerView);



        // a definir
        EventChangeListener();



        return view;
    }



    public void EventChangeListener()
    {



        db.collection("Sujets").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        themeArrayList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){

                            Theme theme = new Theme(snapshot.getString("id") , snapshot.getString("title") , snapshot.getString("description"), snapshot.getString("NBE"),snapshot.getString("encadrant"));
                            themeArrayList.add(theme);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}