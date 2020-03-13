package ca.saskpolytech.cst138cst129assign2;

/**
 * Created by cst129 on 5/11/2018.
 */

public class Course {

    //Attributes
    public long id;
    public String code;
    public String name;
    public String year;
    public double average;
    private Evaluation evaluationFinal;
    private Evaluation evaluationMidterm;
    private Evaluation evaluationA1;
    private Evaluation evaluationA2;
    private Evaluation evaluationA3;
    private Evaluation evaluationA4;

    /**
     * Constructor method
     * @param code
     * @param name
     * @param year
     */
    public Course (String code, String name, String year)
    {
        this.code = code;
        this.name = name;
        this.year = year;
        this.id = -1;
        this.evaluationFinal = new Evaluation(code,0,0);
        this.evaluationMidterm = new Evaluation(code,0,0);
        this.evaluationA1 = new Evaluation(code,0,0);
        this.evaluationA2 = new Evaluation(code,0,0);
        this.evaluationA3 = new Evaluation(code,0,0);
        this.evaluationA4 = new Evaluation(code,0,0);
        this.average = getAverage();
    }

    //Getters for the evaluations created for the Course
    public Evaluation getEvaluationFinal()
    {
        return this.evaluationFinal;
    }
    public Evaluation getEvaluationMidterm()
    {
        return this.evaluationMidterm;
    }
    public Evaluation getEvaluationA1()
    {
        return this.evaluationA1;
    }
    public Evaluation getEvaluationA2()
    {
        return this.evaluationA2;
    }
    public Evaluation getEvaluationA3()
    {
        return this.evaluationA3;
    }
    public Evaluation getEvaluationA4()
    {
        return this.evaluationA4;
    }

    //Calculator for getting the average from each evaluation
    private double getAverage()
    {
        double dReturn;

        int f = (getEvaluationFinal().mark) * ((getEvaluationFinal().weight / 100));
        int m = (getEvaluationMidterm().mark) * ((getEvaluationMidterm().weight / 100));
        int a1 = (getEvaluationA1().mark) * ((getEvaluationA1().weight / 100));
        int a2 = (getEvaluationA2().mark) * ((getEvaluationA2().weight / 100));
        int a3 = (getEvaluationA3().mark) * ((getEvaluationA3().weight / 100));
        int a4 = (getEvaluationA4().mark) * ((getEvaluationA4().weight / 100));

        dReturn = f + m + a1 + a2 + a3 + a4;

        return dReturn;
    }
}
