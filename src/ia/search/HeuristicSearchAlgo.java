package ia.search;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 7 janv. 2010
 * Time: 11:30:29
 * To change this template use File | Settings | File Templates.
 */
public abstract class HeuristicSearchAlgo extends SearchAlgo {
    HeuristicFunction heuristicFunction;

    public HeuristicFunction getHeuristicFunction() {
        return heuristicFunction;
    }

    public void setHeuristic(HeuristicFunction heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
    }
}
