package com.example.contact_navjot_kaur_marar_c0811657_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact_navjot_kaur_marar_c0811657_android.R;
import com.example.contact_navjot_kaur_marar_c0811657_android.model.Contact;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Contact> contactlist;
    private Context context;
    private OnContactClickListener onContactClickListener;
    private OnContactLongClickListener onContactLongClickListener;

    public RecyclerViewAdapter(List<Contact> contactlist, Context context, OnContactClickListener onContactClickListener,OnContactLongClickListener onContactLongClickListener) {
        this.contactlist = contactlist;
        this.context = context;
        this.onContactClickListener = onContactClickListener;
        this.onContactLongClickListener = onContactLongClickListener;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Contact contact = contactlist.get(position);
        holder.firstName.setText(contact.getFirstName());
        holder.lastName.setText(contact.getLastName());
        holder.address.setText(contact.getAddress());
        holder.email.setText(contact.getEmail());
        holder.phoneNumber.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{
        private TextView firstName;
        private TextView lastName;
        private TextView address;
        private TextView email;
        private TextView phoneNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstname);
            lastName = itemView.findViewById(R.id.lastname);
            address = itemView.findViewById(R.id.address);
            email = itemView.findViewById(R.id.email);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        @Override
        public void onClick(View v) {
            onContactClickListener.onContactClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onContactLongClickListener.onContactLongClick(getAdapterPosition());
            return false;
        }
    }

 public interface OnContactClickListener{
     void onContactClick(int position);
 }
    public interface OnContactLongClickListener{
        void onContactLongClick(int position);
    }
}
