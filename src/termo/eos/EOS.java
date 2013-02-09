package termo.eos;

import termo.eos.alpha.Alpha_R;
import termo.eos.alpha.Mathias_StryjekVera;
import termo.eos.alpha.PR_RKS_Constants;
import termo.eos.alpha.Soave_PengRobinsonAlpha;
import termo.eos.alpha.Twu;

/**
 *
 * @author Hugo Redon Rivera
 */
public abstract class EOS {
  
   private String name;
    /**
     * 
     * @return The name of the equation of state as String
     */
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return this.name;
    }
    
    /**
     * 
     * @return The representation of the Equation of State as String
     */
    public abstract String getEquation();
    
    /**
     * 
     * @return The state whether it has been initialized as boolean
     */
//    public abstract boolean isReadyToCalculate();
    /**
     * 
     * @return If components are needed for calculations as boolean
     */
    public abstract boolean needsComponents();
   
    public abstract boolean isCubic();
    

    
    private static CubicAlpha pengRobinsonBase(){
        CubicAlpha pengRobinson = new CubicAlpha();
        pengRobinson.set_u(2);
        pengRobinson.set_w(-1);
         pengRobinson.setOmega_a(0.45723553);
        pengRobinson.setOmega_b(0.077796074);
        return pengRobinson;
    }
    
    
    
    public static CubicAlpha pengRobinson(){
        CubicAlpha pengRobinson = pengRobinsonBase();
        pengRobinson.setName(EOSNames.PengRobinson);
        
        Alpha_R alpha = new Soave_PengRobinsonAlpha();
        alpha.setR(PR_RKS_Constants.r_PengRobinson);
        
        pengRobinson.setAlpha(alpha);
        return pengRobinson;
    }
        
     
    
    public static CubicAlpha pengRobinsonStryjekVera(){
        CubicAlpha prsv = pengRobinsonBase();
        prsv.setName(EOSNames.PengRobinsonStryjekVera);
        
        Alpha_R alpha = new Mathias_StryjekVera();
        alpha.setR(PR_RKS_Constants.r_PengRobinson);
        
         prsv.setAlpha(alpha);
        return prsv;
    }
    
    
    public static IdealGas idealGas(){
        return new IdealGas();
    }
    
    private static CubicAlpha redlichKwongSoaveBase(){
        CubicAlpha rks = new CubicAlpha();
        rks.set_u(1);
        rks.set_w(0);
        
        rks.setOmega_a(0.42748023);
        rks.setOmega_b(0.08664035);
        return rks;
    }
    public static CubicAlpha redlichKwongSoave(){
        CubicAlpha rks = redlichKwongSoaveBase();
        rks.setName(EOSNames.RedlichKwongSoave);
        
        Alpha_R alpha = new Soave_PengRobinsonAlpha();
        alpha.setR(PR_RKS_Constants.r_RedlichKwongSoave);
        
        rks.setAlpha(alpha);
        return rks;
    }
    public static CubicAlpha redlichKwongSoaveMathias(){
        CubicAlpha rksm = redlichKwongSoaveBase();
        rksm.setName(EOSNames.RedlichKwongSoaveMathias);
        
          Alpha_R alpha = new Mathias_StryjekVera();
        alpha.setR(PR_RKS_Constants.r_RedlichKwongSoave);
        
        rksm.setAlpha(alpha);
        return rksm;
    }
    
    public static CubicAlpha twoSimTassone(){
        CubicAlpha tst = new CubicAlpha();
        
        tst.setName(EOSNames.TwuSimTassone);
        tst.setAlpha(new Twu());
        
        tst.set_u(2.5d);
        tst.set_w(-1.5d);
        
        tst.setOmega_a(0.470507);
        tst.setOmega_b(0.0740740);
        
        return tst;
    }
    
    public static Cubic vanDerWaals(){
        return new VanDerWaals();
    }
    public static Cubic pengRobinson_Twu() throws Exception{
        throw new Exception("Not suppote yet");
        
    }

    
}
