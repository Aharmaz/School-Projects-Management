package ma.gi.pfa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
public class Adapter_Recycler_Theme extends RecyclerView.Adapter<Adapter_Recycler_Theme.MyViewHolder>{


    Context context;
    ArrayList<Theme> themeArrayList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Adapter_Recycler_Theme(Context context, ArrayList<Theme> themeArrayList) {

        this.context = context;
        this.themeArrayList = themeArrayList;
    }

    //Update item
    public void updateData (int position){
        Theme itm = themeArrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id",itm.getId());
        bundle.putString("title" , itm.getTitle());
        bundle.putString("encadrant" , itm.getEncadrant());
        bundle.putString("NBE" , itm.getNBE());
        bundle.putString("description" , itm.getDescription());
        Intent intent= new Intent(context,Update.class);
        intent.putExtras(bundle);
        context.startActivity(intent);}

    //Traitement remove item
    public void deleteData(int position)
    {
        Theme item = themeArrayList.get(position);
        db.collection("Sujets").document(item.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    notifyRemoved(position);
                    Toast.makeText(context, "Theme supprimé!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Error de suppression!!" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //remove
    public void notifyRemoved(int position){
        themeArrayList.remove(position);
        notifyItemRemoved(position);

            /*Alerte dialog delete


            AlertDialog.Builder builder = new AlertDialog.Builder();
            builder.setTitle("Confirmation !!!!");
            builder.setMessage("Voulez-vous Vraiment supprimer ce sujet ?");
            builder.setPositiveButton("Oui",null);
            builder.setNegativeButton("Non",null);
            builder.show();*/
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Recycler_Theme.MyViewHolder holder, int position) {

        Theme theme = themeArrayList.get(position);
        holder.title.setText(theme.getTitle());
        holder.nbe.setText(theme.getNBE());
        holder.description.setText(theme.getDescription());
        holder.encadrant.setText(theme.getEncadrant());


    }



    @Override
    public int getItemCount() {
        return themeArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, nbe, description,encadrant;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            nbe = itemView.findViewById(R.id.nbe);
            description = itemView.findViewById(R.id.desc);
            encadrant=itemView.findViewById(R.id.prof);

        }
    }
}

