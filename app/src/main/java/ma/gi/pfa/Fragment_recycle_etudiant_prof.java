package ma.gi.pfa;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_recycle_etudiant_prof#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_recycle_etudiant_prof extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth firebaseAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<Theme> themeArrayList;
    Adapter_Recycler_Theme_etudiant_prof myAdapter;
    FirebaseFirestore db;

    Button btnselect ;





    public Fragment_recycle_etudiant_prof() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_recycle_etudiant_prof.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_recycle_etudiant_prof newInstance(String param1, String param2) {
        Fragment_recycle_etudiant_prof fragment = new Fragment_recycle_etudiant_prof();
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
        View view= inflater.inflate(R.layout.fragment_recycle_etudiant_prof, container, false);

        //**** btn selection
        btnselect= view.findViewById(R.id.select);


        recyclerView= view.findViewById(R.id.recycle_etudiant_prof);
        //database
        db = FirebaseFirestore.getInstance();
        themeArrayList = new ArrayList<Theme>();
        myAdapter = new Adapter_Recycler_Theme_etudiant_prof(getContext(),themeArrayList);
        recyclerView.setAdapter(myAdapter);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EventChangeListener();  // a definir

        /*****handling click event button select*/
        firebaseAuth = FirebaseAuth.getInstance();
        String emailCurrent = firebaseAuth.getCurrentUser().getEmail();
        final String[] nomCurrent = new String[1];
        final String[] idEncadrant = new String[1];

        db.collection("/etudiants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String emailEtudiant = document.getString("emailEtudiant");

                            if (emailEtudiant.equals(emailCurrent)) {
                                nomCurrent[0] = document.getString("nomEtudiant");
                                break;
                            }
                        }
                    }
                });
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(myAdapter.getSelected().size()>0)
                {
                    for(int i=0;i<myAdapter.getSelected().size() ; i++)
                    {
                        Map<String, String> themeMap= new HashMap<>();
                        themeMap.put("Titre Sujet", myAdapter.getSelected().get(i).getTitle());
                        themeMap.put("Email Etudiant",emailCurrent);
                        themeMap.put("Nom Etudiant", nomCurrent[0]);
                        String nomEncadrant = myAdapter.getSelected().get(i).getEncadrant();

                        db.collection("/Professeurs")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            String nomProfesseur = document.getString("nomProfesseur");
                                            if (nomProfesseur.equals(nomEncadrant)) {
                                                db.collection("/Professeurs").document(document.getId()).collection("demandes").document().set((themeMap));
                                                break;
                                            }
                                        }
                                    }
                                });

                    }
                }
                else
                {
                    ShowToast("No selection !!!");
                }

            }
        });


        return view;

    }






    private void EventChangeListener()
    {

          /*traitement selection*/

        themeArrayList = new ArrayList<>();

        for(int i=0;i<20 ; i++)
        {
          Theme theme = new Theme();
          theme.setTitle("Title: " +(i+1));

          //Showing at least one selection
            if(i==0)
            {
                theme.setChecked(true);
            }
            themeArrayList.add(theme);
        }

        myAdapter.setThemeArrayList(themeArrayList);


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

       /*definir tost*/
    private void ShowToast(String msg)
    {
       Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}