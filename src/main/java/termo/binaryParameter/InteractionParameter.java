package termo.binaryParameter;

import java.util.HashMap;
import termo.component.Component;

/**
 *
 * @author
 * Hugo
 */
public class InteractionParameter {
    
  private HashMap<String,Double> param = new HashMap();;
  private boolean symmetric;
    //private HashMap<ArrayList<Component>,Double> k21Value;

    public InteractionParameter(boolean isSymmetric) {
        symmetric = isSymmetric;
    }
    public InteractionParameter(){
	
    }
  
   
    public double  getValue(Component component1,Component component2){
         
        String key = keyFor(component1, component2);
        
        if(param.containsKey(key)){
            return param.get(key);
        }else{
            return 0;
        }
    }
    
    public String keyFor(Component component1,Component component2){
        return "("+component1.getName() +","+ component2.getName()+")";
    }
    
    public void setValue(Component component1,Component component2,double value){
        
        String key = keyFor(component1, component2);
        param.put(key, value);
         
//         this.symmetric = isSymmetric;
	  
        if(symmetric){
            
          String simetricKey = keyFor(component2, component1);
            param.put(simetricKey, value);
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
