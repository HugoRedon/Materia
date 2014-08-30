package termo.optimization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import termo.eos.mixingRule.VDWMixingRule;

public class ParametersNames {

	@Test
	public void test() {
		VDWMixingRule vd = new VDWMixingRule();
		String k =vd.getParameterName(0);
		assertEquals("k",k);
	}

}
