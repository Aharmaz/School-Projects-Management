package ma.gi.pfa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Update extends AppCompatActivity {

    EditText t1,t2,t3,t4;
    Button UpdateBtn,saveBtn;
    FirebaseFirestore db;
    String title,encadrant,NBE,description,uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        //recuperer les infos
        UpdateBtn = findViewById(R.id.btn);
        saveBtn = findViewById(R.id.save);
        t1=findViewById(R.id.titre);
        t2=findViewById(R.id.PROF);
        t3=findViewById(R.id.NBE);
        t4=findViewById(R.id.desc);



        //update to database
        db = FirebaseFirestore.getInstance();



        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            UpdateBtn.setText("Modifier");
            uId= bundle.getString("id");
            title= bundle.getString("title");
            encadrant = bundle.getString("encadrant");
            NBE = bundle.getString("NBE");
            description= bundle.getString("description");
            t1.setText(title);
            t2.setText(encadrant);
            t3.setText(NBE);
            t4.setText(description);

        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Update.this,Fragment_recycle.class));
            }
        });

        //action update
        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = t1.getText().toString();
                String encadrant = t2.getText().toString();
                String NBE = t3.getText().toString();
                String description = t4.getText().toString();


                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){

                    String id= uId;
                    updateToFireStore(id , title, encadrant, NBE, description);


                }

            }
        });
    }

    //a definir update
    public void updateToFireStore(String id , String title , String encadrant, String NBE, String description){

        db.collection("Sujets").document(id).update("title",title, "encadrant",encadrant, "NBE", NBE, "description", description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Update.this, "Theme modifié avec succés" , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Update.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}