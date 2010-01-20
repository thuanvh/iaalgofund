package ia.reasoning.firstordre;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 23:56:54
 * To change this template use File | Settings | File Templates.
 */
public abstract class ChainingAlgo {
    public abstract void chaining(PropositionLogicProblem problem);
    public String printVector(Vector<Term> term) {
        String a = "";
        for (int i = 0; i < term.size(); i++) {
            a += term.get(i) + ",";
        }
        return a;
    }
}
