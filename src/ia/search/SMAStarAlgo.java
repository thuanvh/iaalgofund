package ia.search;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 7 janv. 2010
 * Time: 11:27:55
 * To change this template use File | Settings | File Templates.
 */
public class SMAStarAlgo extends HeuristicSearchAlgo implements Comparator {
    // List of node ordered by fcost
    ArrayList<LabeledPuzzle> list = new ArrayList<LabeledPuzzle>();
    private int maxMemory = 10;
    private int maxDepth = 10;

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
    }

    @Override
    public void initSearch() {
        list.clear();
        int est = estimate(stateCurrent);
        list.add(new LabeledPuzzle(stateCurrent, est, est));
    }

    private boolean isFulled() {
        return list.size() >= maxMemory;
    }

    private boolean isInList(LabeledPuzzle puzzle) {
        /*int index = 0;
        int max = list.size();
        int min = 0;
        int mid = 0;
        while (max > min) {
            mid = (max + min) / 2;
            if (list.get(mid).getCost() > puzzle.getCost()) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return list.get(mid).getPuzzle().compareTo(puzzle) == 0;*/
        /*for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPuzzle().compareTo(puzzle.getPuzzle()) == 0)
                return true;

        }
        return false;*/
        return list.contains(puzzle);
    }

    private boolean isAllInList(Vector<LabeledPuzzle> vector) {
        for (int i = 0; i < vector.size(); i++) {
            if (!isInList(vector.get(i)))
                return false;
        }
        return true;
    }

    private boolean isAllInList(LabeledPuzzle[] puzzleList) {
        for (int i = 0; i < puzzleList.length; i++) {
            if (!isInList(puzzleList[i]))
                return false;
        }
        return true;
    }


    private void addPuzzle(LabeledPuzzle puzzle) {
        int index = 0;
        int max = list.size();
        int min = 0;

        while (max > min) {
            int mid = (max + min) / 2;

            if (list.get(mid).getCost() > puzzle.getCost()) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        if (min >= list.size()) {
            list.add(puzzle);
        } else {
            if (list.get(min).getCost() < puzzle.getCost()) {
                if (min + 1 >= list.size())
                    list.add(puzzle);
                else
                    list.add(min + 1, puzzle);
            } else {
                if (min >= list.size())
                    list.add(puzzle);
                else
                    list.add(min, puzzle);
            }
        }
    }

    private boolean isAtMaximumDepth(int depth) {
        /*for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPuzzle().cost >= depth)
                return false;
        }
        return true;*/
        return depth == maxDepth;
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

    private LabeledPuzzle getDeepestLeastCost() {
        LabeledPuzzle lp = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (lp.getLabel() > list.get(i).getLabel()) {
                lp = list.get(i);
            } else if (lp.getLabel() == list.get(i).getLabel()) {
                if (lp.getPuzzle().cost < list.get(i).getPuzzle().cost) {
                    lp = list.get(i);
                }
            }
        }
        return lp;
        /* if (list.size() > 0)
       return list.get(0);
   else
       return null;*/
    }

    private LabeledPuzzle getShallowestHighestCost() {
        LabeledPuzzle lp = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (lp.getLabel() < list.get(i).getLabel()) {
                lp = list.get(i);
            } else if (lp.getLabel() == list.get(i).getLabel()) {
                if (lp.getPuzzle().cost > list.get(i).getPuzzle().cost) {
                    lp = list.get(i);
                }
            }
        }
        return lp;
        /* if (list.size() > 0)
       return list.get(0);
   else
       return null;*/
    }

    private void removeLabeledPuzzle(LabeledPuzzle puzzle) {
        // remove from memory
        list.remove(puzzle);
    }

    private void removeShallowestHighestCost() {

        // remove from its parent's successors list
        LabeledPuzzle puzzle = getShallowestHighestCost();//list.get(list.size() - 1);
        // remove from memory
        list.remove(puzzle);
        if (puzzle.parent != null) {
            puzzle.parent.removeSuccessor(puzzle);
            if (!isInList(puzzle.parent)) {
                addPuzzle(puzzle.parent);
            }
        }


    }

    public static void main(String[] args) {
        int[][] a = new int[][]{{1, 2, 3}, {7, 4, 5}, {0, 8, 6}};
//        int[][] a = new int[][]{{1, 5, 2}, {0, 4, 3}, {7, 8, 6}};
//        int[][] a = new int[][]{{2, 7, 3}, {1, 0, 6}, {5, 4, 8}};
        int[][] b = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        SearchAlgo search = new SMAStarAlgo();
        ((SMAStarAlgo)search).setMaxDepth(8);
        ((SMAStarAlgo)search).setMaxMemory(3);
        HeuristicFunction f = new ManhattanDistance();
        ((HeuristicSearchAlgo) search).setHeuristic(f);

        Puzzle pa = new Puzzle(a);
        Puzzle pb = new Puzzle(b);
        search.setStateCurrent(pa);
        search.setStateFinish(pb);
        search.Search();

        System.out.println(search.cost);
    }

    private void printList() {
        for (int j = 0; j < list.size(); j++) {
            System.out.print("(" + list.get(j).label + "," + list.get(j).cost + "),");
        }
        System.out.print("\n");
    }

    private void printGoal(LabeledPuzzle p) {
        while (p != null) {
            System.out.println(p.puzzle.print());
            p = p.parent;
        }
    }

    @Override
    public Puzzle doSearch() {

//        int maxDepth = 22;
        while (!list.isEmpty()) {
            LabeledPuzzle best = getDeepestLeastCost();
//            printList();
            if (best == null)
                break;

            if (best.getPuzzle().isMatch(this.stateFinish)) {
                printGoal(best);
                return best.getPuzzle();
            } else {
                cost++;

                LabeledPuzzle child = best.getNextSuccessor();
                if (child != null) {
                    if (child.puzzle.compareTo(this.stateFinish) != 0 && isAtMaximumDepth(child.puzzle.cost)) {
                        child.label = Integer.MAX_VALUE;
                    } else {
                        child.label = (best.label > child.label) ? best.label : child.label;
                    }
                }

                if (best.isAllSuccessorGenerated()) {
                    // If all successors are generate
                    //best.setLabel(best.getMinLabelSuccessor());
                    best.setMinLabelSuccessor();
                    LabeledPuzzle current = best;
                    while (current.parent != null) {
                        if (current.parent.isAllSuccessorGenerated())
                            current.parent.setMinLabelSuccessor();
                        /*if (current.label < current.parent.label) {
                            current.parent.label = current.label;
                        }*/

                        current = current.parent;
                    }
                    if (isAllInList(best.successors)) {
                        // if all successors in list
                        removeLabeledPuzzle(best);
//                        printList();
                    }
                }

                if (isFulled()) {
                    // if memory is full
                    removeShallowestHighestCost();
//                    printList();
                }
                if (child != null) {
//                    if (!isInList(child)) {        //???
                    addPuzzle(child);
//                    printList();
//                    }

                }
            }
        }


        return null;
    }

    private class LabeledPuzzle {
        public LabeledPuzzle() {
            successors = new Vector();
        }

        @Override
        public String toString() {
            return puzzle.print();
        }

        public LabeledPuzzle(Puzzle puzzle, int label, int cost) {
            successors = new Vector();
            this.puzzle = puzzle;
            this.label = label;
            this.cost = cost;
        }

        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;

        private Puzzle puzzle;
        private int label;
        private int cost;
        private LabeledPuzzle parent;
        private Vector<LabeledPuzzle> successors;
        private int typeOfSuccessor;
        private boolean isAllSuccessorGenerated;
        private int recentGeneratedChildIndex = -1;

        public Vector<LabeledPuzzle> getSuccessors() {
            return successors;
        }

        public void setSuccessors(Vector<LabeledPuzzle> successors) {
            this.successors = successors;
        }

        /*public void addSuccessor(LabeledPuzzle p) {
            this.successors.add(p);
        }*/

        public LabeledPuzzle getNextSuccessor() {
            // create new successor
            boolean[] inList = new boolean[]{false, false, false, false};
            for (int i = 0; i < this.successors.size(); i++) {
                switch (this.successors.get(i).typeOfSuccessor) {
                    case LEFT:
                        inList[LEFT] = true;
                        break;
                    case RIGHT:
                        inList[RIGHT] = true;
                        break;
                    case UP:
                        inList[UP] = true;
                        break;
                    case DOWN:
                        inList[DOWN] = true;
                        break;
                }
            }
            LabeledPuzzle nextSuccessor = null;
            recentGeneratedChildIndex++;
            for (int i = 0; i < 4; i++) {
                boolean found = false;
                int newIndex = (recentGeneratedChildIndex + i) % 4;
                if (inList[newIndex] == false) {
                    nextSuccessor = new LabeledPuzzle(new Puzzle(this.puzzle), 0, 0);
                    switch (newIndex) {
                        case LEFT:
                            if (nextSuccessor.puzzle.moveBlankLeft())
                                found = true;
                            break;
                        case RIGHT:
                            if (nextSuccessor.puzzle.moveBlankRight())
                                found = true;
                            break;
                        case UP:
                            if (nextSuccessor.puzzle.moveBlankUp())
                                found = true;
                            break;
                        case DOWN:
                            isAllSuccessorGenerated=true;
                            if (nextSuccessor.puzzle.moveBlankDown())
                            {
                                found = true;
                            }
                            break;
                    }
                    if (found) {
                        nextSuccessor.parent = this;
                        nextSuccessor.puzzle.cost = this.puzzle.cost + 1;
                        nextSuccessor.cost = nextSuccessor.label = estimate(nextSuccessor.puzzle);
                        nextSuccessor.typeOfSuccessor = newIndex;
                        recentGeneratedChildIndex = newIndex;
                        successors.add(nextSuccessor);
                        break;
                    } else {
                        nextSuccessor = null;
                    }
                }
            }
            return nextSuccessor;
        }

        public void setMinLabelSuccessor() {
            int minChildrenLabel = Integer.MAX_VALUE;
            for (int i = 0; i < successors.size(); i++) {
                minChildrenLabel = minChildrenLabel < successors.get(i).label ? minChildrenLabel : successors.get(i).label;
            }
            this.label = minChildrenLabel;
        }

        public boolean isAllSuccessorGenerated() {
            /*if (!isAllSuccessorGenerated) {
                //recentGeneratedChildIndex==3;
                Puzzle[] p = this.puzzle.getPuzzleChildren(false);
                int count = 0;
                for (int i = 0; i < p.length; i++)
                    if (p[i] != null)
                        count++;
                isAllSuccessorGenerated = (this.successors.size() == count);
            }*/
            return isAllSuccessorGenerated;
        }

        public void removeSuccessor(LabeledPuzzle p) {
            this.successors.remove(p);
            /*for (int i = 0; i < this.successors.size(); i++) {
                if (p.getPuzzle().compareTo(p.getPuzzle()) == 0) {
                    this.successors.remove(i);
                    return;
                }
            }*/
        }

        public void dispose() {
            puzzle = null;
            parent = null;
        }

        public LabeledPuzzle getParent() {
            return parent;
        }

        public void setParent(LabeledPuzzle parent) {
            this.parent = parent;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public int getLabel() {
            return label;
        }

        public void setLabel(int label) {
            this.label = label;
        }

        public Puzzle getPuzzle() {
            return puzzle;
        }

        public void setPuzzle(Puzzle puzzle) {
            this.puzzle = puzzle;
        }
    }
}