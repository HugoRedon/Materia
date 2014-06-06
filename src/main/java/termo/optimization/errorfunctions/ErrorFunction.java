/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.optimization.errorfunctions;

import java.util.ArrayList;
import termo.data.ExperimentalData;

/**
 *
 * @author Hugo
 */
public interface ErrorFunction {

    //to class function
    double getParameter(int index);

    int numberOfParameters();

    //to class function
    void setParameter(double value, int index);

    //to class function
    double error();

    
    
}
