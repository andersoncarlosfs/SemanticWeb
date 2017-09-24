/**
 *
 */
package sw.hornRule.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import sw.hornRule.models.FactBase;
import sw.hornRule.models.Formalism;
import sw.hornRule.models.HornRule;
import sw.hornRule.models.HornRuleBase;
import sw.hornRule.models.Variable;

/**
 * @author Anderson Carlos Ferreira da Silva
 *
 */
public class ReasoningForwardChainingOptimised extends AlogrithmChaining {

    private Integer interactionNumber = 0;
    private HashMap<Integer, HashSet<HornRule>> realisableRules = new HashMap<>();
    private HashMap<Integer, HashSet<Variable>> deducedFacts = new HashMap<>();
    private HashMap<Integer, HashMap<Variable, AtomicInteger>> matchesNumber = new HashMap<>();

    /**
     * @param ruleBase knowledge base ruleBase (in a given formalism)
     * @param factBase base of facts : factBase (in a given formalism)
     * @return the saturation of KB w.r.t. facts (the minimal fix point of KB
     * from facts)
     */
    public FactBase forwardChaining(Formalism ruleBase, Formalism factBase) {
        FactBase fB = new FactBase();
        fB.getFact().addAll(((FactBase) factBase).getFact());
        for (Variable fact : ((FactBase) factBase).getFact()) {
            realisableRules.putIfAbsent(interactionNumber, new HashSet<>());
            deducedFacts.putIfAbsent(interactionNumber, new HashSet<>());
            matchesNumber.putIfAbsent(interactionNumber, new HashMap<>());
            fB.getFact().addAll(propagate(fact, (HornRuleBase) ruleBase).getFact());
            interactionNumber++;
        }
        interactionNumber = 0;
        realisableRules = new HashMap<>();
        deducedFacts = new HashMap<>();
        matchesNumber = new HashMap<>();
        return fB;
    }

    @Override
    public boolean entailment(Formalism ruleBase, Formalism factBase, Formalism query) {
        FactBase allInferredFacts = forwardChaining(ruleBase, factBase);
        return allInferredFacts.getFact().contains((Variable) query);
    }

    @Override
    public int countNbMatches() {
        // TODO Auto-generated method stub
        return 0;
    }

    public FactBase propagate(Variable fact, HornRuleBase ruleBase) {
        FactBase fB = new FactBase();
        for (HornRule rule : inRuleConditions(fact, ruleBase).getRules()) {
            rule.getConditions().remove(fact);
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
            if (rule.getConditions().contains(fact)) {
                rB.getRules().add(rule);
            }
        }
        return rB;
    }

}
