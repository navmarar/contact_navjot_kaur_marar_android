package com.example.contact_navjot_kaur_marar_c0811657_android.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.contact_navjot_kaur_marar_c0811657_android.MainActivity;
import com.example.contact_navjot_kaur_marar_c0811657_android.R;
import com.example.contact_navjot_kaur_marar_c0811657_android.model.Contact;
import com.example.contact_navjot_kaur_marar_c0811657_android.model.ContactView;

import java.util.Arrays;

public class AddContact extends AppCompatActivity {
    public static final String FIRST_NAME = "firstname_reply";
    public static final String LAST_NAME = "lastname_reply";
    public static final String EMAIL = "email_reply";
    public static final String PHONE_NUMBER= "phoneNumber_reply";
    public static final String ADDRESS = "address_reply";


    private EditText etFirstName, etPhoneNumber,etLastName,etEmail,etAddress;
    //private Spinner spinnerDept;

    private boolean isEditing = false;
    private int contactid = 0;
    private Contact contactTobeUpdated;

    private ContactView contactView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_add);

        contactView = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ContactView.class);

        etFirstName = findViewById(R.id.et_Firstname);
        etLastName = findViewById(R.id.et_Lastname);
        etEmail = findViewById(R.id.et_Email);
        etPhoneNumber = findViewById(R.id.et_PhoneNumber);
        etAddress = findViewById(R.id.et_Address);


       // spinnerDept = findViewById(R.id.spinner_dept);

        Button addUpdateButton = findViewById(R.id.btn_add_Contact);

        addUpdateButton.setOnClickListener(v -> {
            addUpdateContact();
        });

        if (getIntent().hasExtra(MainActivity.CONTACT_ID)) {
            contactid = getIntent().getIntExtra(MainActivity.CONTACT_ID, 0);
            Log.d("TAG", "onCreate: " + contactid);

            contactView.getContact(contactid).observe(this, contact -> {
                if (contact != null) {
                    etFirstName.setText(contact.getFirstName());
                    etLastName.setText(contact.getLastName());
                    etEmail.setText(contact.getEmail());
                    etPhoneNumber.setText(String.valueOf(contact.getPhoneNumber()));
                    etAddress.setText(contact.getAddress());


                    //String[] departmentArray = getResources().getStringArray(R.array.departments);
                 //   int position = Arrays.asList(departmentArray).indexOf(employee.getAddress());
                   // spinnerDept.setSelection(position);
                    contactTobeUpdated = contact;
                }
            });
            TextView label = findViewById(R.id.label);
            isEditing = true;
            label.setText(R.string.update_label);
            addUpdateButton.setText(R.string.update_contact_btn_text);
        }
    }

    private void addUpdateContact() {
        String firstname = etFirstName.getText().toString().trim();
        String lastname = etLastName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();


        //String department = spinnerDept.getSelectedItem().toString();

        if (firstname.isEmpty()) {
            etFirstName.setError("name field cannot be empty");
            etFirstName.requestFocus();
            return;
        }
        if (lastname.isEmpty()) {
            etLastName.setError("lastname is required");
            etLastName.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError("Contact field cannot be empty");
            etPhoneNumber.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Enter email Address first");
            etEmail.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            etAddress.setError(" Enter Address Please");
            etAddress.requestFocus();
            return;
        }

        if (isEditing) {
            Contact contact = new Contact();
            contact.setId(contactid);
            contact.setFirstName(firstname);
            contact.setLastName(lastname);
            contact.setEmail(email);
            contact.setAddress(address);
            contact.setPhoneNumber(phoneNumber);

            contactView.update(contact);
        } else {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(FIRST_NAME, firstname);
            replyIntent.putExtra(LAST_NAME, lastname);
            replyIntent.putExtra(PHONE_NUMBER,phoneNumber);
            replyIntent.putExtra(EMAIL,email);
            replyIntent.putExtra(ADDRESS,address);
            setResult(RESULT_OK, replyIntent);

            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}

