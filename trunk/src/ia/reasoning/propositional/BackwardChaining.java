package ia.reasoning.propositional;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 19 janv. 2010
 * Time: 08:30:42
 * To change this template use File | Settings | File Templates.
 */
public class BackwardChaining extends ChainingAlgo {
    PropositionLogicProblem problem;
    
    @Override
    public void chaining(PropositionLogicProblem problem) {
        this.problem=problem;

        Node init=new Node();
        Term theory=problem.getF().get(0);
        init.setTerm(theory);
        reasoningANode(init);
        
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
    private boolean reasoningANode(Node node){
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
    }

    /**
     * get clauses have relation with term as conclusion
     * @param term
     * @return
     */
    public Vector<Clause> getRelativeClauses(Term term){
        //Term term=new Term();
        Vector<Clause> relativeClauses=new Vector();
        for(int i=0; i<problem.getBR().size(); i++){
            Clause clause=problem.getBR().get(i);
            if(clause.getConclusion().compareTo(term)==0){
                relativeClauses.add(clause);
            }
        }
        return relativeClauses;
    }
    public static void main(String args[]) {
        try {
            PropositionLogicProblem problem = PropositionLogicProblemFileReader.read("/home/thuan/sandbox/ia-tp-java/kb/proposition/2");
            System.out.println(problem);
            BackwardChaining bc = new BackwardChaining();
            bc.chaining(problem);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private class Node{
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
    }
}
