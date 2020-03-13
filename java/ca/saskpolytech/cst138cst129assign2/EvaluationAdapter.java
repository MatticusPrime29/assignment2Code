package ca.saskpolytech.cst138cst129assign2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by cst129 and cst138 on 5/17/2018.
 */

public class EvaluationAdapter extends ArrayAdapter{


    //Constructor method
    public EvaluationAdapter(@NonNull Context context, ArrayList<Evaluation> grades)
    {
        super(context, R.layout.activity_grade_view, grades);
    }

    /**
     * Creates the view for the list view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Evaluation evaluationFinal = (Evaluation) getItem(position);
        Evaluation evaluationMidterm = (Evaluation) getItem(position);
        Evaluation evaluationA1 = (Evaluation) getItem(position);
        Evaluation evaluationA2 = (Evaluation) getItem(position);
        Evaluation evaluationA3 = (Evaluation) getItem(position);
        Evaluation evaluationA4 = (Evaluation) getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_grade_view,parent,false);
        }


        //grabs the textViews from the layout
        TextView tvFW = (TextView) convertView.findViewById(R.id.tvFW);
        TextView tvFM = (TextView) convertView.findViewById(R.id.tvFM);
        TextView tvMW = (TextView) convertView.findViewById(R.id.tvMW);
        TextView tvMM = (TextView) convertView.findViewById(R.id.tvMM);
        TextView tvA1W = (TextView) convertView.findViewById(R.id.tvA1W);
        TextView tvA1M = (TextView) convertView.findViewById(R.id.tvA1M);
        TextView tvA2W = (TextView) convertView.findViewById(R.id.tvA2W);
        TextView tvA2M = (TextView) convertView.findViewById(R.id.tvA2M);
        TextView tvA3W = (TextView) convertView.findViewById(R.id.tvA3W);
        TextView tvA3M = (TextView) convertView.findViewById(R.id.tvA3M);
        TextView tvA4W = (TextView) convertView.findViewById(R.id.tvA4W);
        TextView tvA4M = (TextView) convertView.findViewById(R.id.tvA4M);

        //Sets the text from each evaluation
        tvFW.setText(evaluationFinal.weight);
        tvFM.setText(evaluationFinal.mark);
        tvMW.setText(evaluationMidterm.weight);
        tvMM.setText(evaluationMidterm.mark);
        tvA1W.setText(evaluationA1.weight);
        tvA1M.setText(evaluationA1.mark);
        tvA2W.setText(evaluationA2.weight);
        tvA2M.setText(evaluationA2.mark);
        tvA3W.setText(evaluationA3.weight);
        tvA3M.setText(evaluationA3.mark);
        tvA4W.setText(evaluationA4.weight);
        tvA4M.setText(evaluationA4.mark);

        return convertView;

    }


}
