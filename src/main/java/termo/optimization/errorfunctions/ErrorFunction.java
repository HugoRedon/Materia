package termo.optimization.errorfunctions;

import java.util.ArrayList;
import termo.data.Experimental;

/**
 *
 * @author Hugo
 */
public abstract class ErrorFunction implements Parameterized{

    
//    protected ArrayList<ErrorData> errorForEachExperimentalData = new ArrayList();
        //to class function
    abstract public double  getParameter(int index);

    abstract public int numberOfParameters();

    //to class function
    abstract public void setParameter(double value, int index);

    //to class function
    abstract public double error();
    
    abstract public void setExperimental(ArrayList<? extends Experimental> experimental);
      /**
     * @return the errorForEachExperimentalData
     */
//    public ArrayList<ErrorData> getErrorForEachExperimentalData() {
//       // if(experimental.size() != errorForEachExperimentalData.size()){
//            error();//para calcular por primera vez
//        //}
//        return errorForEachExperimentalData;
//        
//    }

    
    
}
