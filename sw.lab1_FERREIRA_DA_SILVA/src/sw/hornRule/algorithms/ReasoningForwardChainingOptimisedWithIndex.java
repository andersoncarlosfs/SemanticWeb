/**
 *
 */
package sw.hornRule.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
public class ReasoningForwardChainingOptimisedWithIndex extends AlogrithmChaining {

    private HashMap<Variable, HashSet<HornRule>> realisableRules = new HashMap<>();
    private HashMap<Variable, HashSet<HornRule>> updatedRules = new HashMap<>();
    private HashMap<Variable, HashSet<Variable>> deducedFacts = new HashMap<>();
    private HashMap<Variable, HashMap<Variable, AtomicInteger>> matchesNumber = new HashMap<>();

    /**
     * @param ruleBase knowledge base ruleBase (in a given formalism)
     * @param factBase base of facts : factBase (in a given formalism)
     * @return the saturation of KB w.r.t. facts (the minimal fix point of KB
     * from facts)
     */
    public FactBase forwardChaining(Formalism ruleBase, Formalism factBase) {
        HornRuleBase rB = new HornRuleBase();
        FactBase fB = new FactBase();
        rB.getRules().addAll(((HornRuleBase) ruleBase).getRules());
        fB.getFact().addAll(((FactBase) factBase).getFact());
        for (Variable fact : ((FactBase) factBase).getFact()) {
            realisableRules.putIfAbsent(fact, new HashSet<>());
            updatedRules.putIfAbsent(fact, new HashSet<>());
            deducedFacts.putIfAbsent(fact, new HashSet<>());
            matchesNumber.putIfAbsent(fact, new HashMap<>());
            fB.getFact().addAll(propagate(fact, rB).getFact());
        }
        for (Variable fact : fB.getFact()) {         
            System.out.println("fact: " + fact + " realisable rules: " + realisableRules.get(fact));
            System.out.println("fact: " + fact + " updated rules: " + updatedRules.get(fact));
            System.out.println("fact: " + fact + " deduced facts: " + deducedFacts.get(fact));
            System.out.println("fact: " + fact + " matches: " + matchesNumber.get(fact));
        }
        realisableRules = new HashMap<>();
        updatedRules = new HashMap<>();
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
            realisableRules.get(fact).add(rule);
            HashSet<Variable> conditions = new HashSet<>(rule.getConditions());
            conditions.remove(fact);
            if (conditions.isEmpty()) {
                deducedFacts.get(fact).addAll(rule.getConclusions());
                ruleBase.getRules().remove(rule);
                fB.getFact().addAll(rule.getConclusions());
            } else {
                updatedRules.get(fact).add(rule);
                rule.getConditions().remove(fact);
            }
        }
        FactBase nfB = new FactBase();
        nfB.getFact().addAll(fB.getFact());
        for (Variable f : fB.getFact()) {
            realisableRules.putIfAbsent(f, new HashSet<>());
            updatedRules.putIfAbsent(f, new HashSet<>());
            deducedFacts.putIfAbsent(f, new HashSet<>());
            matchesNumber.putIfAbsent(f, new HashMap<>());
            nfB.getFact().addAll(propagate(f, ruleBase).getFact());
            realisableRules.get(fact).addAll(realisableRules.get(f));
            updatedRules.get(fact).addAll(updatedRules.get(f));
            deducedFacts.get(fact).addAll(deducedFacts.get(f));
            for (Map.Entry<Variable, AtomicInteger> entry : matchesNumber.get(f).entrySet()) {
                matchesNumber.get(fact).putIfAbsent(entry.getKey(), new AtomicInteger(0));
                matchesNumber.get(fact).get(entry.getKey()).getAndAdd(entry.getValue().get());
            }
            realisableRules.remove(f);
            updatedRules.remove(f);
            deducedFacts.remove(f);
            matchesNumber.remove(f);
        }
        return nfB;
    }

    public HornRuleBase inRuleConditions(Variable fact, HornRuleBase ruleBase) {
        HornRuleBase rB = new HornRuleBase();
        for (HornRule rule : ruleBase.getRules()) {
            if (rule.getConditions().contains(fact)) {
                matchesNumber.get(fact).putIfAbsent(fact, new AtomicInteger(0));
                matchesNumber.get(fact).get(fact).incrementAndGet();
                rB.getRules().add(rule);
            }
        }
        return rB;
    }

}
