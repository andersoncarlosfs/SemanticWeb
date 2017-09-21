/**
 *
 */
package sw.hornRule.algorithms;

import sw.hornRule.models.FactBase;
import sw.hornRule.models.Formalism;
import sw.hornRule.models.Variable;

/**
 * @author  Anderson Carlos Ferreira da Silva
 *
 */
public class ReasoningBackwardChaining extends AlogrithmChaining {

    public boolean entailment(Formalism ruleBase, Formalism factBase, Formalism query) {
        return backwardChaining(ruleBase, factBase, query);
    }

    private boolean backwardChaining(Formalism ruleBase, Formalism factBase,
            Formalism query) {
        if (true) {

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
        for (Variable fact : factBase.getFact()) {
            if (fact.getNomVariable().equals(query.getNomVariable())) {
                return true;
            }
        }
        return false;
    }

}
