package com.example.contact_navjot_kaur_marar_c0811657_android.model;

import androidx.lifecycle.AndroidViewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import androidx.lifecycle.LiveData;
import com.example.contact_navjot_kaur_marar_c0811657_android.data.ContactRepo;
import java.util.List;
public class ContactView extends AndroidViewModel {
    private ContactRepo repository;
    private final LiveData<List<Contact>> allContacts;

    public ContactView(@NonNull Application application) {
        super(application);

        repository = new ContactRepo(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {return allContacts;}

    public LiveData<Contact> getContact(int id) {return repository.getContact(id);}

    public void insert(Contact contact) {repository.insert(contact);}

    public void update(Contact contact) {repository.update(contact);}

    public void delete(Contact contact) {repository.delete(contact);}
}
