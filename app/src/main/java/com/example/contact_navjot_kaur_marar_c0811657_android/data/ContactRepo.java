package com.example.contact_navjot_kaur_marar_c0811657_android.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contact_navjot_kaur_marar_c0811657_android.model.Contact;
import com.example.contact_navjot_kaur_marar_c0811657_android.util.ContactRoomDatabase;

import java.util.List;

public class ContactRepo {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    public ContactRepo(Application application) {
 ContactRoomDatabase db = ContactRoomDatabase.getInstance(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<Contact> getContact(int id) {return contactDao.getContact(id);}

    public void insert(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.insert(contact));
    }
  /*  public void searchDatabase(Contact contact) {
        String searchQuery = null;
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.searchDatabase(searchQuery));
    }*/
    public void update(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.update(contact));
    }

    public void delete(Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.delete(contact));
    }
}
