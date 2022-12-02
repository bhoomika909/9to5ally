package com.example.a001;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class myadaptar extends RecyclerView.Adapter<myadaptar.MyViewHolder> {

    Context context;
    ArrayList<item> list;
    private SelectListener listener;
    boolean flag = false;
    public myadaptar(Context context, ArrayList<item> list,SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        item user = list.get(position);
        holder.subject.setText(user.getSubject());
        holder.Name.setText(user.getName());
        holder.Description.setText(user.getDescription());
        holder.RealName.setText(user.getRealName());
        holder.Status.setText(user.getStatus());
        String name = user.getStatus();
        System.out.println("Ayush "+name);
        String comp = "Your Complain is Pending !!";
        if(name.equals(comp))
        {
            holder.setstatus.setImageResource(R.drawable.ic_baseline_pending_24);
        }
        else
        {
            holder.setstatus.setImageResource(R.drawable.ic_baseline_done_outline_24);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView subject, Name, Description,RealName,Status;
        ImageView setstatus;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.tvfirstName);
            Name = itemView.findViewById(R.id.tvlastName);
            Description = itemView.findViewById(R.id.tvage);
            cardView = itemView.findViewById(R.id.main_container);
            RealName = itemView.findViewById(R.id.realtvfirstName);
            Status = itemView.findViewById(R.id.realtvfirstName);
            setstatus = itemView.findViewById(R.id.imageView3);
        }
    }

}
