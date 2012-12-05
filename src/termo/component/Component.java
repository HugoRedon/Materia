package termo.component;

import java.util.HashMap;

/**
 *
 * @author Hugo Redon Rivera
 */
public class Component {
    
    private double acentricFactor;
    private double criticalPressure;
    private double criticalTemperature;    
    private double criticalVolume;
    
    private double prsvk1;
    
    private String name;
    private int id ;
    
    private HashMap<Component,Double> binaryParameter;
    
    
    public Component (){
        
    }
    /**
     * 
     * @param id
     * @param name
     * @param acentricFactor
     * @param criticalPressure atm
     * @param criticalTemperature K
     * @param criticalVolume L/mol
     */
    public Component(int id, String name,
            double acentricFactor, 
            double criticalPressure, double criticalTemperature,double criticalVolume){
      this.acentricFactor = acentricFactor;
      this.criticalPressure = criticalPressure;
      this.criticalTemperature = criticalTemperature;
      this.criticalVolume = criticalVolume;
      this.name = name;
      this.id = id;
    }
    
    public double binaryParameter(Component aComponent){
        
        return binaryParameter.get(aComponent);
       
    }
    
    public double getCriticalTemperature(){
        return this.criticalTemperature;
    }
   
    public double getCriticalPressure(){
        return this.criticalPressure;
    }

    /**
     * @param criticalTemperature the critical Temperature to set in [K]
     */
    public void setCriticalTemperature(double criticalTemperature) {
        this.criticalTemperature = criticalTemperature;
    }

    /**
     * @param criticalPressure the critical Pressure to set in [atm]
     */
    public void setCriticalPressure(double criticalPressure) {
        this.criticalPressure = criticalPressure;
    }

    /**
     * @return the criticalVolume in [L/mol]
     */
    public double getCriticalVolume() {
        return criticalVolume;
    }

    /**
     * @param criticalVolume the critical Volume to set in [L/mol]
     */
    public void setCriticalVolume(double criticalVolume) {
        this.criticalVolume = criticalVolume;
    }

    /**
     * @return the acentricFactor
     */
    public double getAcentricFactor() {
        return acentricFactor;
    }

    /**
     * @param acentricFactor the acentricFactor to set
     */
    public void setAcentricFactor(double acentricFactor) {
        this.acentricFactor = acentricFactor;
    }

    /**
     * @return The name of the component
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The component id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The component id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    @Override public String toString(){
        return this.name;
    }

    /**
     * @return The parameter of the Peng-Robinson-Stryjek-Vera equation of state as double
     */
    public double getPrsvk1() {
        return prsvk1;
    }

    /**
     * @param prsvk1 The parameter of the Peng-Robinson-Stryjek-Vera equation of state to set as double
     */
    public void setPrsvk1(double prsvk1) {
        this.prsvk1 = prsvk1;
            
    }
    
   
    public boolean equals(Component aComponent){ 
       return id == aComponent.id;
    }
   
    
}
