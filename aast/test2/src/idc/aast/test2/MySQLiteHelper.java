package idc.aast.test2;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

public class MySQLiteHelper extends SQLiteOpenHelper {

	 private static final int DATABASE_VERSION = 5;
	    // Database Name
	    private static final String DATABASE_NAME = "AAST_Notifications";
	    
	    private static final String KEY_message ="message";
	    private static final String KEY_message_title = "message_title";
	    private static final String KEY_message_desc = "message_desc"; 
	    private static final String KEY_Serial = "Serial";
	    private static final String KEY_Read = "Read";
	    private static final String KEY_DateTime = "DateTime";
	    private static final String KEY_link = "link";
	    private static final String KEY_sys_code = "sys_code";
	    private static final String KEY_sys_name = "sys_name";
	    private static final String KEY_user_name = "user_name";
	    private static final String KEY_user_type = "user_type";
	    private static final String[] COLUMNS = {
	    	"id",
	    	KEY_message,
	    	KEY_message_title,
	    	KEY_message_desc,
	    	KEY_Serial,
	    	KEY_Read,
	    	KEY_DateTime,
	    	KEY_link,
	    	KEY_sys_code,
	    	KEY_sys_name,
	    	KEY_user_name,
	    	KEY_user_type,
	    };
		 
	      // create books table
	    private static final String[] res_columns = {
	   
	    	"user_id",
	    	"term_id",
	    	"term_desc",
	    	"course_code",
	    	"course_name",
	    	"seventh_degree",
	    	"twelves",
	    	"semi_degree",
	    	"grade_text",
	    	"hours",
	    	
	    };
	    
  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
	  String Create_message_table = "CREATE TABLE messages ( " +
              "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "message TEXT, "+
              "message_title TEXT, "+
              "message_desc TEXT, "+
              "Serial TEXT, "+
              "DateTime TEXT, "+
              "Read TEXT, "+
              "link TEXT, "+
              "sys_code TEXT, "+
              "sys_name TEXT, "+
              "user_name TEXT, "	+

              "user_type TEXT )";
	  String Create_account_table = "CREATE TABLE accounts ( " +
              "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "account TEXT, "+
              "type TEXT )";
	  
	  String Create_result_table = "CREATE TABLE results ( " +
              "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "user_id TEXT, "+
              "term_id TEXT, "+
              "term_desc TEXT, "+
              "course_code TEXT, "+
              "course_name TEXT, "+
              "seventh_degree TEXT, "+
              "twelves TEXT, "+
              "semi_degree TEXT, "+
              "grade_text TEXT, "+
              "hours TEXT )";
      // create books table
	  database.execSQL(Create_message_table);
	  database.execSQL(Create_result_table);
	 database.execSQL(Create_account_table);
	 database.execSQL("ALTER TABLE accounts ADD COLUMN links TEXT");
	 database.execSQL("ALTER TABLE accounts ADD COLUMN counts TEXT");
	 database.execSQL("ALTER TABLE accounts ADD COLUMN morasalat TEXT");
	 database.execSQL("ALTER TABLE accounts ADD COLUMN results TEXT");
  }
  
  
  public ArrayList<Pair<String, String>> getAccounts()
  {
	  ArrayList<Pair<String, String>> msgs = new ArrayList<Pair<String, String>>();
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("results", // a. table
	            new String[]{"account","type"}, // b. column names
	          null, // c. selections
	           null, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) {
          do {
        	

              // Add book to books
        	  msgs.add(new Pair<String, String>(cursor.getString(0),cursor.getString(1)));
          } while (cursor.moveToNext());
      }
      db.close();
      return msgs;
	  
  }
  public int insert_result(result_item res)
  {	 
	  SQLiteDatabase db = this.getReadableDatabase();
  Cursor cursor =
          db.query("results", // a. table
          new String[]{"count(user_id)"}, // b. column names
          "user_id = ? and term_id = ? and course_code = ?", // selections
            new String[] { String.valueOf(res.user_id),String.valueOf(res.term_id),String.valueOf(res.course_code)}, // d. selections args
          null, // e. group by
          null, // f. having
          null, // g. order by
          null); // h. limit
// 3. go over each row, build book and add it to list
  int count = 0;
if (cursor.moveToFirst()) 
{
//	    db.close();
	count =cursor.getInt(0);
	
	
}
if(count  == 0)
{

	  ContentValues values = new ContentValues();
      values.put("user_id", res.user_id); // get title
      values.put("term_id",res.term_id);
      values.put("term_desc", res.term_desc); // get title
      values.put("course_code",res.course_code);
      values.put("course_name", res.course); // get title
      values.put("seventh_degree", res.seventh_degree); // get title
      values.put("twelves",res.twelves_degree); 
      values.put("semi_degree", res.semi_degree); // get title
      values.put("grade_text",res.grade_degree);
      values.put("hours", res.hours); // get title

      

      
      int j=  (int) db.insert("results", // table
              null, //nullColumnHack
              values); // key/value -> keys = column names/ values = column values

      // 4. close
      db.close(); 
      return j;
}
	  
   return 0;
  }
  public int setLinks(String links,String name)
  {
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
      // 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("links", links); // get title
 // get author

      // 3. updating row
      int i = db.update("accounts", //table
              values, // column/value
              "account = ? ", // selections
              new String[] { String.valueOf(name) }); //selection args

      // 4. close
      db.close();

      return i;
  }
  
  public int setCounts(String counts,String name)
  {
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
      // 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("counts", counts); // get title
 // get author

      // 3. updating row
      int i = db.update("accounts", //table
              values, // column/value
              "account = ? ", // selections
              new String[] { String.valueOf(name) }); //selection args

      // 4. close
      db.close();

      return i;
  }
  public int setresults(String morasalat,String name)
  {
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
      // 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("results", morasalat); // get title
 // get author

      // 3. updating row
      int i = db.update("accounts", //table
              values, // column/value
              "account = ? ", // selections
              new String[] { String.valueOf(name) }); //selection args

      // 4. close
      db.close();

      return i;
  }
  
  public int setmorasalat(String morasalat,String name)
  {
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
      // 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("morasalat", morasalat); // get title
 // get author

      // 3. updating row
      int i = db.update("accounts", //table
              values, // column/value
              "account = ? ", // selections
              new String[] { String.valueOf(name) }); //selection args

      // 4. close
      db.close();

      return i;
  }
  public int getAccountsCount()
  {
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("accounts", // a. table
	            new String[]{"count(account)"}, // b. column names
	          null, // c. selections
	           null, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) 
      {
    	    db.close();
    	  return cursor.getInt(0);
      }
      db.close();
    	  return 0;
   
	  
  }
  public String getresults(String name)
  {
	  //ArrayList< String> msgs = new ArrayList< String>();
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("accounts", // a. table
	            new String[]{"results"}, // b. column names
	            "account = ? ", // selections
	              new String[] { String.valueOf(name)}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) 
      {
    	    db.close();
    	  return cursor.getString(0);
    	  
      }
      
      db.close();
      return"";
      
     
	  
  }
  public ArrayList<String> get_terms(String name)
  {
	  //ArrayList< String> msgs = new ArrayList< String>();
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query( true,"results", // a. table
	            new String[]{"term_desc"}, // b. column names
	            "user_id = ? ", // selections
	            new String[] { String.valueOf(name)}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
    ArrayList<String> terms = new ArrayList<String>();
      if (cursor.moveToFirst()) {
          do {
        	

              // Add book to books
        	  terms.add(cursor.getString(0));
          } while (cursor.moveToNext());
      }
      
      db.close();
      return terms;
      
     
	  
  }
  public String getmorasalat(String name)
  {
	  //ArrayList< String> msgs = new ArrayList< String>();
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("accounts", // a. table
	            new String[]{"morasalat"}, // b. column names
	            "account = ? ", // selections
	              new String[] { String.valueOf(name)}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) 
      {
    	    db.close();
    	  return cursor.getString(0);
    	  
      }
      
      db.close();
      return"";
      
     
	  
  }
  public String getcounts(String name)
  {
	 // ArrayList< String> msgs = new ArrayList< String>();
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("accounts", // a. table
	            new String[]{"counts"}, // b. column names
	            "account = ? ", // selections
	              new String[] { String.valueOf(name)}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) 
      {
    	    db.close();
    	  return cursor.getString(0);
      }
      db.close();
      return"0^0^0^0";
     
	  
  }
  public String getlinks(String name)
  {
	 // ArrayList< String> msgs = new ArrayList< String>();
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("accounts", // a. table
	            new String[]{"links"}, // b. column names
	            "account = ? ", // selections
	              new String[] { String.valueOf(name)}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) 
      {
    	    db.close();
    	  return cursor.getString(0);
      }
      db.close();
      return"";
     
	  
  }
  public ArrayList< String> getAccountscon()
  {
	  ArrayList< String> msgs = new ArrayList< String>();
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("accounts", // a. table
	            new String[]{"account","type"}, // b. column names
	          null, // c. selections
	           null, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) {
          do {
        	

              // Add book to books
        	  msgs.add(cursor.getString(1)+cursor.getString(0));
          } while (cursor.moveToNext());
      }
      db.close();
      return msgs;
	  
  }
  public void addAccount(String Account,String type)
  {
	  
	 
	  SQLiteDatabase db = this.getWritableDatabase();
	  ContentValues values = new ContentValues();
      values.put("type", type); // get title
      values.put("account",Account);
      values.put("links", ""); // get title
      values.put("counts","");
      values.put("morasalat", ""); // get title
      

      
      db.insert("accounts", // table
              null, //nullColumnHack
              values); // key/value -> keys = column names/ values = column values

      // 4. close
      db.close(); 
  }
  
  public void deleteAccount(String account) {
	  
      // 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();

      // 2. delete
      db.delete("accounts",
              "account = ?",
              new String[] { String.valueOf(account) });

      // 3. close
      db.close();

    

  }
  
public void deleteAllMessage(String username) {
	  
      // 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();

      // 2. delete
      db.delete("messages",
              "user_name = ?",
              new String[] { String.valueOf(username) });

      // 3. close
      db.close();
      

    

  }
  
  public void deleteMessage(int id) {
	  
      // 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();

      // 2. delete
      db.delete("messages",
              "id = ?",
              new String[] { String.valueOf(id) });

      // 3. close
      db.close();

    

  }
  
  
  public void addMessage(Message msg)
  {
	  
	  Log.d("addBook", msg.getMessage()); 
	  SQLiteDatabase db = this.getWritableDatabase();
	  ContentValues values = new ContentValues();
      values.put(KEY_message, msg.getMessage()); // get title
      values.put(KEY_message_desc,msg.getMessage_desc());
      values.put(KEY_message_title,msg.getMessage_title());
      values.put(KEY_Serial,msg.getSerial());
      values.put(KEY_Read,msg.getRead());
      values.put(KEY_DateTime,msg.getDateTime());
      values.put(KEY_link,msg.getLink());
      values.put(KEY_sys_code,msg.getSys_code());
      values.put(KEY_sys_name,msg.getSys_name());
      values.put(KEY_user_name,msg.getUser_name());
      values.put(KEY_user_type,msg.getUser_type());
      
      db.insert("messages", // table
              null, //nullColumnHack
              values); // key/value -> keys = column names/ values = column values

      // 4. close
      db.close(); 
  }
  
  public ArrayList<String> getSysNames(String user_name , String type)
  {
	  
	  ArrayList<String> msgs = new ArrayList<String>();
	  msgs.add("All");
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("messages", // a. table
	            new String[]{"sys_name","count(sys_name)"}, // b. column names
	            " user_name = ? and user_type = ?  ", // c. selections
	            new String[] { String.valueOf(user_name) ,String.valueOf(type)}, // d. selections args
	            "sys_name", // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) {
          do {
        	

              // Add book to books
        	  msgs.add(cursor.getString(0));
          } while (cursor.moveToNext());
      }
      
      db.close();
      return msgs;
	  
  }
  
  public ArrayList<String> getSysNamesCounts(String user_name , String type)
  {
	  ArrayList<String> msgs = new ArrayList<String>();
	  msgs.add(getmessagecount(user_name, type, "")+"");
	  SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor =
	            db.query("messages", // a. table
	            new String[]{"sys_name","count(sys_name)"}, // b. column names
	            " user_name = ? and user_type = ?  ", // c. selections
	            new String[] { String.valueOf(user_name) ,String.valueOf(type)}, // d. selections args
	            "sys_name", // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      // 3. go over each row, build book and add it to list
   
      if (cursor.moveToFirst()) {
          do {
        	

              // Add book to books
        	  msgs.add(cursor.getString(1));
          } while (cursor.moveToNext());
      }
      db.close();
      return msgs;
	  
  }
  
  public int getmessagecount(String user_name , String type,String filter) {
	 
	  
      // 1. build the query
     // String query = "SELECT  * FROM messages WHERE " + KEY_user_name + "=' " +user_name + "' and " + KEY_user_type + "='" + type+"'";

      // 2. get reference to writable DB
      Cursor cursor ;
      SQLiteDatabase db = this.getReadableDatabase();
      if(filter.equals(""))
      {
    	  		cursor =
	            db.query("messages", // a. table
	            new String[]{"count(user_name)"}, // b. column names
	            " user_name = ? and user_type = ? and Read = ?", // c. selections
	            new String[] { String.valueOf(user_name) ,String.valueOf(type),String.valueOf("0")}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      }
      else
      {
    	   cursor =
   	            db.query("messages", // a. table
   	            		new String[]{"count(user_name)"}, // b. column names
   	            " user_name = ? and user_type = ? and sys_name = ? and Read = ?", // c. selections
   	            new String[] { String.valueOf(user_name) ,String.valueOf(type),String.valueOf(filter),String.valueOf("0")}, // d. selections args
   	            null, // e. group by
   	            null, // f. having
   	            null, // g. order by
   	            null); // h. limit
      }
      // 3. go over each row, build book and add it to list
      Message msg = null;
      if (cursor.moveToFirst()) {
      db.close();
      return cursor.getInt(0);
      }
      
      db.close();
      return 0;
}
  
  public ArrayList<result_item> get_all_results(String term_id,String user_id)
  {
	  ArrayList<result_item> list_res = new ArrayList<result_item>();
	  Cursor cursor ;
      SQLiteDatabase db = this.getReadableDatabase();
      cursor =
	            db.query("results", // a. table
	            res_columns, // b. column names
	            " user_id = ? and term_desc = ?", // c. selections
	            new String[] { String.valueOf(user_id), String.valueOf(term_id) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
      
      result_item res = null;
      if (cursor.moveToFirst()) {
          do {
        	  res= new result_item(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        	  ;

              // Add book to books
        	  list_res.add(res);
          } while (cursor.moveToNext());
      }
      db.close();
      return list_res;
  
  }
  public ArrayList<Message> getall(String user_name , String type,String filter) {
	  ArrayList<Message> msgs = new ArrayList<Message>();
	  
      // 1. build the query
      String query = "SELECT  * FROM messages WHERE " + KEY_user_name + "=' " +user_name + "' and " + KEY_user_type + "='" + type+"'";

      // 2. get reference to writable DB
      Cursor cursor ;
      SQLiteDatabase db = this.getReadableDatabase();
      if(filter.equals(""))
      {
       cursor =
	            db.query("messages", // a. table
	            COLUMNS, // b. column names
	            " user_name = ? and user_type = ?", // c. selections
	            new String[] { String.valueOf(user_name) ,String.valueOf(type)}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            "DateTime   DESC", // g. order by
	            null); // h. limit
      }
      else
      {
    	   cursor =
   	            db.query("messages", // a. table
   	            COLUMNS, // b. column names
   	            " user_name = ? and user_type = ? and sys_name = ?", // c. selections
   	            new String[] { String.valueOf(user_name) ,String.valueOf(type),String.valueOf(filter)}, // d. selections args
   	            null, // e. group by
   	            null, // f. having
   	         "DateTime   DESC", // g. order by
   	            null); // h. limit
      }
      // 3. go over each row, build book and add it to list
      Message msg = null;
      if (cursor.moveToFirst()) {
          do {
        	 msg= new Message(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(4));
        	  ;

              // Add book to books
        	  msgs.add(msg);
          } while (cursor.moveToNext());
      }
      db.close();
      return msgs;
}
  
  public ArrayList<Message> getallSearch(String user_name , String type,String filter,String Search) {
	  ArrayList<Message> msgs = new ArrayList<Message>();
	  
      // 1. build the query
      String query = "SELECT  * FROM messages WHERE " + KEY_user_name + "=' " +user_name + "' and " + KEY_user_type + "='" + type+"'";

      // 2. get reference to writable DB
      Cursor cursor ;
      SQLiteDatabase db = this.getReadableDatabase();
      if(filter.equals(""))
      {
       cursor =
	            db.query("messages", // a. table
	            COLUMNS, // b. column names
	            " user_name = ? and user_type = ? and (message_title like ? or message_desc like ?)", // c. selections
	            new String[] { String.valueOf(user_name) ,String.valueOf(type),String.valueOf(Search),String.valueOf(Search)}, // d. selections args
	            null, // e. group by
	            null, // f. having
	            "DateTime   DESC", // g. order by
	            null); // h. limit
      }
      else
      {
    	   cursor =
   	            db.query("messages", // a. table
   	            COLUMNS, // b. column names
   	            " user_name = ? and user_type = ? and sys_name = ?  and (message_title like ? or message_desc like ?)", // c. selections
   	            new String[] { String.valueOf(user_name) ,String.valueOf(type),String.valueOf(filter),String.valueOf(Search),String.valueOf(Search)}, // d. selections args
   	            null, // e. group by
   	            null, // f. having
   	         "DateTime   DESC", // g. order by
   	            null); // h. limit
      }
      // 3. go over each row, build book and add it to list
      Message msg = null;
      if (cursor.moveToFirst()) {
          do {
        	 msg= new Message(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(4));
        	  ;

              // Add book to books
        	  msgs.add(msg);
          } while (cursor.moveToNext());
      }
      db.close();
      return msgs;
}
  public int markAsRead(int Serial)
  {
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
      // 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("Read", "1"); // get title
 // get author

      // 3. updating row
      int i = db.update("messages", //table
              values, // column/value
              "id = ? ", // selections
              new String[] { String.valueOf(Serial) }); //selection args

      // 4. close
      db.close();

      return i;
  }
  public int markAsUnRead(int Serial)
  {
	  
	  SQLiteDatabase db = this.getWritableDatabase();
	  
      // 2. create ContentValues to add key "column"/value
      ContentValues values = new ContentValues();
      values.put("Read", "0"); // get title
 // get author

      // 3. updating row
      int i = db.update("messages", //table
              values, // column/value
              "id = ? ", // selections
              new String[] { String.valueOf(Serial) }); //selection args

      // 4. close
      db.close();

      return i;
  }
  public Message getMessage(String serial)
  {
	// 1. get reference to readable DB
	    SQLiteDatabase db = this.getReadableDatabase();

	    // 2. build query
	    Cursor cursor =
	            db.query("messages", // a. table
	            COLUMNS, // b. column names
	            " Serial = ?", // c. selections
	            new String[] { String.valueOf(serial) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
	 
	    // 3. if we got results get the first one
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	  Message msg= new Message(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(4));
	    db.close();
	  return msg;
  }
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  
//  onCreate(db);
	//  db.execSQL("ALTER TABLE accounts ADD COLUMN results TEXT");
	  String Create_result_table = "CREATE TABLE results ( " +
              "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "user_id TEXT, "+
              "term_id TEXT, "+
              "term_desc TEXT, "+
              "course_code TEXT, "+
              "course_name TEXT, "+
              "seventh_degree TEXT, "+
              "twelves TEXT, "+
              "semi_degree TEXT, "+
              "grade_text TEXT, "+
              "hours TEXT )";
      // create books table
	 // database.execSQL(Create_message_table);
	  db.execSQL(Create_result_table);
  }

} 