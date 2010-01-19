package ia.search;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 6 janv. 2010
 * Time: 20:11:16
 * To change this template use File | Settings | File Templates.
 */
public class WidthSearchAlgo extends SearchAlgo {
    Queue<Puzzle> queue = new LinkedBlockingQueue<Puzzle>();

    @Override
    public void initSearch() {
        queue.clear();
        queue.add(stateCurrent);
    }


    @Override
    public Puzzle doSearch() {
        int j = 0;
        //Hashtable table= new Hashtable();
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
                for (int i = 0; i < children.length; i++) {
                    if (children[i] != null) {
                        queue.add(children[i]);
//                        System.out.println("add\n"+children[i].print());
                    }
                }
            }
//            System.gc();

        }
        return null;
    }
}
