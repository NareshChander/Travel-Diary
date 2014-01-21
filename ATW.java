package Android.CompetencyCheckpoint4_Taask1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class ATW extends Activity implements OnClickListener
{
    Button v,a,u,d;
    DBHelp dbase;
    EditText etID,etPlace,etCountry,etYear;
/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        a=(Button)findViewById(R.id.btnAdd);
        d=(Button)findViewById(R.id.btnDelete);
        v=(Button)findViewById(R.id.btnView);
        u=(Button)findViewById(R.id.btnUpdate);

        etID=(EditText)findViewById(R.id.editTextPlaceID);
        etPlace=(EditText)findViewById(R.id.editTextPlace);
        etCountry=(EditText)findViewById(R.id.editTextCountry);
        etYear=(EditText)findViewById(R.id.editTextYear);

        a.setOnClickListener(this);
        d.setOnClickListener(this);
        v.setOnClickListener(this);
        u.setOnClickListener(this);

        dbase = new DBHelp(this);
        dbase.open();
    }

    public void onClick(View vi) 
    {
        long l;
        if(vi==a)
        {
            if(etID.getText().toString().length()>0 && etPlace.getText().toString().length()>0 && etCountry.getText().toString().length()>0 && etYear.getText().toString().length()>0)
            {
                l=dbase.insertValues(Integer.valueOf(etID.getText().toString()),etPlace.getText().toString(),etCountry.getText().toString(),etYear.getText().toString());
                if(l>0)
                    Toast.makeText(this, "Record created with ID : "+l , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "No Record was created. ID already exists." , Toast.LENGTH_SHORT).show();
            }
            else
            {
                String s="";
                if(etID.getText().toString().length()==0)
                    s+="\nEnter ID.";
                if(etPlace.getText().toString().length()==0)
                    s+="\nEnter Place.";
                if(etCountry.getText().toString().length()==0)
                    s+="\nEnter Country.";
                if(etYear.getText().toString().length()==0)
                    s+="\nEnter Year.";
                     Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            }
        }
        
        if(vi==v)
        {
            long id;
            String plcName="";
            String cntryName="";
            String saal="";
            if(etID.getText().toString().length()>0)
            {
                try
                {
                    Cursor cursor = dbase.retrieveValues(Integer.valueOf(etID.getText().toString()));
                    cursor.moveToFirst();
                    do
                    {
                        id = cursor.getLong(0);
                        plcName = cursor.getString(1);
                        cntryName = cursor.getString(2);
                        saal = cursor.getString(3); 
                    }
                    while (cursor.moveToNext());
                    cursor.close();

                    etPlace.setText(plcName);
                    etCountry.setText(cntryName);
                    etYear.setText(saal);
                }
                catch(Exception ee)
                {
                    Toast.makeText(this, "No record with given ID", Toast.LENGTH_SHORT).show(); 
                    etID.setText("");                
                    etPlace.setText("");
                    etCountry.setText("");
                    etYear.setText("");
                }
            }
            else
                  Toast.makeText(this, "Enter ID.", Toast.LENGTH_SHORT).show(); 
        }
        
        if(vi==d)
        {
            if(etID.getText().toString().length()>0)
            {
                AlertDialog show;
                final AlertDialog ad = new AlertDialog.Builder(this).create();  
                show = new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                int value = dbase.deleteValues(Integer.valueOf(etID.getText().toString()));
                                if(value > 0)
                                {
                                    //Toast.makeText(this, "Record deleted!" , Toast.LENGTH_SHORT).show();
                            ad.setCancelable(false); // This blocks the 'BACK' button  
                            ad.setMessage("Record deleted successfully.");  
                            ad.setButton("OK", new DialogInterface.OnClickListener() {  
                                @Override  
                                public void onClick(DialogInterface dialog, int which) {  
                                    dialog.dismiss();                      
                                }  
                            });  
                            ad.show(); 
                                    etID.setText("");
                                    etPlace.setText("");
                                    etCountry.setText("");
                                    etYear.setText("");
                                }
                                else
                                {
                                    //Toast.makeText(this, "No record was deleted. Check ID.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            
            }
        }
        
        if(vi==u)
        {
            if(etID.getText().toString().length()>0 && etPlace.getText().toString().length()>0 && etCountry.getText().toString().length()>0 && etYear.getText().toString().length()>0)
            {
                int value=dbase.updateValues(Integer.valueOf(etID.getText().toString()),etPlace.getText().toString(),etCountry.getText().toString(),etYear.getText().toString());
                if(value>0)
                {
                    Toast.makeText(this, "Record updated successfully.", Toast.LENGTH_SHORT).show();
                    etID.setText("");                
                    etPlace.setText("");
                    etCountry.setText("");
                    etYear.setText("");
                }
                else
                {
                    Toast.makeText(this, "No record updated. Check ID.", Toast.LENGTH_SHORT).show(); 
                }
            }
            else
            {
                String s="";
                if(etID.getText().toString().length()==0)
                    s+="\nEnter ID.";
                if(etPlace.getText().toString().length()==0)
                    s+="\nEnter Place.";
                if(etCountry.getText().toString().length()==0)
                    s+="\nEnter Country.";
                if(etYear.getText().toString().length()==0)
                    s+="\nEnter Year.";
                     Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            }
        }
    }
}

