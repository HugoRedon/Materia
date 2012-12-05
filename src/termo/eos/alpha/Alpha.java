package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class Alpha {
    
    private String name ;
//    private String eosName;
    
 
    
//    public void setEOSName(String anEOSName){
//        this.eosName = anEOSName;
//       
//    }
//    public String getEOSName(){
//        return this.eosName;
//    }
    
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    
    
  
    
 
    
    /**
     * 
     * @param temperature
     * @return 
     */
    public abstract double alpha(double temperature,Component component);
    
    public abstract double TOverAlphaTimesDalpha(double temperature,Component component);
  
    @Override 
    public String toString(){
        return name;
    }
 
}
