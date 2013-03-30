package termo.eos.alpha;

/**
 *
 * @author Hugo Redon Rivera
 */
public class AlphaFactory {

    public static Alpha getSoaveExpression(){
        double r1 = 0.48508;
        double r2=1.55171;
        double r3 = -0.15613;
        double r4 = 0.0;
        
        CommonAlphaEquation soave = new CommonAlphaEquation();
        
        soave.setName(AlphaNames.Soave); 
        soave.setR1(r1);
        soave.setR2(r2);
        soave.setR3(r3);
        soave.setR4(r4);
        
        soave.setX(0);  
        return soave;     
    }
    public static Alpha getPengAndRobinsonExpression(){
        CommonAlphaEquation pr = new CommonAlphaEquation();
        
        pr.setName(AlphaNames.PengAndRobinson);
        
        pr.setR1(0.37464);
        pr.setR2(1.54226);
        pr.setR3(-0.2699);
        pr.setR4(0);
        
        pr.setX(0);
        return pr;    
    }
    
    public static Alpha getMathiasExpression(){
        TwoEquationsAlphaExpression mathias = new TwoEquationsAlphaExpression();    
        
        mathias.setName(AlphaNames.Mathias);
        
        CommonAlphaEquation mathiasBelow = new CommonAlphaEquation();
   
        mathiasBelow.setR1(0.48508);
        mathiasBelow.setR2(1.55171);
        mathiasBelow.setR3(-0.15613);
        mathiasBelow.setR4(0);
        
        mathiasBelow.setX(-1);  
        
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
        
        stryjek.setName(AlphaNames.StryjekAndVera);
        
        CommonAlphaEquation stryjekveraBelow = new CommonAlphaEquation();
   
        stryjekveraBelow.setR1(r1);
        stryjekveraBelow.setR2(r2);
        stryjekveraBelow.setR3(r3);
        stryjekveraBelow.setR4(r4);
        
        stryjekveraBelow.setX(1);  
        
        CommonAlphaEquation stryjekVeraAbove = new CommonAlphaEquation();
        
        stryjekVeraAbove.setR1(r1);
        stryjekVeraAbove.setR2(r2);
        stryjekVeraAbove.setR3(r3);
        stryjekVeraAbove.setR4(r4);
        
        stryjekveraBelow.setX(0);  
        
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
        public Alpha getMathiasAndCopemanExpression(){
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
        
        public Alpha getGCEOS_Expression(double A,double B,double C, double D, double k1,double k2 ,double k3, double k4, double k5){
            GCEOSEquation alpha = new GCEOSEquation();
            alpha.setName(AlphaNames.GCEOS);
            
            alpha.setA(A);
            alpha.setB(B);
            alpha.setC(C);
            alpha.setD(D);
            alpha.setK1(k1);
            alpha.setK2(k2);
            alpha.setK3(k3);
            alpha.setK4(k4);
            alpha.setK5(k5);
            
            return alpha;
        }
        
}
