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


public class EspaceProfFragment extends Fragment implements  View.OnClickListener{

    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        View rootView =  inflater.inflate(R.layout.fragment_espace_prof, container, false);

        Button btn1 = (Button) rootView.findViewById(R.id.sujets_prop_prof_btn);
        Button btn2 = (Button) rootView.findViewById(R.id.demandes_prof_btn);
        Button btn3 = (Button) rootView.findViewById(R.id.affectations_finales_prof_btn);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);




        return rootView;

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.sujets_prop_prof_btn:
                fragment = new Fragment_recycle_etudiant_prof();
                replaceFragment(fragment);
                break;
            case R.id.demandes_prof_btn:
                fragment = new SujetsDemandesProfFragment();
                replaceFragment(fragment);
                break;
            case R.id.affectations_finales_prof_btn:
                fragment = new AffectationsFinalesProfFragment();
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
