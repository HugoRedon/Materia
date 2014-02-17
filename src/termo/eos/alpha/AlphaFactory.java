package termo.eos.alpha;

/**
 *
 * @author Hugo Redon Rivera
 */
public class AlphaFactory {
    public static Alpha getVanDerWaalsIndependent(){
        Alpha vdw = new UnitAlpha();
        vdw.setName(AlphaNames.vdwIndependent);
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
        
        CommonAlphaEquation mathiasBelow = new MathiasAlpha();
        
        MathiasAboveTcAlphaEquation mathiasAbove = new MathiasAboveTcAlphaEquation(mathiasBelow);
        
        mathias.setAlphaBelowTc(mathiasBelow);
        mathias.setAlphaAboveTc(mathiasAbove);
        return mathias;     
    }
    public static Alpha getStryjekAndVeraExpression(){
            double r1 = 0.378893;
            double r2=1.4897153;
            double r3 = -0.17131848;
            double r4 = 0.0196554;
                                    
            
        TwoEquationsAlphaExpression stryjek = new TwoEquationsAlphaExpression();    
        
        
        
        CommonAlphaEquation stryjekveraBelow = new StryjekAndVera();
        
        CommonAlphaEquation stryjekVeraAbove = new StryjekAndVera();
        stryjekVeraAbove.setX(0);
       
     
        
        stryjek.setAlphaBelowTc(stryjekveraBelow);
        stryjek.setAlphaAboveTc(stryjekVeraAbove);
        return stryjek;     
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
        
//        public static Alpha getGCEOS_Expression(double A,double B,double C, double D, double k1,double k2 ,double k3, double k4, double k5){
//            GCEOSEquation alpha = new GCEOSEquation();
//            alpha.setName(AlphaNames.GCEOS);
//            
//            alpha.setA(A);
//            alpha.setB(B);
//            alpha.setC(C);
//            alpha.setD(D);
//            alpha.setK1(k1);
//            alpha.setK2(k2);
//            alpha.setK3(k3);
//            alpha.setK4(k4);
//            alpha.setK5(k5);
//            
//            return alpha;
//        }
        
}
