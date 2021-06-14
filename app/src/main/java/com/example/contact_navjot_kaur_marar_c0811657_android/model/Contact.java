package com.example.contact_navjot_kaur_marar_c0811657_android.model;
//this is our entity in room in db
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// this is our entity in Room db
@Entity(tableName = "contact_table")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "FirstName")
    @NonNull
    private String FirstName;

    @ColumnInfo(name = "LastName")
    private String LastName;

    @ColumnInfo(name = "Email")
    private String Email;

    @ColumnInfo(name = "PhoneNumber")
    private String PhoneNumber;

    @ColumnInfo(name = "Address")
    private String Address;


    @Ignore
    public Contact() {
    }

    public Contact(@NonNull String FirstName, String LastName, String Email, String PhoneNumber, String Address) {
        this.FirstName = FirstName;
        this.LastName= LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.Address = Address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(@NonNull String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String Phonenumber) {
        this.PhoneNumber = Phonenumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

}
