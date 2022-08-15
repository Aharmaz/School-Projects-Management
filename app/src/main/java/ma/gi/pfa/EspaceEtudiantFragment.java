package ma.gi.pfa;


import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ma.gi.pfa.R;


public class EspaceEtudiantFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_espace_etudiant, container, false);

        Button btn1 = (Button) rootView.findViewById(R.id.sujets_prop_etudiant_btn);
        Button btn2 = (Button) rootView.findViewById(R.id.suivi_etudiant_btn);

        Button btn4 = (Button) rootView.findViewById(R.id.choisir_sujets_etudiant_btn);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        btn4.setOnClickListener(this);



        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.sujets_prop_etudiant_btn:
                fragment = new Fragment_recycle_etudiant_prof();
                replaceFragment(fragment);
                break;
            case R.id.suivi_etudiant_btn:
                fragment = new SuiviSujetsEtudiantFragment();
                replaceFragment(fragment);
                break;
            case R.id.choisir_sujets_etudiant_btn:
                fragment = new Fragment_recycle_choisir();
                replaceFragment(fragment);
                break;

        }



    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}