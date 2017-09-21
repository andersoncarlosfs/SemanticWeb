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
public class ReasoningBackwardChaining extends AlogrithmChaining {

    public boolean entailment(Formalism ruleBase, Formalism factBase, Formalism query) {
        return backwardChaining(ruleBase, factBase, query);
    }

    private boolean backwardChaining(Formalism ruleBase, Formalism factBase, Formalism query) {
        FactBase fB = new FactBase(((FactBase) factBase).getFact());
        if (match((Variable) query, fB)) {
            return true;
        }
        for (HornRule rule : ((HornRuleBase) ruleBase).getRules()) {
            boolean conclusion = false;
            if (rule.getConclusions().contains(query)) {
                conclusion = true;
                for (Variable condition : rule.getConditions()) {
                    if (!backwardChaining(ruleBase, fB, condition)) {
                        conclusion = false;
                        break;
                    }
                }
            }
            if (conclusion) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int countNbMatches() {
        // TODO To complete
        return 0;
    }

    /**
     *
     */
    private boolean match(Variable query, FactBase factBase) {
        return factBase.getFact().contains(query);
    }

}
