package ia.reasoning.firstorder;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 19 janv. 2010
 * Time: 08:30:42
 * To change this template use File | Settings | File Templates.
 */
public class BackwardChaining extends ChainingAlgo {

    private Vector answers;

    public BackwardChaining() {
        answers = new Vector();
    }

    private Vector<Substitution> backChain(Term conclusion) {
        Vector<Term> newVector = new Vector();
        newVector.add(conclusion);
        Vector<Substitution> subs = backChainList(newVector, null);
        return subs;

    }

    private Vector<Substitution> backChainList(Vector<Term> qlist, Substitution sub) {

        Vector<Substitution> vector = new Vector();
        if (qlist.size() == 0) {
            vector.add(sub);
            return vector;
        }
        Term q = qlist.get(0);
        System.out.println();
        System.out.println("Chaining:"+q);
        for (int i = 0; i < problem.getBF().size(); i++) {
            Term term = problem.getBF().get(i);
            Substitution uni = unify(q, term);
            if (uni != null) {
                System.out.println("Unify:" + q + "," + term + "=" + uni);
                Substitution unisub = uni.compose(sub);
                System.out.println(unisub);
                answers.add(unisub);
            }

        }
        for (int i = 0; i < problem.getBR().size(); i++) {
            Clause clause = problem.getBR().get(i);
            System.out.println("Clause:" + clause);
            //Term conclusion=clause.getConclusion();
            Substitution uni = unify(q, clause.getConclusion());
            if (uni != null) {
                System.out.println("Unify:" + q + "," + clause.getConclusion() + "=" + uni);
                Vector<Term> newProsposition = substitute(clause.getPropositions(), uni);
                System.out.println("Proposition:"+printVector(newProsposition));                
                Vector<Substitution> subs = backChainList(newProsposition, uni.compose(sub));
                answers.addAll(subs);
                //return answers;
            }
        }
        qlist.remove(0);
        Vector<Substitution> answersRest = backChainList(qlist,sub);
        //sous set of answer
        answers.addAll(answersRest);
        return answers;

    }

    @Override
    public void chaining() {

        //Node init=new Node();
        for (int i = 0; i < problem.getF().size(); i++) {
            this.answers.clear();
            Term theory = problem.getF().get(i);
            System.out.println("Prove:"+theory);
            problem.setBF((Vector<Term>)problem.getBaseFactInitial().clone());
            Vector<Substitution> subs = backChain(theory);
            System.out.println();
            System.out.println(printVector(subs));
            System.out.println("====");
        }
        //init.setTerm(theory);
        //reasoningANode(init);

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
    /*private boolean reasoningANode(Node node){
        if(isContain(problem.getBF(),node.term))
            return true; // exist in BF
        Vector<Clause> relativeClauses=getRelativeClauses(node.term);
        if(relativeClauses.size()==0){
            return false;
        }
        for(int i=0; i<relativeClauses.size(); i++){
            Clause clause=relativeClauses.get(i);
            Vector<Term> term=clause.getPropositions();
            int j=0;
            for(j=0; j<term.size(); j++){
                Node child=new Node();
                child.term=term.get(j);
                child.parent=node;
                child.clause=clause;
                boolean a = reasoningANode(child);
                if(!a)
                    break;
            }
            if(j==term.size()){
                // Success, add to BF 
                problem.addFact(node.term);
                System.out.println("Rule:"+clause);
                System.out.println("Add to BF: "+node.term);
                System.out.println("BF:" + printVector(problem.getBF()));
                System.out.println();
                
                return true;
            }
        }
        return true;
    }*/

    /**
     * get clauses have relation with term as conclusion
     *
     * @param term
     * @return
     */
    public Vector<Clause> getRelativeClauses(Term term) {
        //Term term=new Term();
        Vector<Clause> relativeClauses = new Vector();
        for (int i = 0; i < problem.getBR().size(); i++) {
            Clause clause = problem.getBR().get(i);
            if (clause.getConclusion().compareTo(term) == 0) {
                relativeClauses.add(clause);
            }
        }
        return relativeClauses;
    }

    public static void main(String args[]) {
        try {
            FirstOrderLogicProblem problem = FirstOrderLogicProblemFileReader.read("/home/thuan/sandbox/ia-tp-java/kb/firstorder/1");
//            System.out.println(problem);
            BackwardChaining bc = new BackwardChaining();
            bc.setProblem(problem);
            bc.chaining();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /*private class Node{
        private Clause clause;
        private Term term;
        private Node parent;
        public Clause getClause() {
            return clause;
        }

        public void setClause(Clause clause) {
            this.clause = clause;
        }

        public Term getTerm() {
            return term;
        }

        public void setTerm(Term term) {
            this.term = term;
        }
    }*/
}
