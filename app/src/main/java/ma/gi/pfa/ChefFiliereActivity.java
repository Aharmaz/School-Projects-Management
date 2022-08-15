
package ma.gi.pfa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ma.gi.pfa.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChefFiliereActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    FirebaseAuth firebaseAuth;
    TextView nom ;
    FirebaseFirestore db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pour_chef_filiere);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Data from bdd
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        String emailUser = user.getEmail().toString();
        nom=navigationView.getHeaderView(0).findViewById(R.id.nomChef);

        db.collection("/ChefFiliere").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String emailChef = document.getString("emailC");
                        if (emailUser.equals(emailChef)) {
                            //Personalisation du nom dans le menu
                            nom.setText(document.getString("nomChef"));
                            break;
                        }
                    }
                }
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                    EspaceChefFiliereFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_espace_chef_filiere);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_espace_chef_filiere:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EspaceChefFiliereFragment()).commit();
                break;

            case R.id.nav_affectations_finales_chef_filiere:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AffectationsFinalesChefFiliereFragment()).commit();
                break;



            case R.id.nav_ajouter_sujet_chef_filiere:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragement_Ajout_Sujet()).commit();
                break;
            case R.id.nav_sujets_prop_chef_filiere:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment_recycle()).commit();
                break;

            case R.id.nav_deconnexion_chef_filiere:
                new AlertDialog.Builder(this).setIcon(R.drawable.ic_deconnexion)
                        .setTitle("Log out").setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                signOutUser();
                                //finish();
                                Toast.makeText(ChefFiliereActivity.this, "déconnecté",Toast.LENGTH_SHORT).show();
                            }
                            //}).setNegativeButton("No", null).show();
                        }).setNegativeButton("No", null).show();

                break;




        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOutUser() {
        Intent mainActivity =new Intent(ChefFiliereActivity.this,MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public void onBackPressed() {




        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new EspaceChefFiliereFragment()).commit();

        }




    }
}