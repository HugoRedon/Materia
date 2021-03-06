package termo.eos;

import java.lang.reflect.Method;
import java.util.ArrayList;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.Alphas;

/**
 *
 * @author Hugo Redon Rivera
 */
public class EquationsOfState {

    public static ArrayList<EOS> getAllAvailableEquations(){
        ArrayList<EOS> eos = new ArrayList();
    
        for(Method method :EquationsOfState.class.getDeclaredMethods()){
            if( method.getName().equals("getAllAvailableEquations") ||
                    method.getName().equals("getAllAvailableCubicEquations")){
                continue;
            }
            try {
                method.setAccessible(true);
                EOS a = (EOS)method.invoke(null);
                eos.add(a);
            } catch (Exception ex) {
                System.out.println("Error ---------------------------------- "  + ex.getMessage());
            }
        }
        return eos;
    }
    public static ArrayList<Cubic> getAllAvailableCubicEquations(){
        ArrayList<Cubic> cubics = new ArrayList();
        for(EOS eos: getAllAvailableEquations()){
            if(eos.isCubic()){
                cubics.add((Cubic)eos);
            }
        }
        return cubics;
    }
    
    
    
    
    public static Cubic pengRobinson(){
        Cubic pengRobinson = new Cubic();
        pengRobinson.setName(EOSNames.PengRobinson);
        pengRobinson.setU(2);
        pengRobinson.setW(-1);
         pengRobinson.setOmega_a(0.45723553);
        pengRobinson.setOmega_b(0.077796074);
        pengRobinson.setCriticalCompressibilityFactor(0.307);
        return pengRobinson;
    }

    public static IdealGas idealGas(){
        return new IdealGas();
    }
    
    public static Cubic redlichKwongSoave(){
        Cubic rks = new Cubic();
        rks.setU(1);
        rks.setW(0);
        rks.setName(EOSNames.RedlichKwongSoave);
        rks.setCriticalCompressibilityFactor(0.333);
        rks.setOmega_a(0.42748023);
        rks.setOmega_b(0.08664035);
        return rks;
    }
    
    public static Cubic twoSimTassone(){
        Cubic tst = new Cubic();
        
        tst.setName(EOSNames.TwuSimTassone);       
        tst.setU(2.5d);
        tst.setW(-1.5d);
        //TODO: critical compressibilityFactor
        tst.setOmega_a(0.470507);
        tst.setOmega_b(0.0740740);
        
        return tst;
    }
    
    public static Cubic vanDerWaals(){
        //return new VanDerWaals();
        Cubic vanDerWaals = new Cubic();
        vanDerWaals.setU(0);
        vanDerWaals.setW(0);
        vanDerWaals.setOmega_a(27d/64d);
        vanDerWaals.setOmega_b(1d/8d);
        vanDerWaals.setCriticalCompressibilityFactor(0.375);
        vanDerWaals.setName(EOSNames.VanDerWaals);
        
        return vanDerWaals;

    }
}
