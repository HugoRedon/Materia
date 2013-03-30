
package termo.binaryParameter;

import java.util.ArrayList;
import java.util.HashMap;
import termo.component.Component;



/**
 *
 * @author Hugo Redon Rivera
 */
public class BinaryInteractionParameter {
      
  private HashMap<ArrayList<Component>,Double> param ;
//    private boolean symmetric;
    //private HashMap<ArrayList<Component>,Double> k21Value;

    public BinaryInteractionParameter() {
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
//        if(isSymmetric){
//            components.clear();
//            components.add(component2);
//            components.add(component1);
//            kValues.put(components, value);
//        }
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
