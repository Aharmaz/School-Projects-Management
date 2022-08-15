package ma.gi.pfa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText loginEmail, loginPassword;
    TextView forgotPassword;
    Button loginButton;
    Spinner roleSpinner;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        forgotPassword = findViewById(R.id.forgotPassword);
        loginEmail = findViewById(R.id.champEmail);
        loginPassword = findViewById(R.id.champPassword);
        loginButton = findViewById(R.id.loginBtn);
        roleSpinner = findViewById(R.id.roleSpinner);
        forgotPassword = findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        final Dialog recupDialog = new Dialog(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                //Recuperation des donnees
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                String role = roleSpinner.getSelectedItem().toString().trim();

                //Test des donnes entrees
                if(TextUtils.isEmpty(email)) {
                    loginEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is required");
                    return;
                }

                //Authentication
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(role.equals("Etudiant")) {
                                startActivity(new Intent(getApplicationContext(), EtudiantActivity.class));
                            } else if(role.equals("Chef de filiere")) {
                                startActivity(new Intent(getApplicationContext(), ChefFiliereActivity.class));
                            } else if (role.equals("Professeur")) {
                                startActivity(new Intent(getApplicationContext(), ProfActivity.class));
                            }
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, "Authentication Failed" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        */
                //Recupiration des donnees
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                String role = roleSpinner.getSelectedItem().toString().trim();

                //Test des donnes entrees
                if(TextUtils.isEmpty(email)) {
                    loginEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is required");
                    return;
                }
                //Authentification
                if(role.equals("Chef de filiere")) {
                    db.collection("/ChefFiliere")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> extractionTask) {
                                    if (extractionTask.isSuccessful()) {
                                        //List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    for (QueryDocumentSnapshot document : extractionTask.getResult()) {
                                                        String emailChef = document.getString("emailC");
                                                        if (email.equals(emailChef)) {
                                                            startActivity(new Intent(getApplicationContext(), ChefFiliereActivity.class));
                                                            break;
                                                        }else {
                                                            Toast.makeText(MainActivity.this, "Authentication Failed" , Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                }else {
                                                    Toast.makeText(MainActivity.this, "Authentication Failed" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                } else if(role.equals("Etudiant")){
                    db.collection("/etudiants")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> extractionTask) {
                                    if (extractionTask.isSuccessful()) {
                                        //List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    for (QueryDocumentSnapshot document : extractionTask.getResult()) {
                                                        String emailEtudiant = document.getString("emailEtudiant");

                                                        if (email.equals(emailEtudiant)) {
                                                            startActivity(new Intent(getApplicationContext(), EtudiantActivity.class));
                                                            break;
                                                        }
                                                    }
                                                }else {
                                                    Toast.makeText(MainActivity.this, "Authentication Failed" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                }
                            });
                }else if(role.equals("Professeur")) {
                    db.collection("/Professeurs")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> extractionTask) {
                                    if (extractionTask.isSuccessful()) {
                                        //List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    for (QueryDocumentSnapshot document : extractionTask.getResult()) {
                                                        String emailProf = document.getString("emailProf");

                                                        if (email.equals(emailProf)) {
                                                            startActivity(new Intent(getApplicationContext(), ProfActivity.class));
                                                            break;
                                                        }else {
                                                            Toast.makeText(MainActivity.this, "Authentication Failed" , Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                }else {
                                                    Toast.makeText(MainActivity.this, "Authentication Failed" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }else {
                    Toast.makeText(MainActivity.this, "Veuillez choisir un role", Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder forgotPasswordDialog = new AlertDialog.Builder(v.getContext());

                forgotPasswordDialog.setTitle("Forgot Password ?");
                forgotPasswordDialog.setMessage("Entrer Votre E-mail pour recevoir le lien de récuperation ");
                forgotPasswordDialog.setView(resetEmail);

                forgotPasswordDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = resetEmail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Lien Envoyé", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error ! Veuillez ressayer plus tard ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                forgotPasswordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                forgotPasswordDialog.create().show();*/
                recupDialog.setContentView(R.layout.forgot_password);
                Button send = recupDialog.findViewById(R.id.sendEmail);
                EditText resetEmail = recupDialog.findViewById(R.id.champEmailRecuperation) ;

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = resetEmail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Lien Envoyé", Toast.LENGTH_SHORT).show();
                                recupDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error ! Veuillez ressayer plus tard ", Toast.LENGTH_SHORT).show();
                                recupDialog.dismiss();
                            }
                        });
                    }

                });
                recupDialog.show();
            }
        });

    }
}