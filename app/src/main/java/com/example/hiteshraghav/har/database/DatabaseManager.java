package com.example.hiteshraghav.har.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DatabaseManager {

    private static final String DATABASE_NAME = "meditracker.db";
    //Patient table columns
    public static final String KEY_PatID = "_id";
    public static final String KEY_PatientServerID = "patient_id";
    public static final String KEY_PatientFirstName = "first_name";
    public static final String KEY_PatientLastName = "last_name";
    public static final String KEY_PatientDateOfBirth = "dob";
    public static final String KEY_PatientAge = "age";
    public static final String KEY_PatientGender = "gender";
    public static final String KEY_PatientEmailAddress = "email_address";
    public static final String KEY_PatientContactNumber = "contact_number";
    public static final String KEY_PatientImage = "profile_image";
    public static final String KEY_PushToken = "push_token";


    //medicines table columns
    public static final String KEY_ID="id";
    public static final String KEY_PAT_UID="pat_uid";
    public static final String KEY_Prescription_date="prescription_date";
    public static final String KEY_prescription="prescription";

    //BP record value for Patient
    public static final String KEY_PatId_BP="patient_id";
    public static final String KEY_BpSystolic="bp_systolic";
    public static final String KEY_BpDiastolic="bp_Diastolic";
    public static final String KEY_TestDate="tested_on";


    private static final String Medicine_TABLE = "medicines";
    private static final String Patient_TABLE = "patient_info";
    private static final String Blood_Pressure_Table="blood_pressure_records";
    private static final int DATABASE_VERSION = 1;

    private final Context ourContext;
    private DbHelper dbh;
    private SQLiteDatabase odb;


    private static final String PATIENT_INFO_CREATE =
            "CREATE TABLE IF NOT EXISTS " + Patient_TABLE+ "("
                    + KEY_PatID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_PatientServerID+" TEXT,"
                    +KEY_PatientFirstName+" TEXT,"+KEY_PatientLastName+" TEXT,"
                    +KEY_PatientDateOfBirth+" TEXT,"
                    +KEY_PatientAge+" INTEGER,"+KEY_PatientGender+" TEXT,"
                    +KEY_PatientEmailAddress+" TEXT,"+KEY_PatientContactNumber+" TEXT,"
                    + KEY_PatientImage + " BLOB,"+KEY_PushToken+" TEXT )";

    private static final String MEDICINES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+ Medicine_TABLE+ "("
                    +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_PAT_UID + " TEXT,"
                    +KEY_Prescription_date +" TEXT,"
                    +KEY_prescription +" TEXT)";

    private static final String BLOOD_PRESSURE_RECORD_TABLE_CREATE=
            "CREATE TABLE IF NOT EXISTS " + Blood_Pressure_Table+ "("
                    +KEY_PatId_BP+" TEXT,"
                    +KEY_BpSystolic+" INTEGER,"
                    +KEY_BpDiastolic+" INTEGER,"
                    + KEY_TestDate + " DATETIME DEFAULT CURRENT_TIMESTAMP )";


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(PATIENT_INFO_CREATE);
            db.execSQL(MEDICINES_TABLE_CREATE);
            db.execSQL(BLOOD_PRESSURE_RECORD_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // if DATABASE VERSION changes
            // Drop old tables and call super.onCreate()
        }
    }

    public DatabaseManager(Context c) {
        ourContext = c;
        dbh = new DbHelper(ourContext);
    }

    public DatabaseManager open() throws SQLException {
        odb = dbh.getWritableDatabase();
        return this;
    }

    public void close() {
        dbh.close();
    }




    public Cursor getAllPatients(String orderByType) {
        // using simple SQL query
        Cursor cursor=null;
        if (orderByType.equalsIgnoreCase("id")){
            cursor = odb.rawQuery("select * from " +Patient_TABLE, null);
            Log.e("data base","id");
        }
        else if(orderByType.equalsIgnoreCase("name_asc")){
            cursor = odb.rawQuery("select * from " +Patient_TABLE+" order by UPPER("+KEY_PatientFirstName+")", null);
            Log.e("data base","first name asc");
        }
        else if(orderByType.equalsIgnoreCase("name_desc")){
            cursor = odb.rawQuery("select * from " +Patient_TABLE+" order by UPPER("+KEY_PatientFirstName+") desc", null);
            Log.e("data base","first name desc");
        }
        else if(orderByType.equalsIgnoreCase("age_asc")){
            cursor = odb.rawQuery("select * from " +Patient_TABLE+" order by "+KEY_PatientAge+" asc", null);
            Log.e("data base","age asc");
        }
        else if(orderByType.equalsIgnoreCase("age_desc")){
            cursor = odb.rawQuery("select * from " +Patient_TABLE+" order by "+KEY_PatientAge+" desc", null);
            Log.e("data base","age desc");
        }
        return cursor;
    }


    public long insertPatientDetails(String firstName, String lastName, int age, String gender, String email_address, String contact_number, String dateOfBirth, String patientServerID, byte[] patientImage, String pushToken) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put(KEY_PatientFirstName, firstName);
        cv.put(KEY_PatientLastName,lastName);
        cv.put(KEY_PatientAge,age);
        cv.put(KEY_PatientGender,gender);
        cv.put(KEY_PatientEmailAddress,email_address);
        cv.put(KEY_PatientContactNumber,contact_number);
        cv.put(KEY_PatientServerID,patientServerID);
        if (dateOfBirth!=null){
            cv.put(KEY_PatientDateOfBirth,dateOfBirth);
        }
        if (patientImage!=null){
            cv.put(KEY_PatientImage,patientImage);
        }
        if (pushToken!=null){
            cv.put(KEY_PushToken,pushToken);

        }
        return odb.insert(Patient_TABLE, null, cv);
        // returns a number >0 if inserting data is successful
    }

    public void updatePatientProfileImage(String pat_uid, byte[] patImage) {
        ContentValues values = new ContentValues();
        values.put(KEY_PatientImage, patImage);
        try {
            odb.update(Patient_TABLE, values, KEY_PatientServerID + "='" + pat_uid+"'", null);
        } catch (Exception e) {
        }
    }


    public long addPrescription(String pat_uid, String pdate, String prescription) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put(KEY_PAT_UID, pat_uid);
        cv.put(KEY_Prescription_date,pdate);
        cv.put(KEY_prescription,prescription);

        return odb.insert(Medicine_TABLE, null, cv);
    }

    public Cursor getAllPrescriptions(String pat_uid){
        Cursor medicineCursor=odb.rawQuery("select * from " +Medicine_TABLE+" where pat_uid='"+pat_uid+"'", null);
        return medicineCursor;
    }

    public ArrayList<JSONObject> getAllPrescriptionsByDate(String pat_uid, ArrayList<String> pdate){
        ArrayList<JSONObject> prescriptionList = new ArrayList<JSONObject>();
        int size = pdate.size();
        for(int i = 0 ; i < size ;i++){
            Cursor cur=odb.rawQuery("select * from " +Medicine_TABLE+" where (pat_uid='"+pat_uid+"' and prescription_date='"+pdate.get(i)+"')", null);
            if (cur != null) {
                if (cur.getCount() != 0) {
                    cur.moveToFirst();
                    String prescriptionDate = pdate.get(i);
                    StringBuilder medicine = new StringBuilder();
                    while (cur.isAfterLast() == false) {
                        String prescriptionString = cur.getString(cur.getColumnIndexOrThrow("prescription"));
                        String medName = null;
                        try {
                            JSONObject j = new JSONObject(prescriptionString);
                            medName = j.getString("MediName");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        medicine.append(medName+" ");
                        cur.moveToNext();
                    }
                    JSONObject json = null;
                    try {
                        json = new JSONObject();
                        json.put("pdate",prescriptionDate);
                        json.put("prescription",medicine.toString());
                    }  catch (JSONException e) {
                        e.printStackTrace();
                    }

                    prescriptionList.add(json);
                    cur.close();
                    cur = null;
                }
            }
        }
        return prescriptionList;
    }

    public ArrayList<String> getPrescriptionsWrittenToday(String pat_uid, String date){
        Cursor cur=odb.rawQuery("select * from " +Medicine_TABLE+" where (pat_uid='"+pat_uid+"' and prescription_date='"+date+"')", null);
        ArrayList<String> prescriptionList = new ArrayList<String>();
        if (cur != null) {
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                StringBuilder medicine = new StringBuilder();
                while (cur.isAfterLast() == false) {
                    String prescriptionString = cur.getString(cur.getColumnIndexOrThrow("prescription"));
                    prescriptionList.add(prescriptionString);
                    cur.moveToNext();
                }
                cur.close();
                cur = null;
            }
        }
        return prescriptionList;
    }

    public Cursor Query(String query, String[] selectionArgs) {
        return odb.rawQuery(query, selectionArgs);
    }
}