package ia.search;


/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 6 janv. 2010
 * Time: 20:33:11
 * To change this template use File | Settings | File Templates.
 */
public class SearchEngine {
    private static int[][] a = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
    private static int[][] b = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private static Puzzle pa;
    private static Puzzle pb;

    public static void testWidthSearch() {
//        int[][] a = new int[][]{{7, 1, 3}, {8, 0, 4}, {6, 2, 5}};

        SearchAlgo search = new WidthSearchAlgo();
//        Puzzle pa = new Puzzle(a);
//        Puzzle pb = new Puzzle(b);
        search.setStateCurrent(pa);
        search.setStateFinish(pb);
        search.Search();

        System.out.println(search.cost);
    }

    public static void testDeptSearch() {
//        int[][] a = new int[][]{{7, 1, 3}, {8, 0, 4}, {6, 2, 5}};
//        int[][] b = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        SearchAlgo search = new DepthSearchAlgoWithNotParentRepeat();
//        Puzzle pa = new Puzzle(a);
//        Puzzle pb = new Puzzle(b);
        search.setStateCurrent(pa);
        search.setStateFinish(pb);
        search.Search();

        System.out.println(search.cost);
    }

    public static void testAStarSearch() {
        /*int[][] a = new int[][]{{7, 1, 3}, {8, 0, 4}, {6, 2, 5}};
        int[][] b = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};*/
        SearchAlgo search = new AStarAlgo();
        HeuristicFunction f = new ManhattanDistance();
        ((HeuristicSearchAlgo) search).setHeuristic(f);

//        Puzzle pa = new Puzzle(a);
//        Puzzle pb = new Puzzle(b);
        search.setStateCurrent(pa);
        search.setStateFinish(pb);
        search.Search();

        System.out.println(search.cost);
    }

    public static void testIDAStarSearch() {
        /* int[][] a = new int[][]{{7, 1, 3}, {8, 0, 4}, {6, 2, 5}};
int[][] b = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};*/
        SearchAlgo search = new IDAStarAlgo();
        HeuristicFunction f = new ManhattanDistance();
        ((HeuristicSearchAlgo) search).setHeuristic(f);

//        Puzzle pa = new Puzzle(a);
//        Puzzle pb = new Puzzle(b);
        search.setStateCurrent(pa);
        search.setStateFinish(pb);
        search.Search();

        System.out.println(search.cost);
    }

    public static void testSMAStarSearch(int depth,int mem) {
        /*int[][] a = new int[][]{{7, 1, 3}, {8, 0, 4}, {6, 2, 5}};
        int[][] b = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};*/
        SearchAlgo search = new SMAStarAlgo();
        HeuristicFunction f = new ManhattanDistance();
        ((HeuristicSearchAlgo) search).setHeuristic(f);
        ((SMAStarAlgo)search).setMaxDepth(depth);
        ((SMAStarAlgo)search).setMaxMemory(mem);

//        Puzzle pa = new Puzzle(a);
//        Puzzle pb = new Puzzle(b);
        search.setStateCurrent(pa);
        search.setStateFinish(pb);
        search.Search();

        System.out.println(search.cost);
    }

    public static void main(String args[]) {
        try {

//        testWidthSearch();
//        System.out.println(args.length);
            if (args.length > 1) {
                String file = args[1];
                Puzzle[] pbl = PuzzleFileReader.read(file);
                pa = pbl[0];
                pb = pbl[1];
//            System.out.println(args[0]);
                String cmd = args[0];
                if (cmd.compareTo("w") == 0) {
                    testWidthSearch();
                } else if (cmd.compareTo("d") == 0) {
                    testDeptSearch();
                } else if (cmd.compareTo("a") == 0) {
                    testAStarSearch();
                } else if (cmd.compareTo("ida") == 0) {
                    testIDAStarSearch();
                } else if (cmd.compareTo("sma") == 0) {
                    int depth=10;
                    int mem=100;
                    if(args.length>2){
                        for(int i=2; i<args.length; i++){
                            if(args[i].startsWith("-depth=")){
                                depth=Integer.parseInt(args[i].substring(7));
                                System.out.println("depth"+depth);
                            }else
                                if(args[i].startsWith("-mem=")){
                                    mem=Integer.parseInt(args[i].substring(5));
                                    System.out.println("mem:"+mem);
                                }
                        }
                    }
                    testSMAStarSearch(depth,mem);
                }

            }

        } catch (Exception exc) {
            System.out.println(exc);

        }
    }
}
