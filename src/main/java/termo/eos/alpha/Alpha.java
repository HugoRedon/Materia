package termo.eos.alpha;

import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.equation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Alpha other = (Alpha) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.equation, other.equation)) {
            return false;
        }
        return true;
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
