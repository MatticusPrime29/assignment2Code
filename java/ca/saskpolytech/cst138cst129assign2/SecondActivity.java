package ca.saskpolytech.cst138cst129assign2;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cst129 and cst138 on 5/16/2018.
 */

public class SecondActivity extends AppCompatActivity
{
    //Global attributes
    ListView lvGrades;
    EditText etMark;
    EditText etWeight;
    TextView tvWeight;
    TextView tvCourseCode;
    Cursor cursor;
    EvaluationHelper dbEvaluation;
    CourseHelper dbCourse;
    EditText etCode;
    EvaluationAdapter adapter;
    Evaluation evalCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Assigns the databases
        dbEvaluation = new EvaluationHelper(this);
        dbCourse = new CourseHelper(this);

        assignVariables();

        tvCourseCode.setText(etCode.getText().toString());

        refresh();

        lvGrades.setOnItemClickListener((parent, view, position, id) -> {

            evalCurrent = (Evaluation) adapter.getItem(position);

            etMark.setText(evalCurrent.mark);
            etWeight.setText(evalCurrent.weight);

        });
    }

    //Method to refresh the data for each evaluation and course
    public void refresh()
    {
        dbEvaluation.open();

        //Grabs the evaluations based on the course code
        Evaluation evaluationFinal = dbEvaluation.getEvaluation(tvCourseCode.toString());
        Evaluation evaluationMidterm = dbEvaluation.getEvaluation(tvCourseCode.toString());
        Evaluation evaluationA1 = dbEvaluation.getEvaluation(tvCourseCode.toString());
        Evaluation evaluationA2 = dbEvaluation.getEvaluation(tvCourseCode.toString());
        Evaluation evaluationA3 = dbEvaluation.getEvaluation(tvCourseCode.toString());
        Evaluation evaluationA4 = dbEvaluation.getEvaluation(tvCourseCode.toString());


        //Array list that will contain evaluations for the list view
        ArrayList<Evaluation> arrAssign = new ArrayList<>();

        arrAssign.add(evaluationFinal);
        arrAssign.add(evaluationMidterm);
        arrAssign.add(evaluationA1);
        arrAssign.add(evaluationA2);
        arrAssign.add(evaluationA3);
        arrAssign.add(evaluationA4);

        adapter = new EvaluationAdapter(this,arrAssign);

        //Link adapter
        lvGrades.setAdapter(adapter);

        dbEvaluation.close();
    }

    //MEthod called when the save button is called
    public void btnGradeSaveListener(View v)
    {

        dbEvaluation.open();
        dbCourse.open();

        dbEvaluation.updateEvaluation(evalCurrent);

        Course course = dbCourse.getCourse(tvCourseCode.toString());
        dbCourse.updateCourse(course);

        dbEvaluation.close();
        dbCourse.close();
        refresh();
    }

    //Declares the variables initializes them
    private void assignVariables()
    {
        lvGrades = (ListView) findViewById(R.id.lvGrades);
        etMark = (EditText) findViewById(R.id.etMark);
        etWeight = (EditText) findViewById(R.id.etWeight);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvCourseCode = (TextView) findViewById(R.id.tvCourseCode);
        etCode = (EditText) findViewById(R.id.etCode);
    }
}
