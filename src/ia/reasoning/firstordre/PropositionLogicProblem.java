package ia.reasoning.firstordre;

import java.util.Arrays;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 23:09:21
 * To change this template use File | Settings | File Templates.
 */
public class PropositionLogicProblem {
    private Vector<Term> BF;
    private Vector<Clause> BR;
    private Vector<Term> F;

    public PropositionLogicProblem() {
        BF = new Vector();
        BR = new Vector();
        F = new Vector();
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
