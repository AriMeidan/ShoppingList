package com.meidan.shoppinglist;

import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	private final String DB_NAME = "ShoppingDbName"; 
	private final String TABLE_NAME = "ShoppingTableName";
	SQLiteDatabase shoppingDB = null;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> results = new ArrayList<String>();
        try {
        	shoppingDB = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        	shoppingDB.execSQL("CREATE TABLE IF NOT EXISTS "  
        						+ TABLE_NAME	
        						+ "(Name VARCHAR NOT NULL, " 
        						+ "Barcode INT UNIQUE, "
        						+ "Price REAL);");
        	insertExampleToDb();
        	
        	Cursor c = shoppingDB.rawQuery("SELECT * "
        									+ "FROM " + TABLE_NAME , null);
        	
        	if (c != null){
        		if(c.moveToFirst()) {
        			do {
        				String name = c.getString(c.getColumnIndex("Name"));
        				//int code = c.getInt(c.getColumnIndex("Barcode"));
        				double price = c.getDouble(c.getColumnIndex("Price"));
        				results.add(name + ", " + price);
        			}while(c.moveToNext());
        		}
        	}
        	
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));	
        						
        }catch(SQLiteException e){
        	//Log.e(getClass().getSimpleName(), "create/Open the database problem");
        	Log.e(getClass().getSimpleName(), e.getMessage());
        }finally{
        	if (shoppingDB != null)
        		shoppingDB.delete(TABLE_NAME, null, null);
        		Log.d(getClass().getSimpleName(), "Table Deleted!");
        		shoppingDB.close();
        }
        //setContentView(R.layout.activity_main);
        
    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	
    private void insertExampleToDb() {
    	String hebName1 = "קוטג";
    	String hebName2 = "חלב";
		shoppingDB.execSQL("INSERT INTO " 
							+ TABLE_NAME
							+ " VALUES('"+hebName1+"', 121212, 2.53);");
		
		shoppingDB.execSQL("INSERT INTO " 
							+ TABLE_NAME
							+ " VALUES('"+hebName2+"', 3333333, 6.69);");
		
		
	}
}
