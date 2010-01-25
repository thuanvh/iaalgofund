package ia.reasoning.firstorder;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 23:09:21
 * To change this template use File | Settings | File Templates.
 */
public class FirstOrderLogicProblem {
    private Vector<Variable> variables;
    private Vector<Constant> constants;
    private Vector<Predicate> predicates;
    private Vector<Term> BF;
    private Vector<Clause> BR;
    private Vector<Term> F;
    private Vector<Term> baseFactInitial; // Base fact initial

    public FirstOrderLogicProblem() {
        BF = new Vector();
        BR = new Vector();
        F = new Vector();
        variables = new Vector();
        constants = new Vector();
        predicates = new Vector();
        baseFactInitial = new Vector();
    }

    public Vector<Term> getBaseFactInitial() {
        return baseFactInitial;
    }

    public void setBaseFactInitial(Vector<Term> baseFactInitial) {
        this.baseFactInitial = baseFactInitial;
    }

    public Vector<Predicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(Vector<Predicate> predicates) {
        this.predicates = predicates;
    }

    public Predicate getPredicate(String name) {
        for (int i = 0; i < this.predicates.size(); i++) {
            if (this.predicates.get(i).name.compareTo(name) == 0)
                return this.predicates.get(i);
        }
        return null;
    }

    public Variable getVariable(String name) {
        for (int i = 0; i < this.variables.size(); i++) {
            if (this.variables.get(i).name.compareTo(name) == 0)
                return this.variables.get(i);
        }
        return null;
    }

    public Constant getConstant(String name) {
        for (int i = 0; i < this.constants.size(); i++) {
            if (this.constants.get(i).name.compareTo(name) == 0)
                return this.constants.get(i);
        }
        return null;
    }

    public Vector<Constant> getConstants() {
        return constants;
    }

    public void setConstants(Vector<Constant> constants) {
        this.constants = constants;
    }

    public Vector<Variable> getVariables() {
        return variables;
    }

    public void setVariables(Vector<Variable> variables) {
        this.variables = variables;
    }

    private void sort(Vector<Term> vec) {
        Term[] bf = new Term[vec.size()];
        bf = (Term[]) vec.toArray(bf);
        Arrays.sort(bf);
        vec.clear();
        for (int i = 0; i < bf.length; i++)
            vec.add(bf[i]);
    }

    public void sort() {
        sort(BF);
        sort(F);
        for (int i = 0; i < BR.size(); i++) {
            sort(BR.get(i).getPropositions());
        }
    }

    public void addFact(Term term) {
        int index = 0;
        int max = BF.size();
        int min = 0;

        while (max > min) {
            int mid = (max + min) / 2;

            if (BF.get(mid).compareTo(term) > 0) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        if (min >= BF.size()) {
            BF.add(term);
        } else {
            if (BF.get(min).compareTo(term) < 0) {
                if (min + 1 >= BF.size())
                    BF.add(term);
                else
                    BF.add(min + 1, term);
            } else {
                if (min >= BF.size())
                    BF.add(term);
                else
                    BF.add(min, term);
            }
        }
    }

    public Vector<Term> getBF() {
        return BF;
    }

    public void setBF(Vector<Term> BF) {
        this.BF = BF;
    }

    public Vector<Clause> getBR() {
        return BR;
    }

    public void setBR(Vector<Clause> BR) {
        this.BR = BR;
    }

    public Vector<Term> getF() {
        return F;
    }

    public void setF(Vector<Term> f) {
        F = f;
    }
}
