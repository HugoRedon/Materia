package termo.component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Hugo Redon Rivera
 */
public class WilsonParameters extends BinaryInteractionParameters{
     private HashMap<ArrayList<Component>,Double> aValues = new HashMap<>();
     private HashMap<ArrayList<Component>,Double> bValues = new HashMap<>();
     
    public double get_a(Component ci,Component cj){
        ArrayList<Component>list = new ArrayList<>();
        list.add(ci);
        list.add(cj);
        
        return aValues.get(list);
    }
    public void set_a(Component ci,Component cj,double value){
        aValues.put(toArray(ci,cj), value);
    }
    public double get_b(Component ci,Component cj){  
        return bValues.get(toArray(ci,cj));
    }
      public void set_b(Component ci,Component cj,double value){
        bValues.put(toArray(ci,cj), value);
    }
    
    private ArrayList<Component> toArray(Component ci,Component cj){
        ArrayList<Component> list = new ArrayList<>();
        list.add(ci);
        list.add(cj);
        
        return list;
    }
    
    public double Aij(Component ci,Component cj,double T){
        double a = get_a(ci,cj);
        double b = get_b(ci,cj);      
        
        // check
//        double Vj = cj.getWilsonMolarVolume();
//        double Vi = ci.getWilsonMolarVolume();
        double Vj = cj.getLiquidMolarVolumeat298_15K();
        double Vi = ci.getLiquidMolarVolumeat298_15K();
        
        return (Vj / Vi) * Math.exp(- (a + b * T)  /(termo.Constants.R * T));       
    }
    
    
}
