package ca.saskpolytech.cst138cst129assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cst138 and cst129 on 5/11/2018.
 */

public class CourseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Assign2Course.db";
    private static final int DB_VERSION = 1;

    //table constants
    public static final String TABLE_NAME = "Course";
    public static final String ID = "_id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String YEAR = "year";
    public static final String AVERAGE = "average";

    public SQLiteDatabase sqlDB; // reference to the database.


    public CourseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public void open() throws SQLException
    {
        //set the instance of sqlDB to a connection to the database
        sqlDB = this.getWritableDatabase();
    }

    public void close()
    {
        sqlDB.close(); //close the connection to the DB
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sSQL = "CREATE TABLE " + TABLE_NAME
                + " (" + ID + " integer primary key autoincrement, "
                + CODE + " text not null, "
                + NAME + " text not null, "
                + YEAR + " text not null, "
                + AVERAGE + " double not null);";

        //run statement against the DB
        db.execSQL(sSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //delete the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //recreate everything
        onCreate(db);
    }

    public long createCourse(Course course)
    {
        //save a new record to the DB
        // create a content values list to store col/val pairs
        ContentValues cvs = new ContentValues();

        //add values for each column from the object passed in.
        cvs.put(CODE, course.code);
        cvs.put(NAME, course.name);
        cvs.put(YEAR, course.year);
        cvs.put(AVERAGE, course.average);

        long id = sqlDB.insert(TABLE_NAME, null, cvs);
        course.id = id; //Auto incrementing id
        return id;
    }

    public boolean updateCourse(Course course)
    {
        // Check to see if this purchase exists (could do a check against the DB)
        if (course.id < 0)
        {
            return false;
        }
        else // Save the record's changes
        {
            //Create a content value list to store col/val pairs
            ContentValues cvs = new ContentValues();

            // Add values for each column from the object passed in
            cvs.put(CODE, course.code);
            cvs.put(NAME, course.name);
            cvs.put(YEAR, course.year);
            cvs.put(AVERAGE, course.average);

            return (sqlDB.update(TABLE_NAME, cvs, ID + " = " + course.id, null)) > 0;
        }
    }

    public boolean deleteCourse(Course course)
    {
        // Delete will return the # rows deleted, compare to 0 for true/false something was deleted
        return sqlDB.delete(TABLE_NAME, ID + " = " + course.id, null) > 0;
    }

    public Course getCourse(String code) throws SQLException
    {
        // Select the fields to return
        String[] sFields = new String[] {ID, CODE, NAME, YEAR, AVERAGE};
        Cursor cursor = sqlDB.query(TABLE_NAME, sFields, CODE + " = " + code, null, null, null, null);
        // Go to the first position in the cursor, then return a FuelPurchase object instead, populated from the cursor
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Course course = new Course(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );
        return course;
    }

    public Cursor getAllCourses()
    {
        // Select the fields to return
        String[] sFields = new String[] {ID, CODE, NAME, YEAR, AVERAGE};
        // Call query and return the result cursor (nulls for options we aren't using)

        Cursor cursor = sqlDB.query(TABLE_NAME, sFields, null, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0)
        {
            return cursor;
        }

        return sqlDB.query(TABLE_NAME, sFields, null, null, null, null, null);
    }

//    public Cursor getAllCoursesUsingSQL()
//    {
//        // Can write "raw" SQL Selects if desired as well
//        String sql = "SELECT * FROM " + TABLE_NAME;
//        return sqlDB.rawQuery(sql,null);
//    }
}
