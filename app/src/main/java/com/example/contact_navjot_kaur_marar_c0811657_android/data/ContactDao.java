package com.example.contact_navjot_kaur_marar_c0811657_android.data;

import android.app.Person;

import androidx.constraintlayout.helper.widget.Flow;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contact_navjot_kaur_marar_c0811657_android.model.Contact;
import java.util.List;
@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("SELECT * FROM contact_table ORDER BY FirstName ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contact_table WHERE id == :id")
    LiveData<Contact> getContact(int id);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
/*

    @Query("SELECT *FROM contact_table WHERE FirstName LIKE:searchQuery OR LastName LIKE:searchQuery ")
    LiveData<List<Person>> searchQuery();

    void searchDatabase(String searchQuery);
*/

    //fun searchDatabase(searchQuery:String String searchQuery): Flow<List<Person>>

}
