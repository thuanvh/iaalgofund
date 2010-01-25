package ia.reasoning.firstorder;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 23:56:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class ChainingAlgo {
    protected FirstOrderLogicProblem problem;

    public FirstOrderLogicProblem getProblem() {
        return problem;
    }

    public void setProblem(FirstOrderLogicProblem problem) {
        this.problem = problem;
    }

    public abstract void chaining();

    public String printVector(Vector term) {
        String a = "";
        for (int i = 0; i < term.size(); i++) {
            a += term.get(i) + ",";
        }
        return a;
    }

    public Substitution unify(Term t1, Term t2) {
        if (t1.getPredicate() != t2.getPredicate())
            return null;
        Substitution sub = new Substitution();
        for (int i = 0; i < t1.getParameters().size(); i++) {
            Literal l1 = t1.getParameters().get(i);
            Literal l2 = t2.getParameters().get(i);
            Variable var = null;
            Literal varReplacement = null;
            if (l1 instanceof Variable && l2 instanceof Constant) {
                var = (Variable) l1;
                varReplacement =  l2;

            } else if (l1 instanceof Constant && l2 instanceof Variable) {
                var = (Variable) l2;
                varReplacement = l1;
            } else if (l1 instanceof Variable && l2 instanceof Variable){
                var = (Variable) l1;
                varReplacement = l2;
            } else if (l1 instanceof Constant && l2 instanceof Constant){
                if(l1!=l2)
                    return null;
            }
            if (var != null && varReplacement != null) {
                if (!sub.getReplacement().containsKey(var))
                    sub.getReplacement().put(var, varReplacement);
                else {
                    var=generateNewVariable(var);
                    sub.getReplacement().put(var, varReplacement);
                }
            }
        }
        return sub;
    }

    private Variable generateNewVariable(Variable varBase) {
        int i=0;
        String newName = varBase.name + i;
        boolean isExist=false;
        while (true) {
            for (int j = 0; j < problem.getVariables().size(); j++) {
                if (problem.getVariables().get(j).getName().compareTo(newName) == 0) {
                    isExist=true;
                    break;
                }
            }
            if(isExist){
                newName=varBase.name+(++i);
                isExist=false;
            }else{
                break;
            }
        }
        Variable var=new Variable();
        var.setName(newName);
        return var;
    }

    public Term substitute(Term term, Substitution sub) {
        Term newterm=new Term(term);
        for(int i=0; i<newterm.getParameters().size(); i++){
            Literal lit=newterm.getParameters().get(i);
            if(lit instanceof Variable){
                Variable var=(Variable)lit;
                Literal replacement=sub.getReplacement().get(var);
                if(replacement!=null)
                    newterm.getParameters().set(i,replacement);
            }
        }
        return newterm;
    }

    public Vector<Term> substitute(Vector<Term> term, Substitution s) {
        Vector<Term> newterms=(Vector<Term>)term.clone();
        for(int i=0; i<newterms.size(); i++){
            Term a=substitute(newterms.get(i),s);
            newterms.set(i,a);
        }
        return newterms;
    }
}
