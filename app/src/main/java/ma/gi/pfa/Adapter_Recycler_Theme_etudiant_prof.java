package ma.gi.pfa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
public class Adapter_Recycler_Theme_etudiant_prof extends RecyclerView.Adapter<Adapter_Recycler_Theme_etudiant_prof.MyViewHolder>{


    Context context;
    ArrayList<Theme> themeArrayList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void setThemeArrayList(ArrayList<Theme> themeArrayList)
    {
        this.themeArrayList= new ArrayList<>();
        this.themeArrayList= themeArrayList;
        notifyDataSetChanged();
    }

    public Adapter_Recycler_Theme_etudiant_prof(Context context, ArrayList<Theme> themeArrayList) {

        this.context = context;
        this.themeArrayList = themeArrayList;

    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);



    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Recycler_Theme_etudiant_prof.MyViewHolder holder, int position) {



        Theme theme = themeArrayList.get(position);

        holder.itemView.setBackgroundColor(theme.isChecked() ? Color.CYAN : Color.LTGRAY);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme.setChecked(!theme.isChecked());
                holder.imageView.setBackgroundColor(theme.isChecked()? Color.CYAN : Color.LTGRAY);
            }
        });
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

        TextView title, nbe, description,encadrant, empty;

        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            nbe = itemView.findViewById(R.id.nbe);
            description = itemView.findViewById(R.id.desc);
            encadrant=itemView.findViewById(R.id.prof);
            imageView = itemView.findViewById(R.id.img);
            empty=itemView.findViewById(R.id.empty);

        }



    }

  /*get all items selected*/
    public ArrayList<Theme> getAll()
    {
        return themeArrayList;
    }

    //get selected when btn clicked
    public ArrayList<Theme> getSelected(){
        ArrayList<Theme> selected = new ArrayList<>();
        for(int i=0;i < themeArrayList.size(); i++)
        {
            if(themeArrayList.get(i).isChecked())
            {
                selected.add(themeArrayList.get(i));
            }
        }

        return selected;
    }
}
