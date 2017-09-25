/**
 *
 */
package sw.hornRule.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

    /**
     *
     */
    /*
    class RuleComparator implements java.util.Comparator<HornRule> {

        @Override
        public int compare(HornRule o1, HornRule o2) {
            return Integer.compare(o1.getConditions().size(), o2.getConditions().size());
        }

    }

    private RuleComparator comparator = new RuleComparator();
     */
    private HashMap<Variable, HashSet<HornRule>> index = new HashMap<>();

    /*
    private LinkedHashSet<Variable> propagationOrder = new LinkedHashSet<>();
    private HashMap<Variable, HashSet<String>> realisableRules = new HashMap<>();
    private HashMap<Variable, HashSet<String>> updatedRules = new HashMap<>();
    private HashMap<Variable, HashSet<Variable>> deducedFacts = new HashMap<>();
    private HashMap<Variable, HashMap<Variable, AtomicInteger>> matchesNumber = new HashMap<>();
     */
    /**
     * @param ruleBase knowledge base ruleBase (in a given formalism)
     * @param factBase base of facts : factBase (in a given formalism)
     * @return the saturation of KB w.r.t. facts (the minimal fix point of KB
     * from facts)
     */
    public FactBase forwardChaining(Formalism ruleBase, Formalism factBase) {
        HornRuleBase rB = new HornRuleBase();
        rB.getRules().addAll(((HornRuleBase) ruleBase).getRules());
        for (HornRule rule : rB.getRules()) {
            for (Variable condition : rule.getConditions()) {
                index.putIfAbsent(condition, new HashSet<>());
                index.get(condition).add(rule);
            }
        }

        for (Map.Entry<Variable, HashSet<HornRule>> entry : index.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

        FactBase fB = new FactBase();
        fB.getFact().addAll(((FactBase) factBase).getFact());
        for (Variable fact : ((FactBase) factBase).getFact()) {
            /*
            propagationOrder.add(fact);
            realisableRules.putIfAbsent(fact, new HashSet<>());
            updatedRules.putIfAbsent(fact, new HashSet<>());
            deducedFacts.putIfAbsent(fact, new HashSet<>());
            matchesNumber.putIfAbsent(fact, new HashMap<>());
             */
            fB.getFact().addAll(propagate(fact).getFact());
        }
        /*
        for (Variable fact : propagationOrder) {
            System.out.println("fact: " + fact + " realisable rules: " + realisableRules.get(fact));
            System.out.println("fact: " + fact + " updated rules: " + updatedRules.get(fact));
            System.out.println("fact: " + fact + " deduced facts: " + deducedFacts.get(fact));
            System.out.println("fact: " + fact + " matches: " + matchesNumber.get(fact));
        }
         */
        index = new HashMap<>();
        /*
        propagationOrder = new LinkedHashSet<>();
        realisableRules = new HashMap<>();
        updatedRules = new HashMap<>();
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
    public int countNbMatches() {
        // TODO Auto-generated method stub
        return 0;
    }

    public FactBase propagate(Variable fact) {
        FactBase fB = new FactBase();
        HashSet<HornRule> rules = new HashSet<>();
        for (HornRule rule : index.getOrDefault(fact, rules)) {
            /*
            realisableRules.get(fact).add(rule.toString());
            matchesNumber.get(fact).putIfAbsent(fact, new AtomicInteger(0));
            matchesNumber.get(fact).get(fact).getAndIncrement();
             */
            rule.getConditions().remove(fact);
            if (rule.getConditions().isEmpty()) {
                rules.add(rule);
                //deducedFacts.get(fact).addAll(rule.getConclusions());
                fB.getFact().addAll(rule.getConclusions());
            } else {
                //updatedRules.get(fact).add(rule.toString());
            }
        }
        index.getOrDefault(fact, rules).removeAll(rules);
        FactBase nfB = new FactBase();
        nfB.getFact().addAll(fB.getFact());
        for (Variable f : fB.getFact()) {
            /*
            propagationOrder.add(f);
            realisableRules.putIfAbsent(f, new HashSet<>());
            updatedRules.putIfAbsent(f, new HashSet<>());
            deducedFacts.putIfAbsent(f, new HashSet<>());
            matchesNumber.putIfAbsent(f, new HashMap<>());
             */
            nfB.getFact().addAll(propagate(f).getFact());
            /*
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
             */
        }
        return nfB;
    }

}
