package termo.eos.mixingRule;

import termo.Constants;
import termo.activityModel.ActivityModel;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.eos.Cubic;
import termo.matter.Mixture;
import termo.matter.Substance;

public class HuronVidalModified2Order extends ExcessGibbsMixingRule{

	
	
	
	public HuronVidalModified2Order(ActivityModel activityModel,
			Cubic equationOfState) {
		super(activityModel, equationOfState);
		super.name="MHV2 (" +activityModel.getName() + ")";
		super.setL(equationOfState.calculateL(1.632, 1));
		//super.setL2(equationOfState.calculateL(1, 1));
		super.setL2(-0.003654);
	}
	@Override
	public double oneOverNParcial_aN2RespectN(Substance ci, Mixture mixture) {	
		
		double a = mixture.calculate_a_cubicParameter();
		double b =mixture.calculate_b_cubicParameter();
		
		double ai = ci.calculate_a_cubicParameter();
		double bi = ci.calculate_b_cubicParameter();
		double R = Constants.R;
		double T = mixture.getTemperature();
		
		
		double ei =ai / (bi *R*T);
		double e = a / (b *R*T);
		double gammai = super.activityModel.activityCoefficient(ci, mixture);
		
		double q1 = super.getL();
		double q2= super.getL2();
		
		return R*T*b*(
				(q1*ei+ q2*(Math.pow(e, 2)+ Math.pow(ei,2)) + Math.log(gammai) + Math.log(b/bi) + (bi/b)-1)
							/
							(q1 + 2 *q2 * e)
				) 
				+ a*bi/b;
		
		
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
