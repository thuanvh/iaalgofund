package ia.search;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 7 janv. 2010
 * Time: 11:27:55
 * To change this template use File | Settings | File Templates.
 */
public class AStarAlgo extends HeuristicSearchAlgo implements Comparator {
    Queue<Puzzle> queue = new LinkedBlockingQueue<Puzzle>();

    @Override
    public void initSearch() {
        queue.clear();
        queue.add(stateCurrent);
    }

    private int costFromStartState(Puzzle p) {
        /*int cost = 0;
        Puzzle parent = p.parentPuzzle;
        while (parent != null) {
            cost++;
            parent = parent.parentPuzzle;
        }*/
        return p.cost;
    }

    private int estimate(Puzzle p) {
        return costFromStartState(p) + heuristicFunction.heuristic(p, this.stateFinish);
    }

    public int compare(Object o1, Object o2) {
        if (o1 instanceof Puzzle && o2 instanceof Puzzle) {
            int a = estimate((Puzzle) o1);
            int b = estimate((Puzzle) o2);
            if (a > b) return 1;
            else if (a == b) return 0;
            else return -1;
        }
        return 1;
    }

    private void sortByEstimate(Puzzle[] a) {
        Arrays.sort(a, this);

    }

    @Override
    public Puzzle doSearch() {
        int j = 0;

        while (!queue.isEmpty()) {
            Puzzle top = queue.poll();
            if (top == null)
                break;
            /*System.out.println("-s-\n");
            System.out.println(top.print());
            if(!stack.isEmpty())
            System.out.println(stack.peek().print());
            System.out.println("-f-\n");*/
            if (top.isMatch(this.stateFinish)) {
                return top;
            } else {
                cost++;

                Puzzle[] children = top.getPuzzleChildren(false);
                // Sort by heuristic
                sortByEstimate(children);
                for (int i = 0; i < children.length; i++) {
                    if (children[i] != null) {
                        queue.add(children[i]);
//                        System.out.println("add\n"+children[i].print());
                    }
                }
            }

        }
        return null;
    }
}
