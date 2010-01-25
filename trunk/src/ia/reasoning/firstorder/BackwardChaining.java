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

    //private Vector answers;

    public BackwardChaining() {
        //answers = new Vector();
    }

    private Vector<Result> backChain(Term conclusion) {
        Vector<Term> newVector = new Vector();
        newVector.add(conclusion);
        Vector<Result> subs = backChainList(newVector, new Result());
        return subs;

    }

    private Vector<Result> backChainList(Vector<Term> qlist, Result result) {

        Vector<Result> answers = new Vector();
        if (qlist.size() == 0) {
            /*if(sub!=null){
                answers.add(sub);
            }
            return answers;*/
            return null;
        }
        Term q = qlist.get(0);
        System.out.println();
        System.out.println("Chaining:" + q);
        for (int i = 0; i < problem.getBF().size(); i++) {
            Term term = problem.getBF().get(i);
            Substitution uni = unify(q, term);
            if (uni != null) {
                System.out.println("Unify:" + q + "," + term + "=" + uni);
//                if (!uni.getReplacement().isEmpty()) {
                    Substitution unisub = uni.compose(result.substitution);
                    Result ret=new Result();
                    ret.substitution=unisub;
                    ret.term=term;
                    ret.clause=result.clause;
                    System.out.println(unisub);
                    answers.add(ret);
//                }
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
                System.out.println("Proposition:" + printVector(newProsposition));
                Result ret=new Result();
                ret.substitution=uni.compose(result.substitution);
                ret.clause=clause;
                Vector<Result> subs = backChainList(newProsposition, ret);
                if (subs != null)
                    answers.addAll(subs);
                //return answers;
            }
        }
        qlist.remove(0);
        if(qlist.size()>0){
            Vector<Result> answersRest = backChainList(qlist, result);
                //sous set of answer
            if (answersRest != null)
                answers.addAll(answersRest);
            else
                return null;
        }
//        System.out.println(printVector(answers));
        if(answers.size()>0)
            return answers;
        else
            return null;

    }

    @Override
    public void chaining() {

        //Node init=new Node();
        for (int i = 0; i < problem.getF().size(); i++) {
            //this.answers.clear();
            Term theory = problem.getF().get(i);
            System.out.println("Prove:" + theory);
            problem.setBF((Vector<Term>) problem.getBaseFactInitial().clone());
            Vector<Result> subs = backChain(theory);
            System.out.println();
            if (subs != null && subs.size() > 0) {
                System.out.println("Success.");
                System.out.println(printVector(subs));
            } else
                System.out.println("Fail");

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
            if (args.length >= 1) {
                FirstOrderLogicProblem problem = FirstOrderLogicProblemFileReader.read(args[0]);
//            FirstOrderLogicProblem problem = FirstOrderLogicProblemFileReader.read("/home/thuan/sandbox/ia-tp-java/kb/firstorder/1");

//            System.out.println(problem);
                BackwardChaining bc = new BackwardChaining();
                bc.setProblem(problem);
                bc.chaining();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private class Result{
        private Clause clause;
        private Term term;
        private Substitution substitution;

        public Substitution getSubstitution() {
            return substitution;
        }

        public void setSubstitution(Substitution substitution) {
            this.substitution = substitution;
        }

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

        @Override
        public String toString() {
            String a="\nSub:"+substitution;
            if(clause!=null){
                a+=" - Clause:"+clause;
            }
            if(term!=null){
                a+=" - Term:"+term;
            }
//            a+="\n";
            return a;
        }
    }
}
