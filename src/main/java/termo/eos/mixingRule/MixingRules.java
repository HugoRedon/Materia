package termo.eos.mixingRule;

import termo.activityModel.ActivityModel;
import termo.eos.Cubic;

public class MixingRules {

	public static VDWMixingRule vanDerWaals(){
		return new VDWMixingRule();
	}
	public static MathiasKlotzPrausnitzMixingRule mathiasKlotzPrausnitz(){
		return new MathiasKlotzPrausnitzMixingRule();
	}
	public static HuronVidalMixingRule huronVidal(ActivityModel activityModel , Cubic equationOfState){
		return new HuronVidalMixingRule(activityModel, equationOfState);
	}
	
	public static WongSandlerMixingRule wongSandler(ActivityModel activityModel, Cubic equationOfState){
		return new WongSandlerMixingRule(activityModel, equationOfState);
	}
}
