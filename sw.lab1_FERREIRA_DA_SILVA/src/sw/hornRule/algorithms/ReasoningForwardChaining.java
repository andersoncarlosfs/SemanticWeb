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
public class ReasoningForwardChaining extends AlogrithmChaining {

    /*
    private Integer interactionNumber = 0;
   private HashMap<Integer, HashSet<HornRule>> realisableRules = new HashMap<>();
    private HashMap<Integer, HashSet<Variable>> deducedFacts = new HashMap<>();
    private HashMap<Integer, HashMap<Variable, AtomicInteger>> matchesNumber = new HashMap<>();
     */
    /**
     * @param ruleBase knowledge base kb (in a given formalism)
     * @param factBase (in a given formalism)
     * @return forwardChaining(ruleBase,factBase), also called the saturation of
     * ruleBase w.r.t. factBase, mathematically it computes the minimal fix
     * point of KB from facts)
     */
    //It's your turn to implement the algorithm, including the methods match() and eval()
    public FactBase forwardChaining(Formalism ruleBase, Formalism factBase) {
        HornRuleBase rB = new HornRuleBase();
        FactBase fB = new FactBase();
        FactBase nfB = new FactBase();
        rB.getRules().addAll(((HornRuleBase) ruleBase).getRules());
        fB.getFact().addAll(((FactBase) factBase).getFact());
        nfB.getFact().addAll(((FactBase) factBase).getFact());
        do {
            fB.getFact().addAll(nfB.getFact());
            /*
            HornRuleBase rules = new HornRuleBase();
            realisableRules.putIfAbsent(interactionNumber, new HashSet<>());
            deducedFacts.putIfAbsent(interactionNumber, new HashSet<>());
            matchesNumber.putIfAbsent(interactionNumber, new HashMap<>());
             */
            for (HornRule rule : rB.getRules()) {
                if (eval(rule, (FactBase) fB)) {
                    nfB.getFact().addAll(rule.getConclusions());
                    //rules.getRules().add(rule);
                    //realisableRules.get(interactionNumber).add(rule);
                    /*
                    for (Variable conclusion : rule.getConclusions()) {
                        if (!((FactBase) fB).getFact().contains(conclusion)) {
                            //deducedFacts.get(interactionNumber).add(conclusion);
                        }
                    }
                     */
                }
            }
            //rB.getRules().removeAll(rules.getRules());
            //interactionNumber++;
        } while (!((FactBase) fB).getFact().containsAll(nfB.getFact()));
        /*
        for (int i = 0; i < interactionNumber; i++) {
            System.out.println("iteraction: " + i + " realisable rules: " + realisableRules.get(i));
            System.out.println("iteraction: " + i + " deduced facts: " + deducedFacts.get(i));
            System.out.println("iteraction: " + i + " matches: " + matchesNumber.get(i));
        }        
        interactionNumber = 0;
        realisableRules = new HashMap<>();
        deducedFacts = new HashMap<>();
        matchesNumber = new HashMap<>();
         */
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
     * @param hornRule
     * @param factBase
     * @return
     */
    private boolean eval(HornRule hornRule, FactBase factBase) {
        for (Variable condition : hornRule.getConditions()) {
            if (!match(condition, factBase)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param condition
     * @param factBase
     * @return
     */
    private boolean match(Variable condition, FactBase factBase) {
        /*
        if (factBase.getFact().contains(condition)) {
            matchesNumber.get(interactionNumber).putIfAbsent(condition, new AtomicInteger(0));
            matchesNumber.get(interactionNumber).get(condition).incrementAndGet();
            return true;
        }
        return false;
         */
        return factBase.getFact().contains(condition);
    }

}
