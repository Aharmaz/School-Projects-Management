package ma.gi.pfa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Recycler_Theme_Choisir extends RecyclerView.Adapter<Adapter_Recycler_Theme_Choisir.MyViewHolder>{

    Context context;
    ArrayList<ThemeChoisir> themeChoisirArrayList;


    public Adapter_Recycler_Theme_Choisir(Context context, ArrayList<ThemeChoisir> themeChoisirArrayList) {

        this.context = context;
        this.themeChoisirArrayList = themeChoisirArrayList;
    }

    @NonNull
    @Override
    public Adapter_Recycler_Theme_Choisir.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_choisir, parent, false);

        return new Adapter_Recycler_Theme_Choisir.MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Recycler_Theme_Choisir.MyViewHolder holder, int position) {

        ThemeChoisir themeChoisir = themeChoisirArrayList.get(position);
        holder.title.setText(themeChoisir.getTitle());





    }



    @Override
    public int getItemCount() {
        return themeChoisirArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_choisir);


        }
    }

}
