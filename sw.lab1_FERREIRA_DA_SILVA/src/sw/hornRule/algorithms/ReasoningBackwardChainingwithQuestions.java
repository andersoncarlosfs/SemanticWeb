/**
 *
 */
package sw.hornRule.algorithms;

import java.util.Scanner;
import sw.hornRule.models.FactBase;
import sw.hornRule.models.Formalism;
import sw.hornRule.models.HornRule;
import sw.hornRule.models.HornRuleBase;
import sw.hornRule.models.Variable;

/**
 * @author Anderson Carlos Ferreira da Silva
 *
 */
public class ReasoningBackwardChainingwithQuestions extends AlogrithmChaining {

    private final Scanner CONSOLE;
    
    public ReasoningBackwardChainingwithQuestions(Scanner scanner) {
        CONSOLE = scanner;
    }
    
    @Override
    public boolean entailment(Formalism ruleBase, Formalism factBase, Formalism query) {
        // TODO To complete
        // When a literal (i.e. a variable or its negation) cannot be replied by deductive reasoning, 
        // it will be asked to users to give an answer (if the liter holds according to the user)
        return backwardChaining(ruleBase, factBase, query);
    }

    private boolean backwardChaining(Formalism ruleBase, Formalism factBase, Formalism query) {
        FactBase fB = new FactBase(((FactBase) factBase).getFact());
        if (match((Variable) query, fB)) {
            return true;
        }
        for (HornRule rule : ((HornRuleBase) ruleBase).getRules()) {
            boolean conclusion = false;
            if (rule.getConclusions().contains((Variable) query)) {
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
        return ask((Variable) query);
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

    /**
     *
     * @param query
     * @return
     */
    private boolean ask(Variable query) {
        System.out.println("Is " + query + " a fact? (Y/N): ");
        return CONSOLE.nextLine().equalsIgnoreCase("y");
    }

}
