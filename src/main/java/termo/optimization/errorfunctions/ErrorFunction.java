package termo.optimization.errorfunctions;

/**
 *
 * @author Hugo
 */
public interface ErrorFunction extends Parameterized{

        //to class function
    double getParameter(int index);

    int numberOfParameters();

    //to class function
    void setParameter(double value, int index);

    //to class function
    double error();

    
    
}
