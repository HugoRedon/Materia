/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.equations;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import termo.component.Component;

/**
 *
 * @author Hugo
 */
@MappedSuperclass
public abstract class EqnVaporPressure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Component component;
    protected double A;
    protected double B;
    protected double C;
    
    private double minTemperature;
    private double maxTemperature;

    public abstract double vaporPressure(double temperature);
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the component
     */
    public Component getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    /**
     * @return the A
     */
    public double getA() {
        return A;
    }

    /**
     * @param A the A to set
     */
    public void setA(double A) {
        this.A = A;
    }

    /**
     * @return the B
     */
    public double getB() {
        return B;
    }

    /**
     * @param B the B to set
     */
    public void setB(double B) {
        this.B = B;
    }

    /**
     * @return the C
     */
    public double getC() {
        return C;
    }

    /**
     * @param C the C to set
     */
    public void setC(double C) {
        this.C = C;
    }

    /**
     * @return the minTemperature
     */
    public double getMinTemperature() {
        return minTemperature;
    }

    /**
     * @param minTemperature the minTemperature to set
     */
    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    /**
     * @return the maxTemperature
     */
    public double getMaxTemperature() {
        return maxTemperature;
    }

    /**
     * @param maxTemperature the maxTemperature to set
     */
    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
    
  
}
