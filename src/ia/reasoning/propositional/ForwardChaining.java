package ia.reasoning.propositional;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 23:56:01
 * To change this template use File | Settings | File Templates.
 */
public class ForwardChaining extends ChainingAlgo {
    /*
procedure FORWARD-CHAIN'S, p)
if there is a sentence in KB that is a renaming of p then return
Add p to KB
for each (p\ A . . . A p,, => q) in KB such that for some i, UNIFY(P,,/?) = 0 succeeds do
FlND-AND-lNFER(A"B, [p\, ... ,p-,-\,pM,. .. ,p,,],q,9)
end
procedure FIND-AND-lNFER'fi, premises, conclusion, 0)
if premises = [ \ then
FORWARD-CHAIN'S, SuBST(0, conclusion))
else for each/;' in KB such that UNlFY(p', SUBST(0, FlRST( premises))) = #2 do
FlND-AND-lNFER(/fS, REST(premises), conclusion, COMPOSE^, #2))
end
    
     */

    @Override
    public void chaining(PropositionLogicProblem problem) {
        while (problem.getBR().size() > 0) {
            Clause clause = searchRuleMatched(problem);
            if (clause != null) {
                System.out.println("use clause:");
                System.out.println(clause);
                problem.getBR().remove(clause);
                problem.addFact(clause.getConclusion());
                System.out.println("BF");
                System.out.println(printVector(problem.getBF()));
                if (isContain(problem.getBF(), problem.getF())) {
                    System.out.println("success");
                    return;
                }
            } else {
                System.out.println("fail");
                return;
            }
        }
    }

    

    private boolean isContain(Vector<Term> a, Term t) {
        int index = 0;
        int max = a.size();
        int min = 0;

        while (max > min) {
            int mid = (max + min) / 2;
            if (a.get(mid).compareTo(t) == 0)
                return true;
            if (a.get(mid).compareTo(t) > 0) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        if(min>=a.size() || max<0)
            return false;
        if (a.get(min).compareTo(t) == 0)
            return true;
        else
            return false;
    }

    private boolean isContain(Vector<Term> a, Vector<Term> b) {
        if (a.size() < b.size())
            return false;

        for (int i = 0; i < b.size(); i++) {
            if(!isContain(a,b.get(i)))
                return false;
        }
        return true;
    }

    private Clause searchRuleMatched(PropositionLogicProblem problem) {
        for (int i = 0; i < problem.getBR().size(); i++) {
            Clause claus = problem.getBR().get(i);
            if (isContain(problem.getBF(), claus.getPropositions()))
                return claus;
        }
        return null;
    }

    public static void main(String args[]) {
        try {
            PropositionLogicProblem problem = PropositionLogicProblemFileReader.read("/home/thuan/sandbox/ia-tp-java/kb/proposition/1");
            System.out.println(problem);
            ForwardChaining fc = new ForwardChaining();
            fc.chaining(problem);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
