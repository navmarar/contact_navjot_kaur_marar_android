package com.example.contact_navjot_kaur_marar_c0811657_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;


import androidx.recyclerview.widget.LinearLayoutManager;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.contact_navjot_kaur_marar_c0811657_android.adapter.RecyclerViewAdapter;
import com.example.contact_navjot_kaur_marar_c0811657_android.model.Contact;
import com.example.contact_navjot_kaur_marar_c0811657_android.model.ContactView;
import com.example.contact_navjot_kaur_marar_c0811657_android.util.AddContact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class MainActivity extends AppCompatActivity  implements RecyclerViewAdapter.OnContactClickListener, RecyclerViewAdapter.OnContactLongClickListener {


    public static final String CONTACT_ID = "contact_id";
    private static final int REQUEST_PHONE_CALL = 1;

    // declaration of employeeViewModel
    private ContactView contactView;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private Contact deletedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // instantiating the contactViewModel
        contactView = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ContactView.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactView.getAllContacts().observe(this, contacts -> {
            // set adapter
            recyclerViewAdapter = new RecyclerViewAdapter(contacts, this, this, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContact.class);
            /*startActivityForResult(intent, ADD_EMPLOYEE_REQUEST_CODE);*/
            // the following approach as startActivityForResult is deprecated
            launcher.launch(intent);

        });

        // attach the itemTouchHelper to my recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Contact contact = contactView.getAllContacts().getValue().get(position);
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Are you sure to delete this contact?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        deletedContact = contact;
                        contactView.delete(contact);
                        Snackbar.make(recyclerView, deletedContact.getFirstName() + " is deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Undo", v -> contactView.insert(deletedContact)).show();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> recyclerViewAdapter.notifyDataSetChanged());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
                case ItemTouchHelper.RIGHT:
                    Intent intent = new Intent(MainActivity.this, AddContact.class);
                    intent.putExtra(CONTACT_ID, contact.getId());
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            /*new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .setIconHorizontalMargin(1, 1)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightActionIcon(R.drawable.ic_update)
                    .create()
                    .decorate();*/
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    // the following approach instead of onActivityResult as startActivityForResult is deprecated
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String FirstName = data.getStringExtra(AddContact.FIRST_NAME);
                    String LastName = data.getStringExtra(AddContact.LAST_NAME);
                    String Email = data.getStringExtra(AddContact.EMAIL);
                    String PhoneNumber = data.getStringExtra(AddContact.PHONE_NUMBER);
                    String Address = data.getStringExtra(AddContact.ADDRESS);


                    Contact contact = new Contact(FirstName, LastName, Email, PhoneNumber, Address);
                    contactView.insert(contact);
                }
            });


    @Override
    public void onContactLongClick(int position) {

       Intent intent = new Intent(Intent.ACTION_CALL);
       Contact contact = contactView.getAllContacts().getValue().get(position);
       String makecall = contact.getPhoneNumber();
       intent.setData(Uri.parse("tel:" + makecall));
       if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                   Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
           }
else {
           startActivity(intent);
        }
    }



    @Override
    public void onContactClick(int position) {

        Contact contact = contactView.getAllContacts().getValue().get(position);
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        intent.putExtra(CONTACT_ID, contact.getId());
        startActivity(intent);
    }



}
