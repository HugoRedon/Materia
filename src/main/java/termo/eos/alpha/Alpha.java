package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class Alpha {
    
    private String name ;
    private String equation;
    
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
    public  abstract double alpha(double temperature,Component component);
    
    public abstract double TempOverAlphaTimesDerivativeAlphaRespectTemperature(double temperature,Component component);
  
    @Override 
    public String toString(){
        return name;
    }

    /**
     * @return the equation
     */
    public String getEquation() {
        return equation;
    }

    /**
     * @param equation the equation to set
     */
    public void setEquation(String equation) {
        this.equation = equation;
    }



   
}
