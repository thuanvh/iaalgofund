package ia.search;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 9 janv. 2010
 * Time: 10:01:29
 * To change this template use File | Settings | File Templates.
 */
public class IDAStarAlgo extends HeuristicSearchAlgo {

    private int fLimit = Integer.MAX_VALUE;
    private int maxOfDepth = 100000;
    private int count = 0;

    @Override
    protected Puzzle doSearch() {
        //To change body of implemented methods use File | Settings | File Templates.
        fLimit = fCost(this.stateCurrent);
        NodeAndCost nac = new NodeAndCost(this.stateCurrent, fLimit);
        while (true) {
//            System.out.println("Limit=====\n" + fLimit);
            NodeAndCost propose = dfsContour(this.stateCurrent, fLimit);
            /*if(fLimit >= propose.newLimit)
                fLimit = fLimit + 1;
            else*/
            fLimit = propose.newLimit;
            if (propose.node != null) {
                System.out.println("Success");
                System.out.println(count);
                return propose.node;
            }
            if (propose.newLimit == maxOfDepth) {
                //false
                return null;
            }
        }

    }

    @Override
    protected void initSearch() {
        //To change body of implemented methods use File | Settings | File Templates.

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

    private int fCost(Puzzle p) {

        return costFromStartState(p) + heuristicFunction.heuristic(p, this.stateFinish);

    }

//    int fLimit=0;

    private NodeAndCost dfsContour(Puzzle p, int fLimit) {
        int nextf = Integer.MAX_VALUE;
        int a = 0;
        a = fCost(p);
        if (a > fLimit) {
            return new NodeAndCost(null, a);
        }
        count++;
//        if (p != null) {
//        System.out.println(count++);
        if (p.compareTo(this.stateFinish) == 0) {
            // find out the state end
            System.out.println(p.print());
            System.out.println("depth:"+p.cost);
            return new NodeAndCost(p, fLimit);
        }
        Puzzle[] children = p.getPuzzleChildren(false);
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null && children[i].compareTo(this.stateCurrent) != 0) {
                NodeAndCost newNac = dfsContour(children[i], fLimit);
                if (newNac.node != null) {
                    System.out.println(children[i].print());
//                    return new NodeAndCost(newNac.node, nac.fLimit);
                    return new NodeAndCost(newNac.node, fLimit);
                }
                nextf = (nextf < newNac.newLimit) ? nextf : newNac.newLimit;
            }
        }
//        }

        return new NodeAndCost(null, nextf);

    }

    public static void main(String args[]) {
        int[][] a = new int[][]{{8, 3, 7}, {5, 0, 2}, {1, 4, 6}};
        int[][] b = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        SearchAlgo search = new IDAStarAlgo();
        HeuristicFunction f = new ManhattanDistance();
        ((HeuristicSearchAlgo) search).setHeuristic(f);

        Puzzle pa = new Puzzle(a);
        Puzzle pb = new Puzzle(b);
        search.setStateCurrent(pa);
        search.setStateFinish(pb);
        search.Search();

        System.out.println(search.cost);
    }

    class NodeAndCost {
        private Puzzle node;
        private int newLimit;

        public NodeAndCost(Puzzle s, int l) {
            node = s;
            newLimit = l;
        }

        public int getfLimit() {
            return newLimit;
        }

        public void setfLimit(int newLimit) {
            this.newLimit = newLimit;
        }

        public Puzzle getNode() {
            return node;
        }

        public void setNode(Puzzle node) {
            this.node = node;
        }
    }
}
