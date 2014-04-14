package termo.binaryParameter;

import java.util.ArrayList;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class WilsonParameters extends InteractionParameter{
     private InteractionParameter aValues = new InteractionParameter();
     private InteractionParameter bValues = new InteractionParameter();
     
    public double get_a(Component ci,Component cj){
        return aValues.getValue(ci, cj);
    }
    public void set_a(Component ci,Component cj,double value){
        aValues.setValue(ci,cj, value);
    }
    public double get_b(Component ci,Component cj){  
        return bValues.getValue(ci, cj);
    }
      public void set_b(Component ci,Component cj,double value){
        bValues.setValue(ci, cj, value);
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
