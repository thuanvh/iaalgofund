package ia.search;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 7 janv. 2010
 * Time: 11:10:02
 * To change this template use File | Settings | File Templates.
 */
public class ManhattanDistance implements HeuristicFunction {
    public int heuristic(Puzzle state1, Puzzle state2) {
        int manhattan = 0;
        for (int i = 0; i < state2.size(); i++) {
            for (int j = 0; j < state2.size(); j++) {
                int a = state2.getValue(i, j);
                for (int i2 = 0; i2 < state1.size(); i2++) {
                    for (int j2 = 0; j2 < state1.size(); j2++) {
                        if (state1.getValue(i2, j2) == a) {
                            int b = Math.abs(i2 - i) + Math.abs(j2 - j);
                            manhattan += b;
//                            System.out.println(a+":"+b+"\n");
                            i2 = state1.size();
                            break;
                        }
                    }
                }
            }
        }
        return manhattan;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
