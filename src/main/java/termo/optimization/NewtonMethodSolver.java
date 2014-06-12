
package termo.optimization;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import termo.matrix.Matrix;
import termo.optimization.errorfunctions.ErrorFunction;
import termo.optimization.errorfunctions.VaporPressureErrorFunction;

/**
 *
 * @author Hugo
 */
public class NewtonMethodSolver implements PropertyChangeListener {
    //fields
    
    private ErrorFunction errorFunction;
    
    private boolean[] constrainParameters ;
    private double[] maxVariationParameters;
    
   
    private double numericalDerivativeDelta = 0.0001;
  
    private boolean[] fixParameters ;
    
    private boolean indeter;
    private boolean maxIterationsReached;
    private String message;
    
    
    private int iterations;
    private double tolerance = 1e-4;
    
    private ArrayList<Parameters_Error> convergenceHistory = new ArrayList();
    
    
    private boolean applyErrorDecreaseTechnique = false;
    private int maxErrorDecreaseIterations = 26;
    private int errorDecreaseIterations = 0;
    
    
  @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        if(null != evt.getPropertyName())switch (evt.getPropertyName()) {
            case "alpha":
                initializeArrays();
                break;
            case "mixingRule":
                initializeArrays();
                break;
        }
    }
    //constructores
    public NewtonMethodSolver(ErrorFunction errorFunction){
        this.errorFunction = errorFunction;
        initializeArrays();
       
    }
    
    
    public final void initializeArrays(){
        
         int numberOfParameters = errorFunction.numberOfParameters();
        fixParameters = new boolean[numberOfParameters];
        constrainParameters = new boolean[numberOfParameters];
        maxVariationParameters  = new double[numberOfParameters];
    }
    
    public int fixedVariablesCount(){
        int result = 0;
        
        for(boolean fix : fixParameters){
            if(fix){
                result++;
            }
        }
        return result;
    }
    
    public int numberOfVariablesToOptimize(){
       
        
        int numberOfParameters =errorFunction.numberOfParameters();
        int fixedParameters = fixedVariablesCount();
        
        return numberOfParameters- fixedParameters;
    }
  
    
    
  
    
    
    
    
    public double[] initialValues(int numberOfVariablesToOptimize){
        double[] initialValues = new double[numberOfVariablesToOptimize];
            for(int i = 0; i < numberOfVariablesToOptimize; i++){
                for(int j =i; j< errorFunction.numberOfParameters(); j++){
                    if(!fixParameters[j]){
                        initialValues[i] =   errorFunction.getParameter(j);
                        break;
                    }
                }
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
    
    public double[] solveVapoPressureRegression (double[] args){
        indeter=false;
        maxIterationsReached = false;
        message="";
        convergenceHistory.clear();
        double beforeError;
        double error;
        
        double errorDiference =50;
        double gradientAbsSum = gradientAbsSum(args);
        double[] variations = new double[args.length];
        
        iterations = 0;
        convergenceHistory.add(new Parameters_Error(args, errorFunction.error(), iterations,gradientAbsSum));
        
        while(criteria(errorDiference,gradientAbsSum,variations) && iterations < 1000){
            
            beforeError = vaporPressureError( args);
            double[] before = args;
            iterations++;    
           // double[] gradiente=  gradient(args);
            
            args = nextValue(args );
            for(int i =0; i<args.length;i++){
                if(Double.isNaN(args[i]) | Double.isInfinite(args[i])){
                    indeter = true;
                    StringBuilder sb = new StringBuilder();
                    sb.append("El valor del parametro " + i + " se indetermina en la iteraciÃ³n " + iterations);
                    sb.append("El valor que provoca la indeterminaciÃ³n es " + before[i]);
                    
                    message = sb.toString();
                    
                    return before;
                }
            }
            args = applyDamping(before, args);
            if(applyErrorDecreaseTechnique){
                args = errorDecrease(before,args);
            }
            error = vaporPressureError(args);
            
            gradientAbsSum =gradientAbsSum(args);
            //variations = parametersDelta(before,args);
            
            Parameters_Error pe = new Parameters_Error(args, error,iterations,gradientAbsSum);
            convergenceHistory.add(pe);
            
            
            errorDiference = error-beforeError;
        }
        
        if(! (iterations < 1000)){
            maxIterationsReached = true;
            message = "Se alcanzÃ³ el numero mÃ¡ximo de iteraciones";
        }

        return args;
     
    }
    
    private boolean gradientCriterion;
    private boolean errorDiferenceCriterion = true;//default
    boolean parameterDifferenceCriterion ;
    
    private double gradientCriterionTolerance = 1e-4;
    double parametersVariationTolerance = 1e-4;
    public boolean criteria(double errorDiferenceValue,double gradientAbsSum,double[] parametersVariations){
        if(errorDiferenceCriterion){
            return Math.abs(errorDiferenceValue) > tolerance;
        }else if(gradientCriterion){
            
            return gradientAbsSum > gradientCriterionTolerance;
        }else if(parameterDifferenceCriterion){
            return findMax(parametersVariations) >parametersVariationTolerance;
        }
        return false;
    }
  
    
    public double gradientAbsSum(double[] args){
        double sum=0; 
        for( double deriv: gradient(args)){
            sum += Math.abs(deriv);
        }
        return sum;
    }
  
 

   public void setParametersValues(double[] params){
        for(int i = 0; i < params.length; i++){
            for(int j =i; j< errorFunction.numberOfParameters(); j++){
                if(!fixParameters[j]){
                    errorFunction.setParameter(params[i], j);
                    break;
                }
            }
        }
   }
   
   
  
   
    
    public double vaporPressureError(double[] params){
        setParametersValues(params);
       return errorFunction.error();
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
//        double error = error(args);
//        args[i] = params[i] - numericalDerivativeDelta;
//        double backwardError = error(args);
//        args[i] = params[i] + numericalDerivativeDelta;
//        double forwardError = error(args);
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
//        int numberOfVariablestoOptimize = numberOfVariablesToOptimize();
        ArrayList<Double> lambdas = new ArrayList();
        
        for (int i = 0; i < newValues.length; i++){
            for(int j = i; j< errorFunction.numberOfParameters(); j++){
                if(!fixParameters[j]){
                    double lambda = requiredLambdaForConstraint(before[i], newValues[i], maxVariationParameters[j]);
                    if(considerLambda(lambda, constrainParameters[j])){
                        lambdas.add(lambda);
                    }
                    break;
                }
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
    
    public double findMax( double[] list) {
      Double max = list[0];
      for(Double number: list){
          int comparation = max.compareTo(number);
          if(comparation < 0){
              max = number;
          }
      }
      return max;
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

    /**
     * @return the constrainParameters
     */
    public boolean[] getConstrainParameters() {
        return constrainParameters;
    }

    /**
     * @param constrainParameters the constrainParameters to set
     */
    public void setConstrainParameters(boolean[] constrainParameters) {
        this.constrainParameters = constrainParameters;
    }

    /**
     * @return the maxVariationParameters
     */
    public double[] getMaxVariationParameters() {
        return maxVariationParameters;
    }

    /**
     * @param maxVariationParameters the maxVariationParameters to set
     */
    public void setMaxVariationParameters(double[] maxVariationParameters) {
        this.maxVariationParameters = maxVariationParameters;
    }

    /**
     * @return the errorFunction
     */
    public ErrorFunction getErrorFunction() {
        return errorFunction;
    }

    /**
     * @param errorFunction the errorFunction to set
     */
    public void setErrorFunction(ErrorFunction errorFunction) {
        this.errorFunction = errorFunction;
    }

    /**
     * @return the gradientCriterion
     */
    public boolean isGradientCriterion() {
        return gradientCriterion;
    }

    /**
     * @param gradientCriterion the gradientCriterion to set
     */
    public void setGradientCriterion(boolean gradientCriterion) {
        this.gradientCriterion = gradientCriterion;
    }

    /**
     * @return the errorDiferenceCriterion
     */
    public boolean isErrorDiferenceCriterion() {
        return errorDiferenceCriterion;
    }

    /**
     * @param errorDiferenceCriterion the errorDiferenceCriterion to set
     */
    public void setErrorDiferenceCriterion(boolean errorDiferenceCriterion) {
        this.errorDiferenceCriterion = errorDiferenceCriterion;
    }

    /**
     * @return the gradientCriterionTolerance
     */
    public double getGradientCriterionTolerance() {
        return gradientCriterionTolerance;
    }

    /**
     * @param gradientCriterionTolerance the gradientCriterionTolerance to set
     */
    public void setGradientCriterionTolerance(double gradientCriterionTolerance) {
        this.gradientCriterionTolerance = gradientCriterionTolerance;
    }

   

   
}
