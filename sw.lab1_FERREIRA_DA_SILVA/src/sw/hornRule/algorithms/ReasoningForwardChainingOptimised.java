/**
 * 
 */
package sw.hornRule.algorithms;

import sw.hornRule.models.FactBase;
import sw.hornRule.models.Formalism;
import sw.hornRule.models.HornRule;
import sw.hornRule.models.HornRuleBase;
import sw.hornRule.models.Variable;

/**
 * @author  <Your name>
 *
 */
public class ReasoningForwardChainingOptimised extends AlogrithmChaining {
	
	/**
	 * @param a knowledge base ruleBase (in a given formalism)
	 * @param a base of facts : factBase (in a given formalism)
	 * @return the saturation of KB w.r.t. facts (the minimal fix point of KB from facts)
	 */
	public FactBase forwardChainingOptimise(Formalism ruleBase, Formalism factBase){
            FactBase fB = new FactBase();
            fB.getFact().addAll(((FactBase) factBase).getFact());
            for (Variable fact : ((FactBase) factBase).getFact()) {
                fB.getFact().addAll(propagate(fact, (HornRuleBase) ruleBase).getFact());
            }
            return fB;
	};

 
	public boolean entailment(Formalism ruleBase, Formalism factBase, Formalism query) {
		FactBase allInferredFacts = forwardChainingOptimise(ruleBase, factBase);              
                if(allInferredFacts.getFact().contains(query))
			return true;
		else
			return false;
	}

	@Override
	public int countNbMatches() {
		// TODO Auto-generated method stub
		return 0;
	}
        
        public FactBase propagate(Variable fact, HornRuleBase ruleBase) {
            FactBase fB = new FactBase();
            for (HornRule rule : inRuleConditions(fact, ruleBase).getRules()) {
                Variable v = null;
                for (Variable condition : rule.getConditions()) {
                    if (condition.getNomVariable().equals(fact.getNomVariable())) {
                        v = condition;
                        break;
                    }
                }
                rule.getConditions().remove(v);
                if (rule.getConditions().isEmpty()) {
                    ruleBase.getRules().remove(rule);
                    fB.getFact().addAll(rule.getConclusions());
                }
            }
            FactBase factBase = new FactBase(fB.getFact());
            for (Variable f : fB.getFact()) {
                factBase.getFact().addAll(propagate(f, ruleBase).getFact());
            }
            return factBase;
        }
        
        public HornRuleBase inRuleConditions(Variable fact, HornRuleBase ruleBase) {
            HornRuleBase rB = new HornRuleBase();
            for (HornRule rule : ruleBase.getRules()) {
                for (Variable condition : rule.getConditions()) {
                    if (condition.getNomVariable().equals(fact.getNomVariable())) {
                        rB.getRules().add(rule);
                        break;
                    }
                }
            }
            return rB;
        }

}
