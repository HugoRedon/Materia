package termo.eos.alpha;

import termo.component.Component;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class Alpha {
    
    private String name ;
    protected String equation;
    
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

    public abstract int numberOfParameters() ;

    public abstract void setAlphaParameterA(double paramValue, Component component) ;
    public abstract double getAlphaParameterA(Component component);
    
    public abstract void setAlphaParameterB(double paramValue, Component component) ;
    public abstract double getAlphaParameterB( Component component);
    
    public abstract void setAlphaParameterC(double paramValue, Component component) ;
    public abstract double getAlphaParameterC(Component component);


   
}
