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
public class ReasoningForwardChaining extends AlogrithmChaining {
    
	/**
	 * @param a knowledge base kb (in a given formalism)
	 * @param facts (in a given formalism)
	 * @return forwardChaining(ruleBase,factBase), also called the saturation of ruleBase w.r.t. factBase, 
	 * mathematically it computes the minimal fix point of KB from facts)
	 */
	//It's your turn to implement the algorithm, including the methods match() and eval()
	public FactBase forwardChaining(Formalism ruleBase, Formalism factBase){
		FactBase fB = (FactBase) factBase;
                do {                
                factBase = fB;
                    HornRuleBase visited = new HornRuleBase();
                    for (HornRule rule : ((HornRuleBase) ruleBase).getRules()) {                        
                        if (eval(rule, (FactBase) factBase)) {
                            nbMatches++;
                            fB.getFact().addAll(rule.getConclusions());
                            visited.getRules().add(rule);
                            //System.out.println(rule);
                        }
                    }
                    ((HornRuleBase) ruleBase).getRules().removeAll(visited.getRules());
            } while (!((FactBase) factBase).getFact().containsAll(fB.getFact()));
		return fB;
	};
	

	public boolean entailment(Formalism ruleBase, Formalism factBase, Formalism query) {
		FactBase allInferredFacts = forwardChaining(ruleBase, factBase);
		if(allInferredFacts.getFact().contains(query))
			return true;
		else
			return false;
	}

	@Override
	//It's your turn to implement this method
	public int countNbMatches() {
           return 0;
	}
        
        /**
         * 
         */
        private boolean eval(HornRule rule, FactBase factBase) {
            int matches = 0;
            for (Variable condition : rule.getConditions()) {
                for (Variable fact : factBase.getFact()) {
                    if (condition.getNomVariable().equals(fact.getNomVariable())) {
                        matches++;
                    }
                }
            }
            return rule.getConditions().size() == matches;
        }

}
