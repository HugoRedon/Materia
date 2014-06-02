
package termo.optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import termo.component.Component;
import termo.data.ExperimentalData;
import termo.eos.alpha.Alpha;
import termo.matrix.Matrix2x2;
import termo.matrix.Matrix3x3;
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
    
    private boolean fixParameterA;
    private boolean fixParameterB;
    private boolean fixParameterC;
    //end fields
    private boolean indeter;
    private boolean maxIterationsReached;
    private String message;
    
    
    private int iterations;
    private double tolerance = 1e-4;
    
    private ArrayList<Parameters_Error> convergenceHistory = new ArrayList();
    
    
    private double damp = 1;
 
    //constructores
    public AlphaOptimization(HeterogeneousSubstance substance){
        this.substance = substance;
    }
    
    public AlphaOptimization(HeterogeneousSubstance substance,ArrayList<ExperimentalData> experimental){
        this.substance = substance ; 
        this.experimental = experimental;
    }
    public AlphaOptimization(HeterogeneousSubstance substance,ArrayList<ExperimentalData> experimental, double pass){
       this(substance, experimental);
       this.numericalDerivativeDelta = pass;
    }
    //end constructors
    
    public int fixedVariablesCount(){
        int result = 0;
        if(fixParameterA){
            result++;
        }if(fixParameterB){
            result++;
        }if(fixParameterC){
            result++;
        }
        return result;
    }
    
    public int numberOfVariablesToOptimize(){
        Alpha alpha = substance.getVapor().getAlpha();
        
        int numberOfParameters = alpha.numberOfParameters();
        int fixedParameters = fixedVariablesCount();
        
        return numberOfParameters- fixedParameters;
    }

    public double[] initialValues(int numberOfVariablesToOptimize){
        Alpha alpha = substance.getVapor().getAlpha();
        double[] initialValues = new double[numberOfVariablesToOptimize];

        Component component = substance.getVapor().getComponent();
        if(numberOfVariablesToOptimize >=1){
            if(!fixParameterA){
                initialValues[0] = alpha.getAlphaParameterA(component);
            }else if(!fixParameterB){
                initialValues[0] = alpha.getAlphaParameterB(component);
            }else if(!fixParameterC){
                initialValues[0] = alpha.getAlphaParameterC(component);
            }
        }
        
        if(numberOfVariablesToOptimize >=2){
            if(!fixParameterB){
                initialValues[1] = alpha.getAlphaParameterB(component);
            }else if(!fixParameterC){
                initialValues[1] = alpha.getAlphaParameterC(component);
            }
            
        }
        
        if(numberOfVariablesToOptimize >=3){
           initialValues[2] = alpha.getAlphaParameterC(component);
        }
        
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
    
    public double[] solveVapoPressureRegression (double...args){
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
                    StringBuffer sb = new StringBuffer();
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
   

   public void setParametersValues(double... params){
        Component component = substance.getVapor().getComponent();
        Alpha alpha = substance.getVapor().getAlpha();
        int numberOfVariablesToOptimize = numberOfVariablesToOptimize();
        
        
        if(numberOfVariablesToOptimize >=1){
            if(!fixParameterA){
                alpha.setAlphaParameterA(params[0],component);
            }else if(!fixParameterB){
                alpha.setAlphaParameterB(params[0],component);
            }else if(!fixParameterC){
                alpha.setAlphaParameterC(params[0],component);
            }
        }
        
        if(numberOfVariablesToOptimize >=2){
            if(!fixParameterB){
                alpha.setAlphaParameterB(params[1],component);
            }else if(!fixParameterC){
                alpha.setAlphaParameterC(params[1],component);
            }
            
        }
        
        if(numberOfVariablesToOptimize >=3){
           alpha.setAlphaParameterC(params[2],component);
        }
   }
   
    
    public double vaporPressureError(double... params){
        setParametersValues(params);
       return vaporPressureError();
    }

    
    
    
    
    
    

    
//    public double[] applyDeltaOnA(double... args){
//        args[0] = args[0] + numericalDerivativeDelta;
//        return args;
//    }
//    public double[] applyDeltaOnB(double... args){
//        args[1] = args[1] + numericalDerivativeDelta;
//        return args;
//    }
//    public double[] applyDeltaOnC(double... args){
//        args[2] = args[2] + numericalDerivativeDelta;
//        return args;
//    }

//    public double derivative_A(double... args){
//        double[] before = args.clone();//wtf
//        double error = vaporPressureError(args);
//        args = applyDeltaOnA(args);
//        double error_ = vaporPressureError(args);
//        
//        double result = (error_ - error)/numericalDerivativeDelta;
//        
//        setParametersValues(before);
//        return result;
//    }
    
  
    
    public double centralDerivativeA(double[] args){
        return centralDerivative(args, 0);
    }
    
    public double centralDerivativeB(double... args){
        return centralDerivative(args, 1);
//        int variableIndex = 1;
//        
//        double[] before = args.clone();
//        args[variableIndex] = before[variableIndex] - numericalDerivativeDelta;
//        double backError = vaporPressureError(args);
//        
//        args[variableIndex] = before[variableIndex] + numericalDerivativeDelta;
//        double forwardError = vaporPressureError(args);
//        
//        setParametersValues(before);
//        return (forwardError - backError)/(2*numericalDerivativeDelta);
    }
    
    
//    public double derivative_B(double... args){
//        double[] before = args.clone();
//        double error = vaporPressureError(args);
//        args = applyDeltaOnB(args);
//        double error_ = vaporPressureError(args);
//        setParametersValues(before);
//        return(error_ - error)/numericalDerivativeDelta;
//    }

    
    public double centralDerivativeC(double... args){
        return centralDerivative(args, 2);
    }
    
//    public double derivative_C(double... args){
//        double[] before = args.clone();
//        double error = vaporPressureError(args);
//        double error_ = vaporPressureError(args[0] , args[1],args[2]+ numericalDerivativeDelta);
//        setParametersValues(before);
//        return(error_ - error)/numericalDerivativeDelta;
//    }
//    

    public double[] gradient(double... args){
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
    public double doubleDeriv(double[] args, int i){
        double[] params = args.clone();
        double error = vaporPressureError(args);
        args[i] = params[i] - numericalDerivativeDelta;
        double backwardError = vaporPressureError(args);
        args[i] = params[i] + numericalDerivativeDelta;
        double forwardError = vaporPressureError(args);
        resetParameterValues(args,params);
        return (forwardError - 2 * error + backwardError)/ Math.pow(numericalDerivativeDelta,2);
    }
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
    
    
    
  public double doubleCentralDerivAA(double... args){
        return doubleDeriv(args,0);
    }

    
    public double doubleCentralDerivAB(double... args){
        int i = 0;
        int j = 1;
        return crossDerivative_ij(args, i, j);
        
    }

     public double doubleCentralDerivBA(double... args){
        int i = 1;
        int j = 0;
        return crossDerivative_ij(args, i, j);

    }
    
 
    
    public double doubleCentralDerivAC(double... args){
        int i = 0;
        int j = 2;
        return crossDerivative_ij(args, i, j);

    }
    
    

     public double doubleCentralDerivBB(double... args){
         return doubleDeriv(args, 1);

    }
    

    public double doubleCentralDerivBC(double... args){
        int i = 1;
        int j = 2;
        
        return crossDerivative_ij(args, i, j);
        

    }
    

    public double doubleCentralDerivCA(double... args){
        int i = 2;
        int j = 0;
        
        return crossDerivative_ij(args, i, j);

    }

    public double doubleCentralDerivCB(double... args){
        int i = 2;
        int j = 1;
        
        return crossDerivative_ij(args, i, j);

    }

     public double doubleCentralDerivCC(double... args){
         return doubleDeriv(args,2);

    }
    
    

    
    public double[][] hessian(double... args){
        if(args.length==1){
            double[][] result =  {{doubleCentralDerivAA(args)}};
            return result;
        }else if(args.length==2){
            double[][] result = {
                {doubleCentralDerivAA(args),doubleCentralDerivAB(args)},
                {doubleCentralDerivBA(args),doubleCentralDerivBB(args)}
            };
            return result;
        
        }else if(args.length==3){
            double[][] result = {
                {doubleCentralDerivAA(args),doubleCentralDerivAB(args),doubleCentralDerivAC(args)},
                {doubleCentralDerivBA(args),doubleCentralDerivBB(args),doubleCentralDerivBC(args)},
                {doubleCentralDerivCA(args),doubleCentralDerivCB(args),doubleCentralDerivCC(args)}
            };
            return result;
        }
        return null;
    }
    

    public double[] nextValue(double... args){
        if(args.length ==1){
            double[] result = {args[0] -centralDerivativeA(args)/doubleCentralDerivAA(args)};
            return result;
        }else if(args.length ==2){
            Matrix2x2 hessian = new Matrix2x2(hessian(args));
            Matrix2x2 hessianInverse = new Matrix2x2(hessian.inverse());
            double[] gradient = gradient(args);
            double []del = hessianInverse.matrixVectorMultiplication(gradient);

            double[] result = {args[0]- del[0], args[1]- del[1]};
            return result;
        }else if(args.length ==3){
            Matrix3x3 hessian = new Matrix3x3(hessian(args));
            Matrix3x3 hessianInverse = new Matrix3x3(hessian.inverse());
            double[] gradient = gradient(args);
            double[] del= hessianInverse.matrixVectorMultiplication(gradient);
            
            double[] result = {args[0]- del[0], args[1]- del[1], args[2]-del[2]};
            return result;
        }
        return null;
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
//        
//        if(size ==1){
//            double newA = newValues[0];
//            double lastA = before[0];
//            double deltaA = newA -lastA;
//            if(needsToBeConstrained(deltaA, parameterAMaxVariation, constrainParameterA)){
//                newA = lastA + Math.signum(deltaA)*  parameterAMaxVariation;
//            }
//            result[0] = newA;
//            
//        }else if(size ==2){
//            double newA = newValues[0];
//            double lastA = before[0];
//            double deltaA = newA -lastA;
//            
//            double newB = newValues[1];
//            double lastB = before[1];
//            double deltaB = newB- lastB;
//            
//           // boolean applyConstraintOnB = needsToBeConstrained(deltaB, parameterBMaxVariation, constrainParameterB);
//            
//            if(needsToBeConstrained(newA-lastA, parameterAMaxVariation, constrainParameterA)){
//                newA =lastA + Math.signum(deltaA)* parameterAMaxVariation;
//                newB =lastB + (newA - lastA) *(deltaB/deltaA);
//               
//            }
//            
//            if(needsToBeConstrained(newB-lastB,parameterBMaxVariation , constrainParameterB)){
//                newB = lastB + Math.signum(deltaB)* parameterBMaxVariation;
//                newA = lastA + (deltaA/deltaB)*(newB-lastB);
//            }
//            
//            result[0] = newA;
//            result[1] = newB;
//        }else if(size ==3){
//            
            double lambda = findLambda(before, newValues);
            for(int i = 0; i < newValues.length; i++){
                result[i] = before[i] + lambda * (newValues[i]- before[i]);
            }
//        }

        return result;
    }
    
    
    
    
    
    
    private double findLambda(double[] before, double[] newValues){
        int numberOfVariablestoOptimize = numberOfVariablesToOptimize();
        ArrayList<Double> lambdas = new ArrayList();
        
        double lambda0;
         if(numberOfVariablestoOptimize >=1){
            if(!fixParameterA){
                lambda0 = requiredLambdaForConstraint(before[0], newValues[0], parameterAMaxVariation);
                if(considerLambda(lambda0, constrainParameterA)){
                    lambdas.add(lambda0);
                }
                
            }else if(!fixParameterB){
                lambda0 = requiredLambdaForConstraint(before[0], newValues[0], parameterBMaxVariation);
                if(considerLambda(lambda0, constrainParameterB)){
                    lambdas.add(lambda0);
                }
            }else if(!fixParameterC){
                lambda0 = requiredLambdaForConstraint(before[0], newValues[0], parameterCMaxVariation);
                if(considerLambda(lambda0, constrainParameterC)){
                    lambdas.add(lambda0);
                }
            }
        }
        
        double lambda1;
         if(numberOfVariablestoOptimize >=2){
            if(!fixParameterB){
                lambda1 = requiredLambdaForConstraint(before[1], newValues[1], parameterBMaxVariation);
                if(considerLambda(lambda1, constrainParameterB)){
                    lambdas.add(lambda1);
                }
            }else if(!fixParameterC){
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
         
//        double lamdaA = requiredLambdaForConstraint(before[0], newValues[0], parameterAMaxVariation);
//        double lamdaB = requiredLambdaForConstraint(before[1], newValues[1], parameterBMaxVariation);
//        double lamdaC = requiredLambdaForConstraint(before[2], newValues[2], parameterCMaxVariation);
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        if(considerLambda(lamdaA, constrainParameterA)){
//            lambdas.add(lamdaA);
//        }if(considerLambda(lamdaB, constrainParameterB)){
//            lambdas.add(lamdaB);
//        }if(considerLambda(lamdaC, constrainParameterC)){
//            lambdas.add(lamdaC);
//        }
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
     * @return the damp
     */
    public double getDamp() {
        return damp;
    }

    /**
     * @param damp the damp to set
     */
    public void setDamp(double damp) {
        this.damp = damp;
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

    /**
     * @return the fixParameterA
     */
    public boolean isFixParameterA() {
        return fixParameterA;
    }

    /**
     * @param fixParameterA the fixParameterA to set
     */
    public void setFixParameterA(boolean fixParameterA) {
        this.fixParameterA = fixParameterA;
    }

    /**
     * @return the fixParameterB
     */
    public boolean isFixParameterB() {
        return fixParameterB;
    }

    /**
     * @param fixParameterB the fixParameterB to set
     */
    public void setFixParameterB(boolean fixParameterB) {
        this.fixParameterB = fixParameterB;
    }

    /**
     * @return the fixParameterC
     */
    public boolean isFixParameterC() {
        return fixParameterC;
    }

    /**
     * @param fixParameterC the fixParameterC to set
     */
    public void setFixParameterC(boolean fixParameterC) {
        this.fixParameterC = fixParameterC;
    }

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

   
}
