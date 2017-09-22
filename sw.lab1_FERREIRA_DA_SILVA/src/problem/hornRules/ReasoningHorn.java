package problem.hornRules;

import java.util.Scanner;
import sw.hornRule.algorithms.*;
import sw.hornRule.models.*;

public class ReasoningHorn {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AlogrithmChaining reasoner = new ReasoningBackwardChainingwithQuestions(scanner);
        Tutorial1 pb = new Tutorial1();
        HornRuleBase kb = pb.getRuleBase();
        FactBase fb = pb.getFactBase();

        System.out.println("The rule base is:");
        for (HornRule r : kb.getRules()) {
            System.out.println(r);
        }

        System.out.println("");

        System.out.println("The fact base is:");
        System.out.println(fb);

        fb.getFact().remove(new Variable("boat"));

        /*
        //Display all facts inferred by the given knowledge base kb and fact base fb
        HashSet<Variable> inferredAllFacts = ((ReasoningForwardChainingOptimised) reasoner).forwardChaining(kb, fb).getFact();
        System.out.println("All the inferred facts are:");
        for (Variable s : inferredAllFacts) {
            System.out.println(s);
        }
         */
        Variable q = new Variable("transoceanic_race");
        if (reasoner.entailment(kb, fb, q)) {
            System.out.println("\nYes, the query is entailed by the given rules and facts");
        } else {
            System.out.println("\nNo, the query is not entailed based on the given rules and facts");
        }

        scanner.close();

    }

}
