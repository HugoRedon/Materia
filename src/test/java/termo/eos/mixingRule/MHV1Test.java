package termo.eos.mixingRule;

import static org.junit.Assert.*;

import org.junit.Test;

import termo.eos.Cubic;
import termo.eos.EquationsOfState;


public class MHV1Test {
	@Test
	public void testsrkQ1(){
		Cubic c = EquationsOfState.redlichKwongSoave();
		double L = c.calculateL(1.235, 1);
		System.out.println(L);
		assertEquals(-0.593,L,1e-3);
	}
	
	@Test public void testprq1(){
		Cubic c = EquationsOfState.pengRobinson();
		double L = c.calculateL(1.235, 1);
		System.out.println(L);
		assertEquals(-0.53,L,1e-2);
	}
	@Test public void testvdwq1(){
		Cubic c = EquationsOfState.vanDerWaals();
		double L = c.calculateL(1.235, 1);
		System.out.println(L);
		assertEquals(-0.80/*-0.85*/,L,1e-2);//
	}
}
