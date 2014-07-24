package termo.eos.mixingRule;

import termo.activityModel.ActivityModel;
import termo.eos.Cubic;

public class MixingRules {

	public VDWMixingRule vanDerWaals(){
		return new VDWMixingRule();
	}
	public MathiasKlotzPrausnitzMixingRule mathiasKlotzPrausnitz(){
		return new MathiasKlotzPrausnitzMixingRule();
	}
	public HuronVidalMixingRule huronVidal(ActivityModel activityModel , Cubic equationOfState){
		return new HuronVidalMixingRule(activityModel, equationOfState);
	}
	
	public WongSandlerMixingRule wongSandler(ActivityModel activityModel, Cubic equationOfState){
		return new WongSandlerMixingRule(activityModel, equationOfState);
	}
}
