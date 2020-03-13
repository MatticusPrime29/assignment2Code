package ca.saskpolytech.cst138cst129assign2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cst129 and cst138
 */

public class EvaluationHelper extends SQLiteOpenHelper {


    //Final variables for database
    private static final String DB_NAME = "Assign2Evaluation.db";
    private static final int DB_VERSION = 1;

    //table constants
    public static final String TABLE_NAME = "Evaluation";
    public static final String ID = "_id";
    public static final String CODE = "code";
    public static final String MARK = "mark";
    public static final String WEIGHT = "weight";


    public SQLiteDatabase sqlDB; // reference to the database.



    public EvaluationHelper(Context context)
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
                + " (" + ID + " integer not null, "
                + CODE + " text not null, "
                + MARK + " integer not null, "
                + WEIGHT + " integer not null);";

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

    public boolean updateEvaluation(Evaluation evaluation)
    {
        // Check to see if this purchase exists (could do a check against the DB)
            ContentValues cvs = new ContentValues();

            // Add values for each column from the object passed in
            cvs.put(CODE, evaluation.code);
            cvs.put(MARK, evaluation.mark);
            cvs.put(WEIGHT, evaluation.weight);

            return (sqlDB.update(TABLE_NAME, cvs,  ID + " = " + evaluation.id, null)) > 0;

    }

    public long createEvaluation(Evaluation evaluation)
    {
        //save a new record to the DB
        // create a content values list to store col/val pairs
        ContentValues cvs = new ContentValues();

        //add values for each column from the object passed in.
        cvs.put(CODE, evaluation.code);
        cvs.put(MARK, evaluation.mark);
        cvs.put(WEIGHT, evaluation.weight);

        long id = sqlDB.insert(TABLE_NAME, null, cvs);
        evaluation.id = id;
        return id;

    }

    public boolean deleteEvaluation(Evaluation evaluation)
    {
        // Delete will return the # rows deleted, compare to 0 for true/false something was deleted
        return sqlDB.delete(TABLE_NAME, CODE + " = " + evaluation.code, null) > 0;
    }

    public Evaluation getEvaluation(String code) throws SQLException
    {
        // Select the fields to return
        String[] sFields = new String[] {ID, CODE, MARK, WEIGHT};
        Cursor cursor = sqlDB.query(TABLE_NAME, sFields, CODE + " = " + code, null, null, null, null);
        // Go to the first position in the cursor, then return a Evaluation object instead, populated from the cursor
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Evaluation evaluation = new Evaluation(
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
                );
        return evaluation;
    }
}
