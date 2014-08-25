package termo.matter;

import static org.junit.Assert.*;

import org.junit.Test;

import termo.component.Compound;
import termo.eos.EquationsOfState;
import termo.eos.alpha.Alphas;

public class HeterogeneousSubstanceTest {
	HeterogeneousSubstance a;
	HeterogeneousSubstance b;
	HeterogeneousSubstance c;
	public HeterogeneousSubstanceTest(){
		a = new HeterogeneousSubstance(EquationsOfState.pengRobinson(),
						Alphas.getStryjekAndVeraExpression(),
						new Compound("Test")
						);
		b=new HeterogeneousSubstance(EquationsOfState.pengRobinson(),
				Alphas.getStryjekAndVeraExpression(),
				new Compound("Test")
				);
		c=new HeterogeneousSubstance(EquationsOfState.pengRobinson(),
				Alphas.getStryjekAndVeraExpression(),
				new Compound("Test")
				);
	}
	@Test
	public void testHashCodeReflexivity() {
		boolean reflexivity=a.equals(a);
		assertEquals(true,reflexivity);
	}
	@Test 
	public void testHashCodeSymmetry(){
		boolean symmetry = a.equals(b);
		boolean s = b.equals(a);
		
		boolean result = symmetry && s;
		assertEquals(true,result);
	}
	
	@Test 
	public void testHashCodeTransitivity(){
		boolean ab = a.equals(b);
		boolean bc = b.equals(c);
		boolean ac = a.equals(c);
		
		boolean result = ab && bc && ac;
		assertEquals(true,result);
	}
	
	@Test
	public void testHashCodeConsistency(){
		Integer ha=a.hashCode();
		Integer hb=b.hashCode();
		
		boolean res=ha.equals(hb);
		assertEquals(true,res);
	}

}
