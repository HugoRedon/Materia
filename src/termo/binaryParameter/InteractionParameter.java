/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.binaryParameter;

import java.util.ArrayList;
import java.util.HashMap;
import termo.component.Component;
import termo.substance.PureSubstance;

/**
 *
 * @author
 * Hugo
 */
public class InteractionParameter {
    
  private HashMap<ArrayList<Component>,Double> param ;
//    private boolean symmetric;
    //private HashMap<ArrayList<Component>,Double> k21Value;

    public InteractionParameter() {
        param = new HashMap();
    }
  
   
    public double  getValue(Component component1,Component component2){
         ArrayList<Component> components = new ArrayList<>();
        components.add(component1);
        components.add(component2);
        
        if(param.containsKey(components)){
            return param.get(components);
        }else{
            return 0;
        }
    }
    
    public void setValue(Component component1,Component component2,double value,boolean isSymmetric){
        
        ArrayList<Component> components = new ArrayList<>();
        components.add(component1);
        components.add(component2);
        
        //What the fuck!!!
         param.put((ArrayList<Component>)components.clone(), value);
         
//         this.symmetric = isSymmetric;
	  ArrayList<Component> components1 = new ArrayList<>();
        if(isSymmetric){
            
            components1.add(component2);
            components1.add(component1);
            param.put((ArrayList<Component>)components1.clone(), value);
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
