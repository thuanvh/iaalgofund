package ia.search;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 7 janv. 2010
 * Time: 11:07:18
 * To change this template use File | Settings | File Templates.
 */
public interface HeuristicFunction {
    public abstract int heuristic(Puzzle state1, Puzzle state2);
}
