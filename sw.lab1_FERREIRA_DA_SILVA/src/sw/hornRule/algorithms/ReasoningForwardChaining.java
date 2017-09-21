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
 * @author Anderson Carlos Ferreira da Silva
 *
 */
public class ReasoningForwardChaining extends AlogrithmChaining {

    /**
     * @param ruleBase knowledge base kb (in a given formalism)
     * @param factBase (in a given formalism)
     * @return forwardChaining(ruleBase,factBase), also called the saturation of
     * ruleBase w.r.t. factBase, mathematically it computes the minimal fix
     * point of KB from facts)
     */
    //It's your turn to implement the algorithm, including the methods match() and eval()
    public FactBase forwardChaining(Formalism ruleBase, Formalism factBase) {
        FactBase fB = (FactBase) factBase;
        do {
            factBase = fB;
            HornRuleBase visited = new HornRuleBase();
            for (HornRule rule : ((HornRuleBase) ruleBase).getRules()) {
                if (eval(rule, (FactBase) factBase)) {
                    fB.getFact().addAll(rule.getConclusions());
                    visited.getRules().add(rule);
                }
            }
            ((HornRuleBase) ruleBase).getRules().removeAll(visited.getRules());
        } while (!((FactBase) factBase).getFact().containsAll(fB.getFact()));
        return fB;
    }

    @Override
    public boolean entailment(Formalism ruleBase, Formalism factBase, Formalism query) {
        FactBase allInferredFacts = forwardChaining(ruleBase, factBase);
        return allInferredFacts.getFact().contains((Variable) query);
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
        return rule.getConditions().containsAll(factBase.getFact());
    }

}
