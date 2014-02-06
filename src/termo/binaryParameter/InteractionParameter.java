/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.binaryParameter;

import java.util.ArrayList;
import java.util.HashMap;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class InteractionParameter {
    
  private HashMap<ArrayList<PureSubstance>,Double> param ;
//    private boolean symmetric;
    //private HashMap<ArrayList<Component>,Double> k21Value;

    public InteractionParameter() {
        param = new HashMap();
    }
  
   
    public double  getValue(PureSubstance component1,PureSubstance component2){
         ArrayList<PureSubstance> components = new ArrayList<>();
        components.add(component1);
        components.add(component2);
        
        if(param.containsKey(components)){
            return param.get(components);
        }else{
            return 0;
        }
    }
    
    public void setValue(PureSubstance component1,PureSubstance component2,double value,boolean isSymmetric){
        
        ArrayList<PureSubstance> components = new ArrayList<>();
        components.add(component1);
        components.add(component2);
        
        //What the fuck!!!
         param.put((ArrayList<PureSubstance>)components.clone(), value);
         
//         this.symmetric = isSymmetric;
	  ArrayList<PureSubstance> components1 = new ArrayList<>();
        if(isSymmetric){
            
            components1.add(component2);
            components1.add(component1);
            param.put((ArrayList<PureSubstance>)components1.clone(), value);
        }
    }

//    /**
//     * @return the symmetric
//     */
//    public boolean isSymmetric() {
//        return symmetric;
//    }
//
//    /**
//     * @param symmetric the symmetric to set
//     */
//    public void setSymmetric(boolean symmetric) {
//        this.symmetric = symmetric;
//    }

}
