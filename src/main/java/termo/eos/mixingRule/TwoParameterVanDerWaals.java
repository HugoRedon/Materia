package termo.eos.mixingRule;

import termo.binaryParameter.ActivityModelBinaryParameter;
import termo.binaryParameter.InteractionParameter;
import termo.component.Compound;
import termo.matter.Mixture;
import termo.matter.Substance;

public class TwoParameterVanDerWaals extends MixingRule {

	public TwoParameterVanDerWaals() {
		this.name = "2PDVW";
	}


	@Override
    public double a(Mixture mixture){
		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
        double a= 0;
        for(Substance iComponent: mixture.getPureSubstances()){
           for(Substance jComponent: mixture.getPureSubstances()){
              double xi = iComponent.getMolarFraction();
              double xj = jComponent.getMolarFraction();
              
              double ai = iComponent.calculate_a_cubicParameter();
              double aj = jComponent.calculate_a_cubicParameter();
              
              //double kij =kij(iComponent,jComponent,param);
              double Kij = param.getTwoParameterVanDerWaals().getValue(iComponent.getComponent(), jComponent.getComponent());
              double Kji = param.getTwoParameterVanDerWaals().getValue(jComponent.getComponent(), iComponent.getComponent());
             
              a += xi * xj * Math.sqrt(ai * aj) * (1-xi*Kij-xj*Kji);
          }
        }
        return a;
    }

	@Override
	public double oneOverNParcial_aN2RespectN(Substance iComponent,
			Mixture mixture) {
		//double a = mixture.calculate_a_cubicParameter();
		//return parcialNaRespectNi(iComponent, mixture) + a;
		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
		
		double zi = iComponent.getMolarFraction();
		double ai = iComponent.calculate_a_cubicParameter();
		
		double firstTerm = 0;
		for(Substance cj : mixture.getPureSubstances()){
			double zj = cj.getMolarFraction();
			double aj = cj.calculate_a_cubicParameter();
			double Kij = param.getTwoParameterVanDerWaals().getValue(iComponent.getComponent(), cj.getComponent());
			double Kji = param.getTwoParameterVanDerWaals().getValue(cj.getComponent(), iComponent.getComponent());
			double aij = Math.sqrt(ai*aj)*(1-zi*Kij -zj *Kji);
			firstTerm += zj*aij;
			
		}
		
		
		
		double thirdTerm = 0;
		for(Substance cj : mixture.getPureSubstances()){
			if(!cj.getComponent().equals(iComponent.getComponent())){
				double zj = cj.getMolarFraction();
				double aj = cj.calculate_a_cubicParameter();
				double Kij = param.getTwoParameterVanDerWaals().getValue(iComponent.getComponent(), cj.getComponent());				
				thirdTerm  += zj*Math.sqrt(ai*aj)*Kij; 
				//secondTerm+= zi*zj * Math.sqrt(ai*aj)*(zj*Kji-(1-zi)*Kij);
			}
		}	
		
		double secondTerm = 0;
		for(Substance cj : mixture.getPureSubstances()){
			for(Substance ck : mixture.getPureSubstances()){
				if(!ck.getComponent().equals(iComponent.getComponent())){
					
					double zj = cj.getMolarFraction();
					double zk = ck.getMolarFraction();
					
					double aj = cj.calculate_a_cubicParameter();
					double ak = ck.calculate_a_cubicParameter();
					
					double Kjk = param.getTwoParameterVanDerWaals().getValue(cj.getComponent(), ck.getComponent());
					double Kkj = param.getTwoParameterVanDerWaals().getValue(ck.getComponent(), cj.getComponent());
					
					secondTerm += zj *zk *Math.sqrt(aj*ak)*(zj *Kjk + zk*Kkj );
					
				}
			}
		}
		
		
		return 2*firstTerm +  secondTerm -2*zi*thirdTerm ;		
	}

//	@Override
//	public double oneOverNParcial_aN2RespectN(Substance iComponent,
//			Mixture mixture) {
//		//double a = mixture.calculate_a_cubicParameter();
//		//return parcialNaRespectNi(iComponent, mixture) + a;
//		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
//		double firstTerm = 0;
//		for(Substance sk : mixture.getPureSubstances()){
//			double zk = sk.getMolarFraction();
//			double aik = aij(iComponent,sk,param);
//			firstTerm += zk*aik;
//		}
//		double secondTerm = 0;
//		
//		double zi = iComponent.getMolarFraction();
//		double ai = iComponent.calculate_a_cubicParameter();
//		for(Substance cj : mixture.getPureSubstances()){
//			if(!cj.getComponent().equals(iComponent.getComponent())){
//				double zj = cj.getMolarFraction();
//				double aj = cj.calculate_a_cubicParameter();
//				
//				double Kij = param.getTwoParameterVanDerWaals().getValue(iComponent.getComponent(), cj.getComponent());
//				double Kji = param.getTwoParameterVanDerWaals().getValue(cj.getComponent(), iComponent.getComponent());
//				
//				secondTerm+= zi*zj * Math.sqrt(ai*aj)*(zj*Kji-(1-zi)*Kij);
//			}
//		}				
//		return 2*(firstTerm + secondTerm);		
//	}
	
//	public double parcialNaRespectNi(Substance iComponent,
//			Mixture mixture){
//		
//		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)mixture.getBinaryParameters();
//		double firstTerm = 0;
//		for(Substance sk : mixture.getPureSubstances()){
//			double zk = sk.getMolarFraction();
//			double aik = aij(iComponent,sk,param);
//			firstTerm += zk*aik;
//		}
//		double secondTerm = 0;
//		
//		double zi = iComponent.getMolarFraction();
//		double ai = iComponent.calculate_a_cubicParameter();
//		for(Substance cj : mixture.getPureSubstances()){
//			if(cj.equals(iComponent)){
//				System.out.println("mismo compuesto.");
//				continue;
//			}
//			double zj = cj.getMolarFraction();
//			double aj = cj.calculate_a_cubicParameter();
//			
//			double Kij = param.getTwoParameterVanDerWaals().getValue(iComponent.getComponent(), cj.getComponent());
//			double Kji = param.getTwoParameterVanDerWaals().getValue(cj.getComponent(), iComponent.getComponent());
//			
//			
//			secondTerm+= zi*zj * Math.sqrt(ai*aj)*(zj*Kji-(1-zi)*Kij	);
//		}
//		
//		double a = mixture.calculate_a_cubicParameter();
//		
//		return 2*(firstTerm + secondTerm) - a;
//		
//		
//	}
	
	


	@Override
	public double temperatureParcial_a(Mixture mixture) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getParameter(Compound referenceComponent,
			Compound nonReferenceComponent, InteractionParameter params,
			int index) {
		
		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)params;
		if(index ==0){
			return param.getTwoParameterVanDerWaals().getValue(referenceComponent, nonReferenceComponent);
		}else if(index ==1){
			return param.getTwoParameterVanDerWaals().getValue(nonReferenceComponent, referenceComponent);
		}else{
			return 0;
		}
	}

	@Override
	public void setParameter(double value, Compound referenceComponent,
			Compound nonReferenceComponent, InteractionParameter params,
			int index) {
		ActivityModelBinaryParameter param = (ActivityModelBinaryParameter)params;
		if(index ==0){
			param.getTwoParameterVanDerWaals().setValue(referenceComponent, nonReferenceComponent,value);
		}else if(index ==1){
			param.getTwoParameterVanDerWaals().setValue(nonReferenceComponent, referenceComponent,value);
		}
	}

	@Override
	public int numberOfParameters() {
		return 2;
	}
	@Override
	public String getParameterName(int index) {
		if(index== 0){
			return "Kij";
		}else{
			return "Kji";
		}
	}

}
