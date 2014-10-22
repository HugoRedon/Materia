package termo.eos.mixingRule;

import termo.activityModel.ActivityModel;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;

public class HuronVidalOrbeySandlerModification extends ExcessGibbsMixingRule {

	public HuronVidalOrbeySandlerModification(ActivityModel activityModel,
			Cubic equationOfState) {
		super(activityModel, equationOfState);
		super.setL2(equationOfState.calculateL(1,	1));
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
