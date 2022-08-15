package ma.gi.pfa;

import static android.widget.Toast.LENGTH_SHORT;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragement_Ajout_Sujet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragement_Ajout_Sujet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db;
    EditText t1,t2,t3,t4;
    Button btn;

    public Fragement_Ajout_Sujet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragement_Ajout_Sujet.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragement_Ajout_Sujet newInstance(String param1, String param2) {
        Fragement_Ajout_Sujet fragment = new Fragement_Ajout_Sujet();
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
        View view= inflater.inflate(R.layout.fragment_fragement__ajout__sujet, container, false);
        //recuperer les infos
        btn = view.findViewById(R.id.btn);
        t1=view.findViewById(R.id.titre);
        t2=view.findViewById(R.id.encadrant);
        t3=view.findViewById(R.id.NBE);
        t4=view.findViewById(R.id.desc);

        //add to database
        db = FirebaseFirestore.getInstance();
        EventAddListener();
        return view;
    }

    public void EventAddListener()
    {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = UUID.randomUUID().toString();

                String titre=t1.getText().toString();
                String encadrant=t2.getText().toString();
                String NBE=t3.getText().toString();
                String description=t4.getText().toString();

                Map<String, String> themeMap= new HashMap<>();
                themeMap.put("id", id);
                themeMap.put("title",titre);
                themeMap.put("encadrant",encadrant);
                themeMap.put("NBE",NBE);
                themeMap.put("description",description);


                db.collection("Sujets").document(id).set(themeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Theme ajouté", LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error= e.getMessage();
                        Toast.makeText(getActivity(), "Error" +error, LENGTH_SHORT).show();

                    }
                });




            }
        });

    }
}