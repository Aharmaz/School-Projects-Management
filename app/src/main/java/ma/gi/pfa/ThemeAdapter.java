package ma.gi.pfa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ThemeAdapter extends FirestoreRecyclerAdapter<Theme,ThemeAdapter.MyViewHolder> {

    public ThemeAdapter(@NonNull FirestoreRecyclerOptions<Theme> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Theme model) {
        holder.title.setText(model.getTitle());
        holder.nbe.setText(model.getNBE());
        holder.description.setText(model.getDescription());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ThemeAdapter.MyViewHolder(v);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, nbe, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            nbe = itemView.findViewById(R.id.nbe);
            description = itemView.findViewById(R.id.desc);
        }
    }
}
