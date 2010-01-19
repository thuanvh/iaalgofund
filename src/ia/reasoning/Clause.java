package ia.reasoning;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 21:20:16
 * To change this template use File | Settings | File Templates.
 */
public class Clause {
    private Term conclusion;
    private Vector<Term> propositions;

    public Clause() {
        propositions = new Vector();
    }

    @Override
    public String toString() {
        String a = conclusion.toString() + "=";
        for (int i = 0; i < propositions.size(); i++) {
            a += propositions.get(i).toString() + ",";
        }
        return a;
    }

    public Term getConclusion() {
        return conclusion;
    }

    public void setConclusion(Term conclusion) {
        this.conclusion = conclusion;
    }

    public Vector<Term> getPropositions() {
        return propositions;
    }

    public void setPropositions(Vector<Term> propositions) {
        this.propositions = propositions;
    }

    
}
