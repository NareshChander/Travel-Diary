/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Android.CompetencyCheckpoint4_Taask1;

/**
 *
 * @author NARESH
 */
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.content.Context;
import android.database.Cursor;

public class DBHelp extends SQLiteOpenHelper
{
static final int DATABASE_VERSION = 1;
static final String DATABASE_NAME = "AroundTheWorld";
static final String TABLE_NAME = "Places";
static final String TABLE_KEY = "_id";
static final String PLACE_NAME = "placeName";
static final String COUNTRY_NAME = "countryName";
static final String YEAR = "year";
SQLiteDatabase db1;
static final String TABLE_CREATE = "create table " + TABLE_NAME
+  "("  +  TABLE_KEY  +  "  integer primary key,"  +  PLACE_NAME
+" text not null," + COUNTRY_NAME + " text not null," + YEAR + " text not null)";

public DBHelp(Context c)
{
    super(c, DATABASE_NAME, null, DATABASE_VERSION);
}
@Override
public void onCreate(SQLiteDatabase db1)
{
    db1.execSQL(TABLE_CREATE);
}
@Override
public void onUpgrade(SQLiteDatabase db1, int oldVer, int newVer)
{
    db1.execSQL("Drop table if exists Places");
onCreate(db1);
}
//create or open existing db
public void open() throws SQLException
{
    db1 = this.getWritableDatabase();
}
//close db
public void close()
{
    db1.close();
}

public long insertValues(int id,String pName, String cName, String yr)
{
    ContentValues value = new ContentValues();
    value.put(TABLE_KEY, id);
    value.put(PLACE_NAME, pName);
    value.put(COUNTRY_NAME, cName);
    value.put(YEAR, yr);
    long in = db1.insert(TABLE_NAME, null, value);
    return in;
} 

public int deleteValues(int id)
{
    int  value =  db1.delete(TABLE_NAME,  TABLE_KEY  +  "="  + id,  null);
    return value;
}

public int updateValues(int id,String pName, String cName, String yr)
{
    ContentValues value = new ContentValues();
    value.put(PLACE_NAME, pName);
    value.put(COUNTRY_NAME, cName);
    value.put(YEAR, yr);
    int upValue = db1.update(TABLE_NAME, value, TABLE_KEY + "=" + id, null);
    return upValue;
}

public Cursor retrieveValues(int key)
{
    Cursor  c  =  db1.query(TABLE_NAME,  new  String[]  {TABLE_KEY, 
    PLACE_NAME, COUNTRY_NAME,YEAR}, TABLE_KEY+" = "+key, null, null, null, null);
    return c;
}
}
