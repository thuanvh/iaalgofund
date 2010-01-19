package ia.search;

import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 6 janv. 2010
 * Time: 20:11:16
 * To change this template use File | Settings | File Templates.
 */
public class DepthSearchAlgoWithNotParentRepeat extends SearchAlgo {
    Stack<Puzzle> stack = new Stack<Puzzle>();
//    Puzzle treatedList=new Hashtable();

    @Override
    public void initSearch() {
        stack.clear();
        stack.push(stateCurrent);
        parentState = stateCurrent;
    }

    Puzzle parentState = null;

    @Override
    public Puzzle doSearch() {
        int j = 0;
        while (!stack.isEmpty()) {
            Puzzle top = stack.pop();

            if (top == null)
                break;
//            System.out.println("-s-\n");
//            System.out.println(top.print());
//            if(!stack.isEmpty())
//            System.out.println(stack.peek().print());
//            System.out.println("-f-\n");
            if (top.isMatch(this.stateFinish)) {
                return top;
            } else {
                cost++;

                Puzzle[] children = top.getPuzzleChildren(true);
                for (int i = 0; i < children.length; i++) {
                    if (children[i] != null && !children[i].isRepeatedAncestor()) {
                        stack.push(children[i]);
//                        System.out.println("add\n"+children[i].print());
                    }
                }
                parentState = top;
            }

        }
        return null;
    }
}