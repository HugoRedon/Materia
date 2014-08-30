package termo.activityModel;

import java.util.ArrayList;
import java.util.HashMap;

import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.component.Compound;
import termo.matter.Mixture;
import termo.matter.Substance;

/**
 *
 * @author Hugo Redon Rivera
 */
public class NRTLActivityModel extends ActivityModel{
    public NRTLActivityModel(){
        super.setName("NRTL");
    }
    
    @Override
    public double excessGibbsEnergy(Mixture mixture) {
        double gibbsExcess =0;
        ActivityModelBinaryParameter param = (ActivityModelBinaryParameter) mixture.getBinaryParameters();
        
        for(Substance ci: mixture.getPureSubstances()){
            double xi = ci.getMolarFraction();
            
            double numerator = 0;
            double denominator = 0;
            
            for ( Substance cj : mixture.getPureSubstances()){
                double xj = cj.getMolarFraction();
                double  tau = tau(cj.getComponent(), ci.getComponent(), param, mixture.getTemperature());// param.get_gji(cj,ci) / (Constants.R * temperature);
                double Gji = G(cj.getComponent(), ci.getComponent(), param, mixture.getTemperature());//= Math.exp(- param.getAlpha().getValue(cj,ci) * tau);
                
                numerator += xj * tau * Gji;
                denominator += xj * Gji;
            }
            
            gibbsExcess += xi * numerator / denominator;
        }

        return gibbsExcess *Constants.R * mixture.getTemperature();                
    }

    @Override
    public double activityCoefficient(
            //ArrayList<Component> components, 
            //ArrayList<Component> components, 
                        Substance cip, 
            Mixture mixture) {
        
	ArrayList<Compound> components = new ArrayList<>();
	HashMap<Compound,Double> fractions = new HashMap();
	for(Substance c: mixture.getPureSubstances()){
	    components.add(c.getComponent());
	    fractions.put(c.getComponent(), c.getMolarFraction());
	}
	
	Compound ci = cip.getComponent();
        double xj = 0;
        double tauij = 0;
        double gij = 0;
        
        double t0 = 0;
        ActivityModelBinaryParameter k = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
        double summa2 = summa2(ci, components, fractions, k, mixture.getTemperature());
        double summa1 = summa1(ci, components, fractions, k, mixture.getTemperature());
        
        double secondTerm = 0;

        for(Compound cj : components){
            xj = fractions.get(cj);
            gij = G(ci, cj, k, mixture.getTemperature());
            tauij = tau(ci, cj, k, mixture.getTemperature());
            
            double sum1kj = summa1(cj, components, fractions, k, mixture.getTemperature());
            double sum2mk = summa2(cj, components, fractions, k, mixture.getTemperature());
            
            secondTerm += (xj * gij / sum1kj )* (tauij - (sum2mk / sum1kj));
            
        }
        double firstTerm = summa2 / summa1;     
        double result= firstTerm + secondTerm;
	
	return Math.exp(result);
    }
    
    private double summa1 (Compound ci, ArrayList<Compound> components, HashMap<Compound, Double> fractions, ActivityModelBinaryParameter k, double temperature){
         double summa = 0;
         for(Compound cj: components){
                double xj = fractions.get(cj);
                summa += xj * G( cj, ci, k,temperature);
            }
        return summa;
    }
      private double summa2 (Compound ci, ArrayList<Compound> components, HashMap<Compound, Double> fractions, ActivityModelBinaryParameter k, double temperature){
         double summa = 0;
         for(Compound cj: components){
                double xj = fractions.get(cj);
                double tauji = tau(cj, ci, k, temperature);
                double gji = G(cj, ci, k, temperature);
                
                summa += xj * tauji * gji;
            }
        return summa;
    }
    
    
//    public double tau(Compound cj,Compound ci, ActivityModelBinaryParameter param,double temperature){
////        double gji = param.get_gji(cj, ci);
////        double gii = param.get_gji(ci, ci);
////        double delta = gji - gii;
////        
////        return delta/ (Constants.R * temperature);
//        
//        double aji = param.getA().getValue(cj, ci);
//        double bji = param.getB().getValue(cj, ci);
//        
//        return (aji + bji * temperature)/(Constants.R * temperature);
//    }
    public double G(Compound cj,Compound ci, ActivityModelBinaryParameter param,double temperature){
         double tau =tau(cj, ci, param, temperature);
        return Math.exp(- param.getAlpha().getValue(cj,ci) * tau);
    }

    @Override
    public double parcialExcessGibbsRespectTemperature(
            ArrayList<Compound> components, 
            HashMap<Compound, Double> fractions, 
            ActivityModelBinaryParameter param,
            double temperature) {
//        NRTLBinaryParameter param = (NRTLBinaryParameter)k;
        
        double T0 ;
        double T1;
        double B1 ;
        double A1;
        double A0;
        
      
        double xi ;
        
        double bji ;
        double alphaji ;
        double tauji ;
        double xj ;
        
        double t0;
        double t1;
        
          double result =0;
        
        for(Compound ci: components){
           xi = fractions.get(ci);
           
           T0 =0;
           T1 =0;
           B1 =0;
           A1 =0;
           A0 =0;
           
            for(Compound cj: components){
                bji = param.getB().getValue(cj, ci);
                alphaji = param.getAlpha().getValue(cj, ci);
                tauji = tau(cj, ci, param, temperature);
                xj = fractions.get(cj);
                
                t0 = xj* G(cj, ci, param, temperature);
                t1 = t0 * tauji;
                T0 += t0;
                T1 += t1;
                B1 += t0*bji/Constants.R;
                A1 += t1* alphaji *(tauji - (bji/Constants.R)) ;
                A0 += t0 * alphaji*(tauji - (bji/Constants.R)) ;
                
            }   
            
            
            result += xi* (((B1 + A1)/T0) - (T1*A0 / Math.pow(T0,2)));
        }
        
        return Constants.R * result;
    }

    @Override
    public int numberOfParameters() {
        return super.numberOfParameters()+ 1; 
    }

    @Override
    public double getParameter(Compound referenceComponent, Compound nonReferenceComponent, ActivityModelBinaryParameter params, int index) {
        int parametersNumber = super.numberOfParameters();
        if(index ==parametersNumber ){
            return params.getAlpha().getValue(referenceComponent, nonReferenceComponent);//simetrico
        }
//        else if(index == parametersNumber +1 ){
//            return params.getAlpha().getValue(nonReferenceComponent, referenceComponent);
//        }
        
        return super.getParameter(referenceComponent, nonReferenceComponent, params, index); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String getParameterName(int index) {
    	if(index == super.numberOfParameters()){
    		return "\\alpha";
    	}
    	return super.getParameterName(index);
    }

    @Override
    public void setParameter(double value, Compound referenceComponent, Compound nonReferenceComponent, ActivityModelBinaryParameter params, int index) {
         int parametersNumber = super.numberOfParameters();
        if(index ==parametersNumber ){
            params.getAlpha().setValue(referenceComponent, nonReferenceComponent,value);
        }
//            else if(index == parametersNumber +1 ){
//            params.getAlpha().setValue(nonReferenceComponent, referenceComponent,value);
//        }
        else{
            super.setParameter(value, referenceComponent, nonReferenceComponent, params, index); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    

    
    


}
