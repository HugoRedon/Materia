package termo.cp;

/**
 *
 * @author Hugo Redon Rivera
 */
public interface  CpEquation {
    
    public double cp(double temperature);
    public double Enthalpy(double temperature,double referenceTemperature, double enthalpyReference);
    public double idealGasEntropy(double temperature, double referenceTemeprature,double pressure, double referencePressure,  double entropyReference);


}
