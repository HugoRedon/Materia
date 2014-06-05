
package termo.optimization;

import java.util.ArrayList;
import termo.component.Component;
import termo.data.ExperimentalData;
import termo.eos.alpha.Alpha;
import termo.matrix.Matrix;
import termo.matter.HeterogeneousSubstance;

/**
 *
 * @author Hugo
 */
public class AlphaOptimization {
    //fields
    private HeterogeneousSubstance substance;
    private ArrayList<ExperimentalData> experimental = new ArrayList();
    private double numericalDerivativeDelta = 0.0001;
    
//    private boolean fixParameterA;
//    private boolean fixParameterB;
//    private boolean fixParameterC;
//    
    private boolean[] fixParameters ;
    
    //end fields
    private boolean indeter;
    private boolean maxIterationsReached;
    private String message;
    
    
    private int iterations;
    private double tolerance = 1e-4;
    
    private ArrayList<Parameters_Error> convergenceHistory = new ArrayList();
    
    
    
 
    //constructores
    public AlphaOptimization(HeterogeneousSubstance substance){
        this.substance = substance;
        
        int numberOfParameters = substance.getVapor().getAlpha().numberOfParameters();
        fixParameters = new boolean[numberOfParameters];
    }
    
    public AlphaOptimization(HeterogeneousSubstance substance,ArrayList<ExperimentalData> experimental){
        this.substance = substance ; 
        this.experimental = experimental;
        int numberOfParameters = substance.getVapor().getAlpha().numberOfParameters();
        fixParameters = new boolean[numberOfParameters];
    }
    //end constructors
    
    public int fixedVariablesCount(){
        int result = 0;
        
        for(boolean fix : fixParameters){
            if(fix){
                result++;
            }
        }
//        if(fixParameterA){
//            result++;
//        }if(fixParameterB){
//            result++;
//        }if(fixParameterC){
//            result++;
//        }
        return result;
    }
    
    public int numberOfVariablesToOptimize(){
        Alpha alpha = substance.getVapor().getAlpha();
        
        int numberOfParameters = alpha.numberOfParameters();
        int fixedParameters = fixedVariablesCount();
        
        return numberOfParameters- fixedParameters;
    }
  
    
    
    
    
    public double[] initialValues(int numberOfVariablesToOptimize){
        
        double[] initialValues = new double[numberOfVariablesToOptimize];

        Component component = substance.getVapor().getComponent();
            Alpha alpha = substance.getVapor().getAlpha();

            for(int i = 0; i < numberOfVariablesToOptimize; i++){
                for(int j =i; j< numberOfVariablesToOptimize; j++){
                    if(!fixParameters[j]){
                        initialValues[i] =   alpha.getParameter( component, j);
                        break;
                    }
                }
        }
        
        
        
//        
//        if(numberOfVariablesToOptimize >=1){
//            if(!fixParameters[0]){
//                initialValues[0] = alpha.getParameter(component,0);
//            }else if(!fixParameters[1]){
//                initialValues[0] = alpha.getParameter(component,1);
//            }else if(!fixParameters[2]){
//                initialValues[0] = alpha.getParameter(component,2);
//            }
//        }
//        
//        if(numberOfVariablesToOptimize >=2){
//            if(!fixParameters[1]){
//                initialValues[1] = alpha.getParameter(component,1);
//            }else if(!fixParameters[2]){
//                initialValues[1] = alpha.getParameter(component,2);
//            }
//            
//        }
//        
//        if(numberOfVariablesToOptimize >=3){
//           initialValues[2] = alpha.getParameter(component,2);
//        }
        
        return initialValues;
    }
    
    public void solve(){
        int numberOfVariablesToOptimize = numberOfVariablesToOptimize();
        if(numberOfVariablesToOptimize == 0){
            return;
        }
        double[] initialValues =initialValues(numberOfVariablesToOptimize);
        solveVapoPressureRegression(initialValues);
    }
    
    public double[] solveVapoPressureRegression (double[] args){
        indeter=false;
        maxIterationsReached = false;
        message="";
        convergenceHistory.clear();
        double beforeError;
        double error;
        
        double criteria =50;
        
        iterations = 0;
        convergenceHistory.add(new Parameters_Error(args, vaporPressureError(), iterations));
        
        while(Math.abs(criteria) > tolerance && iterations < 1000){
            
            beforeError = vaporPressureError( args);
            double[] before = args;
            iterations++;    
           // double[] gradiente=  gradient(args);
            
            args = nextValue(args );
            for(int i =0; i<args.length;i++){
                if(Double.isNaN(args[i]) | Double.isInfinite(args[i])){
                    indeter = true;
                    StringBuilder sb = new StringBuilder();
                    sb.append("El valor del parametro " + i + " se indetermina en la iteración " + iterations);
                    sb.append("El valor que provoca la indeterminación es " + before[i]);
                    
                    message = sb.toString();
                    
                    return before;
                }
            }
            args = applyDamping(before, args);
            if(applyErrorDecreaseTechnique){
                args = errorDecrease(before,args);
            }
            error = vaporPressureError(args);
            
            Parameters_Error pe = new Parameters_Error(args, error,iterations);
            convergenceHistory.add(pe);
            
            
            criteria = error-beforeError;
        }
        
        if(! (iterations < 1000)){
            maxIterationsReached = true;
            message = "Se alcanzó el numero máximo de iteraciones";
        }

        return args;
     
    }
    

    
   public double vaporPressureError(){
        errorForEachExperimentalData.clear();
        double error =0;
        for (ExperimentalData pair: experimental){
            double temperature = pair.getTemperature();
            substance.setTemperature(temperature);
            substance.dewPressure();
            double expP = pair.getPressure();
            double calcP = substance.getPressure();
            double relativeError = (calcP - expP)/expP;
            double squareError = Math.pow(relativeError,2);
            error += squareError;
            errorForEachExperimentalData.add(new ErrorData(expP, calcP, relativeError,temperature));
        }   
        totalError = error;
        return error;   
   }
   private Double totalError;
   private ArrayList<ErrorData> errorForEachExperimentalData = new ArrayList();
   

   public void setParametersValues(double[] params){
        Component component = substance.getVapor().getComponent();
        Alpha alpha = substance.getVapor().getAlpha();
        
        for(int i = 0; i < params.length; i++){
            for(int j =i; j< params.length; j++){
                if(!fixParameters[j]){
                    alpha.setParameter(params[i], component, j);
                    break;
                }
            }
        }
   }
   
    
    public double vaporPressureError(double[] params){
        setParametersValues(params);
       return vaporPressureError();
    }

    
 

    public double[] gradient(double[] args){
        double[] gradient = new double[args.length];
        for(int i = 0; i < args.length;i++){
            gradient[i] = centralDerivative(args, i);
        }
   
        
        return gradient;
    }
    
    public double centralDerivative(double[] args, int i){
        double[] params = args.clone();
        args[i] = params[i] - numericalDerivativeDelta;
        double backError = vaporPressureError(args);
        args[i] = params[i] + numericalDerivativeDelta;
        double forwardError = vaporPressureError(args);
        resetParameterValues(args, params);
        return (forwardError - backError)/(2*numericalDerivativeDelta);
    }
//    public double doubleDeriv(double[] args, int i){
//        double[] params = args.clone();
//        double error = vaporPressureError(args);
//        args[i] = params[i] - numericalDerivativeDelta;
//        double backwardError = vaporPressureError(args);
//        args[i] = params[i] + numericalDerivativeDelta;
//        double forwardError = vaporPressureError(args);
//        resetParameterValues(args,params);
//        return (forwardError - 2 * error + backwardError)/ Math.pow(numericalDerivativeDelta,2);
//    }
    public double crossDerivative_ij(double[] args ,int i, int j ){
        double[] params = args.clone();
        args[i] = params[i] + numericalDerivativeDelta;
        double forwardDeriv =  centralDerivative(args, j);
        args[i] = params[i]- numericalDerivativeDelta;
        double backwardDeriv = centralDerivative(args, j);
        resetParameterValues(args, params);
        return (forwardDeriv - backwardDeriv)/(2*numericalDerivativeDelta);
    }
    
    
    
    
      public void resetParameterValues(double[] args, double[] params){
        setParametersValues(params);        
        for(int i = 0; i < params.length; i++){
            args[i] = params[i];
        }
    }
    
    


    
    public double[][] hessian(double[] args){
        int dimension = args.length;
        double[][] result = new double[dimension][dimension];
        
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j< dimension;j++){
//                if(i==j){
//                    result[i][j] = doubleDeriv(args, i);
//                }else{
                    result[i][j] = crossDerivative_ij(args, i, j);
//                }
            }
        }
        return result;
    }
    

    public double[] nextValue(double[] args){
        Matrix hessian = new Matrix(hessian(args));
        Matrix hessianInverse = new Matrix(hessian.inverse());
        double[] gradient = gradient(args);
        double[]del = hessianInverse.matrixVectorMultiplication(gradient);
        
        double[] result = new double[args.length];
        for(int i =0; i<args.length; i++){
            result[i] = args[i] - del[i];
        }return result;
        
    }
    private double parameterAMaxVariation = 0.2;
    private boolean constrainParameterA = false;
    
    private double parameterBMaxVariation = 0.2;
    private boolean constrainParameterB = false;
    
    private double parameterCMaxVariation = 0.2;
    private boolean constrainParameterC = false;
    
    
    private boolean applyErrorDecreaseTechnique = false;
    private int maxErrorDecreaseIterations = 26;
    private int errorDecreaseIterations = 0;
    
    public double[] errorDecrease(double[] before, double[] newValues){
        double error = vaporPressureError(before);
        double newError= vaporPressureError(newValues);
        
        errorDecreaseIterations = 0;
        
        while(newError > error && errorDecreaseIterations < maxErrorDecreaseIterations){
            errorDecreaseIterations++;
            for(int i =0 ; i< newValues.length; i++){
                newValues[i] = before[i] +(0.5) * (newValues[i]- before[i]);
            }
            newError = vaporPressureError(newValues);
            
        }
        return newValues;
    }
    
    
    
    public double[] applyDamping(double[] before, double[]newValues ){
        int size = before.length;
        double[] result = new double[size];         
            double lambda = findLambda(before, newValues);
            for(int i = 0; i < newValues.length; i++){
                result[i] = before[i] + lambda * (newValues[i]- before[i]);
            }
        return result;
    }
    
    
    
    
    
    
    private double findLambda(double[] before, double[] newValues){
        int numberOfVariablestoOptimize = numberOfVariablesToOptimize();
        ArrayList<Double> lambdas = new ArrayList();
        
        double lambda0;
         if(numberOfVariablestoOptimize >=1){
            if(!fixParameters[0]){
                lambda0 = requiredLambdaForConstraint(before[0], newValues[0], parameterAMaxVariation);
                if(considerLambda(lambda0, constrainParameterA)){
                    lambdas.add(lambda0);
                }
                
            }else if(!fixParameters[1]){
                lambda0 = requiredLambdaForConstraint(before[0], newValues[0], parameterBMaxVariation);
                if(considerLambda(lambda0, constrainParameterB)){
                    lambdas.add(lambda0);
                }
            }else if(!fixParameters[2]){
                lambda0 = requiredLambdaForConstraint(before[0], newValues[0], parameterCMaxVariation);
                if(considerLambda(lambda0, constrainParameterC)){
                    lambdas.add(lambda0);
                }
            }
        }
        
        double lambda1;
         if(numberOfVariablestoOptimize >=2){
            if(!fixParameters[1]){
                lambda1 = requiredLambdaForConstraint(before[1], newValues[1], parameterBMaxVariation);
                if(considerLambda(lambda1, constrainParameterB)){
                    lambdas.add(lambda1);
                }
            }else if(!fixParameters[2]){
                lambda1 = requiredLambdaForConstraint(before[1], newValues[1], parameterCMaxVariation);
                if(considerLambda(lambda1, constrainParameterC)){
                    lambdas.add(lambda1);
                }
            }
            
        }
        
        double lambda2;
        if(numberOfVariablestoOptimize >=3){
           lambda2 =requiredLambdaForConstraint(before[2], newValues[2], parameterCMaxVariation);
           if(considerLambda(lambda2, constrainParameterC)){
                    lambdas.add(lambda2);
                }
        }
        
        if(lambdas.isEmpty()){
            return 1;
        }
        return findMin(lambdas);
        
       
        
    }
    
    public double findMin(final ArrayList<Double> list) {
      Double min = list.get(0);
      for(Double number: list){
          int comparation = min.compareTo(number);
          if(comparation > 0){
              min = number;
          }
      }
      return min;
    }
    
    
    
    private boolean considerLambda(double lamda, boolean constrainParameter){
        return greaterThanZeroAndLessOrEqualsToOne(lamda) && constrainParameter;
    }
    
    
    private boolean greaterThanZeroAndLessOrEqualsToOne(double lambda){
        return lambda <= 1 && lambda > 0;
            
    }
    
    
    private double requiredLambdaForConstraint(double lastValue, double newValue, double maxDelta){
        double delta = newValue - lastValue;
        double limitVar = lastValue + Math.signum(delta) * maxDelta;
        
        double lamda = (limitVar- lastValue)/(delta);
        
        return lamda;
    }

    
    public boolean needsToBeConstrained(double delta, double maxVariation,boolean constraint){
        return Math.abs(delta) > maxVariation && constraint;
    }
    
    
    /**
     * @return the numericalDerivativeDelta
     */
    public double getNumericalDerivativeDelta() {
        return numericalDerivativeDelta;
    }

    /**
     * @param numericalDerivativeDelta the numericalDerivativeDelta to set
     */
    public void setNumericalDerivativeDelta(double numericalDerivativeDelta) {
        this.numericalDerivativeDelta = numericalDerivativeDelta;
    }

    /**
     * @return the experimental
     */
    public ArrayList<ExperimentalData> getExperimental() {
        return experimental;
    }

    /**
     * @param experimental the experimental to set
     */
    public void setExperimental(ArrayList<ExperimentalData> experimental) {
        this.experimental = experimental;
    }

    /**
     * @return the iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * @param iterations the iterations to set
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    /**
     * @return the tolerance
     */
    public double getTolerance() {
        return tolerance;
    }

    /**
     * @param tolerance the tolerance to set
     */
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

//    /**
//     * @return the fixParameterA
//     */
//    public boolean isFixParameterA() {
//        return fixParameterA;
//    }
//
//    /**
//     * @param fixParameterA the fixParameterA to set
//     */
//    public void setFixParameterA(boolean fixParameterA) {
//        this.fixParameterA = fixParameterA;
//    }
//
//    /**
//     * @return the fixParameterB
//     */
//    public boolean isFixParameterB() {
//        return fixParameterB;
//    }
//
//    /**
//     * @param fixParameterB the fixParameterB to set
//     */
//    public void setFixParameterB(boolean fixParameterB) {
//        this.fixParameterB = fixParameterB;
//    }
//
//    /**
//     * @return the fixParameterC
//     */
//    public boolean isFixParameterC() {
//        return fixParameterC;
//    }
//
//    /**
//     * @param fixParameterC the fixParameterC to set
//     */
//    public void setFixParameterC(boolean fixParameterC) {
//        this.fixParameterC = fixParameterC;
//    }

    /**
     * @return the convergenceHistory
     */
    public ArrayList<Parameters_Error> getConvergenceHistory() {
        return convergenceHistory;
    }

    /**
     * @param convergenceHistory the convergenceHistory to set
     */
    public void setConvergenceHistory(ArrayList<Parameters_Error> convergenceHistory) {
        this.convergenceHistory = convergenceHistory;
    }

    /**
     * @return the totalError
     */
    public double getTotalError() {
        if(totalError == null){
            vaporPressureError();
        }
        return totalError;
    }

   

    /**
     * @return the errorForEachExperimentalData
     */
    public ArrayList<ErrorData> getErrorForEachExperimentalData() {
       // if(experimental.size() != errorForEachExperimentalData.size()){
            vaporPressureError();//para calcular por primera vez
        //}
        return errorForEachExperimentalData;
        
    }

    /**
     * @return the indeter
     */
    public boolean isIndeter() {
        return indeter;
    }

    /**
     * @param indeter the indeter to set
     */
    public void setIndeter(boolean indeter) {
        this.indeter = indeter;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the maxIterationsReached
     */
    public boolean isMaxIterationsReached() {
        return maxIterationsReached;
    }

    /**
     * @param maxIterationsReached the maxIterationsReached to set
     */
    public void setMaxIterationsReached(boolean maxIterationsReached) {
        this.maxIterationsReached = maxIterationsReached;
    }

    /**
     * @return the parameterAMaxVariation
     */
    public double getParameterAMaxVariation() {
        return parameterAMaxVariation;
    }

    /**
     * @param parameterAMaxVariation the parameterAMaxVariation to set
     */
    public void setParameterAMaxVariation(double parameterAMaxVariation) {
        this.parameterAMaxVariation = parameterAMaxVariation;
    }

    /**
     * @return the constrainParameterA
     */
    public boolean isConstrainParameterA() {
        return constrainParameterA;
    }

    /**
     * @param constrainParameterA the constrainParameterA to set
     */
    public void setConstrainParameterA(boolean constrainParameterA) {
        this.constrainParameterA = constrainParameterA;
    }

    /**
     * @return the parameterBMaxVariation
     */
    public double getParameterBMaxVariation() {
        return parameterBMaxVariation;
    }

    /**
     * @param parameterBMaxVariation the parameterBMaxVariation to set
     */
    public void setParameterBMaxVariation(double parameterBMaxVariation) {
        this.parameterBMaxVariation = parameterBMaxVariation;
    }

    /**
     * @return the constrainParameterB
     */
    public boolean isConstrainParameterB() {
        return constrainParameterB;
    }

    /**
     * @param constrainParameterB the constrainParameterB to set
     */
    public void setConstrainParameterB(boolean constrainParameterB) {
        this.constrainParameterB = constrainParameterB;
    }

    /**
     * @return the parameterCMaxVariation
     */
    public double getParameterCMaxVariation() {
        return parameterCMaxVariation;
    }

    /**
     * @param parameterCMaxVariation the parameterCMaxVariation to set
     */
    public void setParameterCMaxVariation(double parameterCMaxVariation) {
        this.parameterCMaxVariation = parameterCMaxVariation;
    }

    /**
     * @return the constrainParameterC
     */
    public boolean isConstrainParameterC() {
        return constrainParameterC;
    }

    /**
     * @param constrainParameterC the constrainParameterC to set
     */
    public void setConstrainParameterC(boolean constrainParameterC) {
        this.constrainParameterC = constrainParameterC;
    }

    /**
     * @return the applyErrorDecreaseTechnique
     */
    public boolean isApplyErrorDecreaseTechnique() {
        return applyErrorDecreaseTechnique;
    }

    /**
     * @param applyErrorDecreaseTechnique the applyErrorDecreaseTechnique to set
     */
    public void setApplyErrorDecreaseTechnique(boolean applyErrorDecreaseTechnique) {
        this.applyErrorDecreaseTechnique = applyErrorDecreaseTechnique;
    }

    /**
     * @return the maxErrorDecreaseIterations
     */
    public int getMaxErrorDecreaseIterations() {
        return maxErrorDecreaseIterations;
    }

    /**
     * @param maxErrorDecreaseIterations the maxErrorDecreaseIterations to set
     */
    public void setMaxErrorDecreaseIterations(int maxErrorDecreaseIterations) {
        this.maxErrorDecreaseIterations = maxErrorDecreaseIterations;
    }

    /**
     * @return the errorDecreaseIterations
     */
    public int getErrorDecreaseIterations() {
        return errorDecreaseIterations;
    }

    /**
     * @param errorDecreaseIterations the errorDecreaseIterations to set
     */
    public void setErrorDecreaseIterations(int errorDecreaseIterations) {
        this.errorDecreaseIterations = errorDecreaseIterations;
    }

    /**
     * @return the fixParameters
     */
    public boolean[] getFixParameters() {
        return fixParameters;
    }

    /**
     * @param fixParameters the fixParameters to set
     */
    public void setFixParameters(boolean[] fixParameters) {
        this.fixParameters = fixParameters;
    }

   
}
