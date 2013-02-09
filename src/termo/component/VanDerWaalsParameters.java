package termo.component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Hugo Redon Rivera
 */
public class VanDerWaalsParameters extends BinaryInteractionParameters{
    
  
  private HashMap<ArrayList<Component>,Double> kValues = new HashMap<>();
    private boolean symmetric;
    //private HashMap<ArrayList<Component>,Double> k21Value;
   
    public double  getValue(Component component1,Component component2){
         ArrayList<Component> components = new ArrayList<>();
        components.add(component1);
        components.add(component2);
        
        if(kValues.containsKey(components)){
            return kValues.get(components);
        }else{
            return 0;
        }
    }
    
    public void setValue(Component component1,Component component2,double value,boolean isSymmetric){
        
        ArrayList<Component> components = new ArrayList<>();
        components.add(component1);
        components.add(component2);
         kValues.put((ArrayList<Component>)components.clone(), value);
         
         this.symmetric = isSymmetric;
        if(isSymmetric){
            components.clear();
            components.add(component2);
            components.add(component1);
            kValues.put(components, value);
        }
    }

    /**
     * @return the symmetric
     */
    public boolean isSymmetric() {
        return symmetric;
    }

    /**
     * @param symmetric the symmetric to set
     */
    public void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;
    }
 
}
