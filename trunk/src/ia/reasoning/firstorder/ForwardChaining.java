package ia.reasoning.firstorder;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 23:56:01
 * To change this template use File | Settings | File Templates.
 */
public class ForwardChaining extends ChainingAlgo {
    

    public void forwardChaining(Term p) {
        System.out.println();
        System.out.println("Chaining:" + p);
        if (existRenaming(p))
            return;
        else {
            problem.getBF().add(p);
            System.out.println("add to BF: " + p);
            System.out.println("BF:" + printVector(problem.getBF()));


            for (int i = 0; i < problem.getBR().size(); i++) {
                Clause clause = problem.getBR().get(i);
                System.out.println("Verify clause:" + clause);
                for (int j = 0; j < clause.getPropositions().size(); j++) {
                    Term pro = clause.getPropositions().get(j);
                    Substitution sub = unify(pro, p);
                    if (sub != null) {
                        System.out.println("Unify:" + pro + " + " + p + "=" + sub);
                    }
                    if (sub != null) {
                        Vector<Term> premises = (Vector<Term>) clause.getPropositions().clone();
                        Term conclusion = new Term(clause.getConclusion());
                        premises.remove(pro);
                        findAndInfer(premises, conclusion, sub);
                    }
                }
            }
        }

    }

    private void findAndInfer(Vector<Term> premises, Term conclusion, Substitution sub) {
        if (premises.size() == 0) {
            Term p = substitute(conclusion, sub);
            System.out.println("infer:" + p);
            forwardChaining(p);
        } else {
            for (int i = 0; i < problem.getBF().size(); i++) {
                Term pf = problem.getBF().get(i);
                Term ts = substitute(premises.get(0), sub);
                Substitution sub2 = unify(pf, ts);
                if (sub2 != null) {
                    System.out.println("Unify:" + ts + " + " + pf + "=" + sub2);
                    Vector<Term> prem=(Vector<Term>)premises.clone();
                    premises.remove(premises.get(0));
                    findAndInfer(premises, conclusion, sub.compose(sub2));
                }
                if(premises.size()<=0)
                    break;
            }
        }
    }

    private boolean isRenaming(Term t1, Term t2) {
        int numberOfVar = 0;
        if (t1.getPredicate() == t2.getPredicate()) {
            for (int i = 0; i < t1.getParameters().size(); i++) {
                Literal l1 = t1.getParameters().get(i);
                Literal l2 = t2.getParameters().get(i);
                if (l1 instanceof Constant && l2 instanceof Constant && l1 == l2) {
                    continue;
                } else if (l1 instanceof Variable && l2 instanceof Variable) {
                    numberOfVar++;
                    if (numberOfVar > 1)
                        return false;
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean existRenaming(Term p) {
        for (int i = 0; i < problem.getBF().size(); i++) {
            if (isRenaming(problem.getBF().get(i), p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void chaining() {

        for (int i = 0; i < problem.getBaseFactInitial().size(); i++) {

            Term term = problem.getBaseFactInitial().get(i);

            forwardChaining(term);
        }
        System.out.println();
        System.out.println("BF:"+ printVector(problem.getBF()));
        /*
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
        */
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
        if (min >= a.size() || max < 0)
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
            if (!isContain(a, b.get(i)))
                return false;
        }
        return true;
    }

    private Clause searchRuleMatched(FirstOrderLogicProblem problem) {
        for (int i = 0; i < problem.getBR().size(); i++) {
            Clause claus = problem.getBR().get(i);
            if (isContain(problem.getBF(), claus.getPropositions()))
                return claus;
        }
        return null;
    }

    public static void main(String args[]) {
        try {
            if(args.length>=1){
                FirstOrderLogicProblem problem = FirstOrderLogicProblemFileReader.read(args[0]);
//                FirstOrderLogicProblem problem = FirstOrderLogicProblemFileReader.read("/home/thuan/sandbox/ia-tp-java/kb/firstorder/3");
    //            System.out.println(problem);
                ForwardChaining fc = new ForwardChaining();
                fc.setProblem(problem);
                fc.chaining();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
