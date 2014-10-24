package termo.eos.mixingRule;

import termo.activityModel.ActivityModel;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;

public class LinearCombinationOfHuronVidalAndMichelsenModels extends ExcessGibbsMixingRule{

	public double lambda;
	Cubic equationOfSate;
	
	public LinearCombinationOfHuronVidalAndMichelsenModels(
			ActivityModel activityModel, Cubic equationOfState,double lambda) {
		super(activityModel, equationOfState);
		super.name="LCVM (" +activityModel.getName() + ")";
		this.equationOfSate = equationOfState;
		setLambda( lambda);
		
	}
	
	public double getLambda(){
		return lambda;
	}
	public void setLambda(double lambda){
		this.lambda = lambda;
		updateLs();
	}
	
	public void updateLs(){
		double c_ = equationOfSate.calculateL(1, 1);
		double q1 = equationOfSate.calculateL(1.235,1);
		setL((lambda/c_ )+ ((1-lambda)/q1));
		setL2((1-lambda)/q1);
	}

	@Override
	public String getParameterName(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getParameter(Compound referenceComponent,
			Compound nonReferenceComponent, InteractionParameter params,
			int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setParameter(double value, Compound referenceComponent,
			Compound nonReferenceComponent, InteractionParameter params,
			int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int numberOfParameters() {
		// TODO Auto-generated method stub
		return 0;
	}

}
