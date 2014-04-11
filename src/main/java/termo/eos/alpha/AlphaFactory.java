package termo.eos.alpha;

import java.lang.reflect.Method;
import java.util.ArrayList;
import termo.eos.alpha.commonaAlphaEquationsImplementation.MathiasAlpha;
import termo.eos.alpha.commonaAlphaEquationsImplementation.PengAndRobinsonAlpha;
import termo.eos.alpha.commonaAlphaEquationsImplementation.SoaveAlpha;
import termo.eos.alpha.commonaAlphaEquationsImplementation.StryjekAndVera;

/**
 *
 * @author Hugo Redon Rivera
 */
public class AlphaFactory {
    
    public static ArrayList<Alpha> getAllAvailableAlphas() {
        ArrayList<Alpha> alphas = new ArrayList();
        Method[] methods = AlphaFactory.class.getDeclaredMethods();
        for (Method method : methods){
            if( method.getName().equals("getAllAvailableAlphas")){
                continue;
            }
            try {
                method.setAccessible(true);
                Alpha a = (Alpha)method.invoke(null);
                alphas.add(a);
            } catch (Exception ex) {
                System.out.println("Error ---------------------------------- "  + ex.getMessage());
            }
        }
        return alphas;
    }
    public static Alpha getVanDerWaalsIndependent(){
        Alpha vdw = new UnitAlpha();   
        return vdw;
        
    }
    
    public static Alpha getSoaveExpression(){
        CommonAlphaEquation soave = new SoaveAlpha();
        return soave;     
    }
    public static Alpha getPengAndRobinsonExpression(){
        CommonAlphaEquation pr = new PengAndRobinsonAlpha();
        return pr;    
    }
    
    public static Alpha getMathiasExpression(){
        TwoEquationsAlphaExpression mathias = new TwoEquationsAlphaExpression();    
        mathias.setName(AlphaNames.Mathias);
        CommonAlphaEquation mathiasBelow = new MathiasAlpha();
        
        MathiasAboveTcAlphaEquation mathiasAbove = new MathiasAboveTcAlphaEquation(mathiasBelow);
        
        mathias.setAlphaBelowTc(mathiasBelow);
        mathias.setAlphaAboveTc(mathiasAbove);
        return mathias;     
    }
    public static Alpha getStryjekAndVeraExpression(){
        TwoEquationsAlphaExpression stryjek = new TwoEquationsAlphaExpression();    
        stryjek.setName(AlphaNames.StryjekAndVera);           
        CommonAlphaEquation stryjekveraBelow = new StryjekAndVera();
        CommonAlphaEquation stryjekVeraAbove = new StryjekAndVera();
        stryjekVeraAbove.setX(0);        
        stryjek.setAlphaBelowTc(stryjekveraBelow);
        stryjek.setAlphaAboveTc(stryjekVeraAbove);
        return stryjek;     
    }
    
    public static Alpha getAdachiAndLu(){
        AdachiAndLuAlphaExpresion lu= new AdachiAndLuAlphaExpresion();
        lu.setName(AlphaNames.AdachiAndLu);
        return lu;
    }
    public static Alpha getSoave2Parameters(){
        Soave2Parameters soave = new Soave2Parameters();
        soave.setName(AlphaNames.Soave2);
        return soave;
    }
    public static Alpha getMelhemEtAl(){
        MelhemEtAl melhem = new MelhemEtAl();
        melhem.setName(AlphaNames.MelhemEtAl);
        return melhem;
    }
    public static Alpha getAndroulakisEtAl(){
        TwoEquationsAlphaExpression androulakistwo = new TwoEquationsAlphaExpression();
        AndroulakisEtAl androulakis = new AndroulakisEtAl(); 
        androulakistwo.setAlphaBelowTc(androulakis);
        AndroulakisEtAlAboveTc above = new AndroulakisEtAlAboveTc();
        androulakistwo.setAlphaAboveTc(above);
        
        androulakistwo.setName(AlphaNames.AndroulakisEtAl);
        return androulakistwo;
    }
    public static Alpha getYuAndLu(){
        YuAndLu yu = new YuAndLu();
        YuAndLuAboveTc above = new YuAndLuAboveTc();
        
        TwoEquationsAlphaExpression two = new TwoEquationsAlphaExpression();
        
        two.setAlphaBelowTc(yu);
        two.setAlphaAboveTc(above);
        
        two.setName(AlphaNames.YuAndLu);
        return two;
    }
    
        public static Alpha getTwuExpression(){
            Alpha twu = new TwuEquation();
            twu.setName(AlphaNames.Twu);
            return twu;
        }
        
        public static Alpha getGeneralTwuEquation(){
            TwoEquationsAlphaExpression twu = new TwoEquationsAlphaExpression();
            twu.setName(AlphaNames.GeneralTwu);
            
            GeneralTwuEquation twuBelow = new GeneralTwuEquation();
            
            twuBelow.setL0(0.196545);
            twuBelow.setM0(0.906437);
            twuBelow.setN0(1.26251);
            twuBelow.setL1(0.704001);
            twuBelow.setM1(0.790407);
            twuBelow.setN1(2.13076);
            
            GeneralTwuEquation twuAbove = new GeneralTwuEquation();
            
            twuAbove.setL0(0.358826);
            twuAbove.setM0(4.234778);
            twuAbove.setN0(-0.2);
            twuAbove.setL1(0.0206444);
            twuAbove.setM1(1.22942);
            twuAbove.setN1(-0.8);
            
            twu.setAlphaAboveTc(twuAbove);
            twu.setAlphaBelowTc(twuBelow);
            return twu;
        }
        public static Alpha getMathiasAndCopemanExpression(){
             TwoEquationsAlphaExpression mathiasAndCopeman = new TwoEquationsAlphaExpression();
             mathiasAndCopeman.setName(AlphaNames.Mathias_Copeman);
             
             MathiasCopemanEquation belowtr = new MathiasCopemanEquation();
             belowtr.setX(1);
             
             MathiasCopemanEquation abovetr = new MathiasCopemanEquation();
             abovetr.setX(0);
             
             mathiasAndCopeman.setAlphaAboveTc(abovetr);
             mathiasAndCopeman.setAlphaBelowTc(belowtr);
             
             return mathiasAndCopeman;             
        }
        
        public static Alpha getGCEOS_Expression(){
            GCEOSEquation alpha = new GCEOSEquation();
            alpha.setName(AlphaNames.GCEOS);
            
//            alpha.setA(A);
//            alpha.setB(B);
//            alpha.setC(C);
//            alpha.setD(D);
//            alpha.setK1(k1);
//            alpha.setK2(k2);
//            alpha.setK3(k3);
//            alpha.setK4(k4);
//            alpha.setK5(k5);
            
            return alpha;
        }
        
}
