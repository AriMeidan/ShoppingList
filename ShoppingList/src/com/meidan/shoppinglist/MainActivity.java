package com.meidan.shoppinglist;

import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends ExpandableListActivity {
	
	private final String DB_NAME = "ShoppingDbName"; 
	private final String TABLE_NAME = "ShoppingTableName";
	SQLiteDatabase shoppingDB = null;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> results = new ArrayList<String>();
        try {
        	shoppingDB = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        	shoppingDB.execSQL("CREATE TABLE IF NOT EXIST "  
        						+ TABLE_NAME	
        						+ "(Name VARCHAR NOT NULL " 
        						+ "Barcode INT UNIQUE "
        						+ "Price REAL);");
        	insertExampleToDb();
        	
        	Cursor c = shoppingDB.rawQuery("SELECT ALL " 
        									+ " FROM " + TABLE_NAME, null);
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
        	
        	
        						
        }
        setContentView(R.layout.activity_main);
    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	
    private void insertExampleToDb() {
    	String hebName = "קוטג' 5%";
		shoppingDB.execSQL("INSERT TO " 
							+ TABLE_NAME
							+ " Values("+hebName+", 121212, 2.53);");
		
		
	}
}
