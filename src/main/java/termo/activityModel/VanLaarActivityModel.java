package termo.activityModel;

import java.util.Iterator;

import termo.Constants;
import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.matter.Mixture;
import termo.matter.Substance;

public class VanLaarActivityModel extends ActivityModel {

	@Override
	public double excessGibbsEnergy(Mixture mixture) {
		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
		int nc = mixture.getComponents().size();
		if(nc >2){
			System.out.println("VanLaar solo funciona para dos compuestos");
		}
		Iterator<Substance> iterator =mixture.getPureSubstances().iterator();	 
		Substance c1 = iterator.next();
		Substance c2 = iterator.next();
		
		double A12 = param.getA_vanLaar().getValue(c1.getComponent(), c2.getComponent());
		double A21 = param.getA_vanLaar().getValue(c2.getComponent(), c1.getComponent());
		double z1 = c1.getMolarFraction();
		double z2 = c2.getMolarFraction();
		
		return  Constants.R * mixture.getTemperature() * (A12 * A21 * z1 * z2)/(z1*A12 + z2*A21);		
	}

	@Override
	public double activityCoefficient(Substance ci, Mixture mixture) {
		
		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
		Substance cj =null;
		for(Substance ck : mixture.getPureSubstances()){
			if(!ck.equals(ci)){
				cj = ck;
			}
		}
		 
		double Aij =  param.getA_vanLaar().getValue(ci.getComponent(), cj.getComponent());
		double Aji = param.getA_vanLaar().getValue(cj.getComponent(), ci.getComponent());
		
		double zi = ci.getMolarFraction();
		double zj = cj.getMolarFraction();
		
		double lngammai = Aij * Math.pow( Aji*zj/(Aij*zi+Aji*zj)  ,2);
		
		return Math.exp(lngammai);
	}

	@Override
	public double parcialExcessGibbsRespectTemperature(Mixture mixture) {
		return excessGibbsEnergy(mixture)/mixture.getTemperature();
	}

}
