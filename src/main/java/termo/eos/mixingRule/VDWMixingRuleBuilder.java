/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termo.eos.mixingRule;

import java.util.ArrayList;
import termo.binaryParameter.InteractionParameter;
import termo.component.Component;
import termo.eos.Cubic;
import termo.eos.alpha.Alpha;
import termo.phase.Phase;


public class VDWMixingRuleBuilder {
    private Cubic equationOfState;
    private Alpha alpha;
    private ArrayList<Component> components;
    private Phase phase;
    private InteractionParameter interactionParameters;

    public VDWMixingRuleBuilder() {
    }

    public VDWMixingRuleBuilder setEquationOfState(Cubic equationOfState) {
	this.equationOfState = equationOfState;
	return this;
    }

    public VDWMixingRuleBuilder setAlpha(Alpha alpha) {
	this.alpha = alpha;
	return this;
    }

    public VDWMixingRuleBuilder setComponents(ArrayList<Component> components) {
	this.components = components;
	return this;
    }

    public VDWMixingRuleBuilder setPhase(Phase phase) {
	this.phase = phase;
	return this;
    }

    public VDWMixingRuleBuilder setInteractionParameters(InteractionParameter k) {
	this.interactionParameters = k;
	return this;
    }

    public VDWMixingRule createVDWMixingRule() {
	return new VDWMixingRule();
    }
    
}
