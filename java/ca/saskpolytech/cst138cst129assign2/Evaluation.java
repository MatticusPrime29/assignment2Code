package ca.saskpolytech.cst138cst129assign2;

/**
 * Created by cst129 and cst138 on 5/11/2018.
 */

public class Evaluation {

    //Attributes
    public long id;
    public String code;
    public int mark;
    public int weight;

    //Constructor
    public Evaluation (String code, int mark, int weight)
    {
        this.code = code;
        this.mark = mark;
        this.weight = weight;
        id = -1;
    }
}
