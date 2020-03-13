package ca.saskpolytech.cst138cst129assign2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * Created by cst129 and cst138
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Global Attributes
    private CourseHelper dbCourse;
    private EvaluationHelper dbEvaluation;
    private Cursor cursor;
    private Spinner spinnerCourse;
    private EditText etCode;
    private EditText etName;
    private RadioGroup rdgYear;
    private RadioButton optOne;
    private RadioButton optTwo;

    /**
     * Method that will be called on the creation of the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates a new instance of each database
        dbCourse = new CourseHelper(this);
        dbEvaluation = new EvaluationHelper(this);

        // assignes the global variables to the appropriate ID's in the interface
        assignVariables();

        //Used for receiving any new information needed for the list view rows
        refreshData();

        //Get the shared preferences stored
        SharedPreferences prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        String option = prefs.getString("year", "One");

        //Check if its one or two and check the box accordingly
        if(option.matches("One"))
        {
            rdgYear.check(R.id.optOne);
        }
        else
        {
            rdgYear.check(R.id.optTwo);
        }


        //Spinner object event listener
        spinnerCourse.setOnItemSelectedListener(this);
    }

    /**
     * Method to assign the global variables to the appropriate ID's
     */
    private void assignVariables()
    {
        etCode = (EditText) findViewById(R.id.etCode);
        etName = (EditText) findViewById(R.id.etName);
        spinnerCourse  = (Spinner) findViewById(R.id.spinnerCourse);
        rdgYear = (RadioGroup) findViewById(R.id.rdgYear);
        optOne = (RadioButton) findViewById(R.id.optOne);
        optTwo = (RadioButton) findViewById(R.id.optTwo);
    }

    /**
     * Clears the text boxes and resets the radio group buttons to the last selected option
     */
    public void btnNewListener(View v)
    {
        // method to clear all the text boxes and reset the radio group
        clearTextBoxes();
    }

    /**
     * Method to call and display the second activity interface
     * @param v
     */
    public void btnEditListener(View v)
    {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }

    /**
     * Method that will save the current content in the text boxes and creates a new course and evaluation object
     * and saves it into the database
     * @param v
     */
    public void btnSaveListener(View v)
    {
        //open the database
        dbCourse.open();
        dbEvaluation.open();

        //get the attributes in each text field
        String name = etName.getText().toString();
        String code = etCode.getText().toString();
        String year = rdgYear.getCheckedRadioButtonId() == optOne.getId() ? "One" : "Two";


        //Creates a new course object and populates its attributes with what is in the interface texts fields
        Course course = new Course(name,code,year);

        Evaluation evaluationFinal = course.getEvaluationFinal();
        Evaluation evaluationMidterm = course.getEvaluationMidterm();
        Evaluation evaluationA1 = course.getEvaluationA1();
        Evaluation evaluationA2= course.getEvaluationA2();
        Evaluation evaluationA3 = course.getEvaluationA3();
        Evaluation evaluationA4 = course.getEvaluationA4();

        //adds the course into the database
        dbCourse.createCourse(course);
        //adds the evaulation (grades) into the evaluation (grades) database
        dbEvaluation.createEvaluation(evaluationFinal);
        dbEvaluation.createEvaluation(evaluationMidterm);
        dbEvaluation.createEvaluation(evaluationA1);
        dbEvaluation.createEvaluation(evaluationA2);
        dbEvaluation.createEvaluation(evaluationA3);
        dbEvaluation.createEvaluation(evaluationA4);

        //closes the database after conpletion
        dbCourse.close();
        dbEvaluation.close();

        //method called that refreshes the data with the updated information
        refreshData();

    }

    /**
     * Method called for saving a preference when a new course is added
     */
    private void preference()
    {
        SharedPreferences prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        String year = rdgYear.getCheckedRadioButtonId() == optOne.getId() ? "One" : "Two";
        editor.putString("year", year);

        editor.commit();
    }

    /**
     * method that will be called once the delete button is pressed
     * @param v
     */
    public void btnDeleteListener(View v)
    {
        //opens database
        dbCourse.open();
        dbEvaluation.open();

        String name = etName.getText().toString();
        String code = etCode.getText().toString();
        String year = rdgYear.getCheckedRadioButtonId() == optOne.getId() ? "One" : "Two";

        Course course = new Course(name,code,year);

        Evaluation evaluationFinal = course.getEvaluationFinal();
        Evaluation evaluationMidterm = course.getEvaluationMidterm();
        Evaluation evaluationA1 = course.getEvaluationA1();
        Evaluation evaluationA2= course.getEvaluationA2();
        Evaluation evaluationA3 = course.getEvaluationA3();
        Evaluation evaluationA4 = course.getEvaluationA4();

        //Calls the deleteCourse method specified in the CourseHelper class
        dbCourse.deleteCourse(course);

        //Calls the deleteEvaluation (Grades) method specified in the EvaluationHelper class
        dbEvaluation.deleteEvaluation(evaluationFinal);
        dbEvaluation.deleteEvaluation(evaluationMidterm);
        dbEvaluation.deleteEvaluation(evaluationA1);
        dbEvaluation.deleteEvaluation(evaluationA2);
        dbEvaluation.deleteEvaluation(evaluationA3);
        dbEvaluation.deleteEvaluation(evaluationA4);

        //closes the database
        dbCourse.close();
        dbEvaluation.close();

        //method that will refresh the data
        refreshData();
    }

    /**
     * Method that creates an adapter from all of the rows in the database and then populates the
     * spinner with the data.
     */
    public void refreshData()
    {
        dbCourse.open();

        cursor = dbCourse.getAllCourses();


        // Define which fields to display (in this case just Date),
        // and which text view ids from the layout to show them in (again just 1 this time)
        String[] cols = new String[] {dbCourse.CODE, dbCourse.AVERAGE}; //Shows code and average for the course
        int[] views = new int[] {android.R.id.text1, android.R.id.text2}; // Built-in TextView inside the simple list item layout

        // Define the adapter, indicating the layout to use, the cursor, and which fields to show
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, cols, views);

        // Link adapter to spinner
        spinnerCourse.setAdapter(adapter);

        //close the database
        dbCourse.close();
    }

    /**
     * Helper method for the spinner listener that sets the text based on the position of the spinner
     * item selected
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        etCode.setText(cursor.getString(1));
        etName.setText(cursor.getString(2));
        rdgYear.check(cursor.getString(3).equals("One") ? R.id.optOne : R.id.optTwo);

        //Called to set a preference
        preference();
    }

    /**
     * Will be called if nothing in the spinner is selected on run
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        Toast.makeText(this,"Nothing Selected", Toast.LENGTH_SHORT).show();
    }

    //helper method to clear the text boxes
    private void clearTextBoxes()
    {
        etCode.setText("");
        etName.setText("");
        rdgYear.clearCheck();
    }
}
