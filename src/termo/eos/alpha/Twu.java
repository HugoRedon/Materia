package termo.eos.alpha;

import java.util.HashMap;
import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public final class Twu extends Alpha {
  
    HashMap<String,HashMap<String,Double>> belowTc ;
    HashMap<String,HashMap<String,Double>> aboveTc;
  
//    private double[][] parametersBelowTc = {
//        /*for alpha 0*/{
//            /*L*/,
//            /*M*/,
//            /*N*/},
//        /*for alpha1*/{
//            /*L*/,
//            /*M*/,
//            /*N*/}
//    };
//   private double[][] parametersAboveTc = {
//        /*for alpha 0*/{
//            /*L*/0.358826d,
//            /*M*/4.23478d,
//            /*N*/-0.2d},
//        /*for alpha1*/{
//            /*L*/0.0206444d,
//            /*M*/1.22942d,
//            /*N*/-8.0d}
//    };
    public Twu(){
       
        setName("Twu");
        
       
        belowTc = new HashMap<>();
        belowTc.put("alpha0", new HashMap(){             
            {        
                put("L", 0.196545d);
                put("M",0.906437d);
                put("N",1.2651d);
            }
        });
        belowTc.put("alpha1", new HashMap(){{
            put("L", 0.704001d);
            put("M",0.790407d); 
            put("N",1.2651d);
        }});
        
        aboveTc = new HashMap<>();
        aboveTc.put("alpha0",new HashMap(){{
            put("L",0.358826d );
            put("M",4.23478d); 
            put("N",-0.2d);          
        }});
        aboveTc.put("alpha1",new HashMap(){{
            put("L", 0.0206444d);
            put("M",1.22942d); 
            put("N",-8.0d);   
            
        }});
                
                
                
//            alpha0.put("L", 0.196545d);
//            alpha0.put("M",0.906437d);    
//            alpha0.put("N",1.2651d);
//        HashMap<String,Double> alpha1 = new HashMap<>();
//            alpha0.put("L", 0.704001d);
//            alpha0.put("M",0.790407d);    
//            alpha0.put("N",2.13076);
//      
//        belowTc = new HashMap<>();
//            belowTc.put("alpha0",alpha0);
//            belowTc.put("alpha1", alpha1);
            
         
    }
    
  
    @Override
    public double alpha(double temperature,Component component) {
        double acentricFactor = component.getAcentricFactor(); 
        double criticalTemperature = component.getCriticalTemperature();      
        double reducedTemperature = temperature / criticalTemperature;
        
        double alpha0;
        double alpha1;
        
       
        
        if(reducedTemperature <= 1){
            alpha0 = alpha(reducedTemperature,belowTc.get("alpha0"));
            alpha1 = alpha(reducedTemperature,belowTc.get("alpha1"));
         
            return alpha0 + acentricFactor * (alpha1 - alpha0);
        }else{
           alpha0 = alpha(reducedTemperature,aboveTc.get("alpha0"));
           alpha1 = alpha(reducedTemperature,aboveTc.get("alpha1"));
            
           return alpha0 + acentricFactor * (alpha1 - alpha0);
        }
    
    }
    
    private double alpha(double tr, HashMap<String,Double> lmn){
        double L = lmn.get("L");
        double M = lmn.get("M");
        double N = lmn.get("N");
        
        return  Math.pow(tr,N * (M-1) * Math.exp(L * (1- Math.pow(tr, N * M))));
  
    }

 

   
  @Override
    public double TOverAlphaTimesDalpha(double temperature,Component component){
    
       throw new UnsupportedOperationException("Not ready");
    }
  

 
    
}
