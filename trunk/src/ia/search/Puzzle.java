package ia.search;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 5 janv. 2010
 * Time: 11:33:15
 * To change this template use File | Settings | File Templates.
 */
public class Puzzle implements Comparable {
    int[][] elements;
    int blankCellRow;
    int blankCellCol;
    Puzzle parentPuzzle;
    int cost = 0;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Puzzle getParentPuzzle() {
        return parentPuzzle;
    }

    public void setParentPuzzle(Puzzle parentPuzzle, boolean refToParent) {
        if (refToParent)
            this.parentPuzzle = parentPuzzle;
        this.cost = parentPuzzle.cost + 1;
    }

    public Puzzle(int size) {
        elements = new int[size][size];
    }

    public Puzzle() {
    }

    public Puzzle(Puzzle puzzle) {
        this.setElements(puzzle.getElements());
    }

    public int[][] getElements() {
        return elements;
    }

    public void setElements(int[][] elements) {
        this.elements = new int[elements.length][];
        for (int i = 0; i < elements.length; i++) {
            this.elements[i] = new int[elements[i].length];
            for (int j = 0; j < elements[i].length; j++) {
                this.elements[i][j] = elements[i][j];
                if (elements[i][j] == 0) {
                    blankCellRow = i;
                    blankCellCol = j;

                }
            }
        }
    }

    public Puzzle(int[][] values) {
        setElements(values);

    }

    public void dispose() {
        this.elements = null;
        this.parentPuzzle = null;
    }

    public int size() {
        if (this.elements == null)
            return 0;
        return this.elements.length;
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int a = elements[x1][y1];
        elements[x1][y1] = elements[x2][y2];
        elements[x2][y2] = a;
    }

    public boolean moveBlankUp() {

        int newBlankCellRow = blankCellRow - 1;
        if (newBlankCellRow >= 0) {
            swap(blankCellRow, blankCellCol, newBlankCellRow, blankCellCol);
            blankCellRow = newBlankCellRow;
            return true;
        }
        return false;
    }

    public boolean moveBlankDown() {
        int newBlankCellRow = blankCellRow + 1;
        if (newBlankCellRow < size()) {
            swap(blankCellRow, blankCellCol, newBlankCellRow, blankCellCol);
            blankCellRow = newBlankCellRow;
            return true;
        }
        return false;
    }

    public boolean moveBlankLeft() {
        int newBlankCellCol = blankCellCol - 1;
        if (newBlankCellCol >= 0) {
            swap(blankCellRow, blankCellCol, blankCellRow, newBlankCellCol);
            blankCellCol = newBlankCellCol;
            return true;
        }
        return false;
    }

    public boolean moveBlankRight() {
        int newBlankCellCol = blankCellCol + 1;
        if (newBlankCellCol < size()) {
            swap(blankCellRow, blankCellCol, blankCellRow, newBlankCellCol);
            blankCellCol = newBlankCellCol;
            return true;
        }
        return false;
    }

    public Puzzle[] getPuzzleChildren(boolean refParent) {
        /*System.out.println(this.print());
        System.out.println("--\n");*/
        Puzzle up = new Puzzle(this);
        if (!up.moveBlankUp())
            up = null;
        else {
            up.setParentPuzzle(this, refParent);

        }

        Puzzle down = new Puzzle(this);
        if (!down.moveBlankDown())
            down = null;
        else {
            down.setParentPuzzle(this, refParent);
        }

        Puzzle left = new Puzzle(this);
        if (!left.moveBlankLeft())
            left = null;
        else {
            left.setParentPuzzle(this, refParent);
        }

        Puzzle right = new Puzzle(this);
        if (!right.moveBlankRight()) right = null;
        else {
            right.setParentPuzzle(this, refParent);
        }

        Puzzle[] a = new Puzzle[]{up, down, left, right};
        /* for(int i=0; i<a.length; i++){
            if(a[i]!=null)
            System.out.println(a[i].print());
        }
        System.out.println("===========\n");*/
        return (a);


    }

    /**
     * @param row
     * @param col
     * @return
     */
    public int getValue(int row, int col) {
        return elements[row][col];
    }

    /**
     * @param puzzle
     * @return
     */
    public boolean isMatch(Puzzle puzzle) {
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j] != puzzle.getValue(i, j))
                    return false;
            }
        }
        return true;
    }

    public boolean isRepeatedAncestor() {
        Puzzle parent = this.parentPuzzle;
        while (parent != null) {
            if (this.isMatch(parent))
                return true;
            else
                parent = parent.parentPuzzle;
        }
        return false;
    }

    public int compareTo(Object o) {
        if (o instanceof Puzzle) {
            Puzzle puzzle = (Puzzle) o;
            for (int i = 0; i < elements.length; i++) {
                for (int j = 0; j < elements[i].length; j++) {
                    if (elements[i][j] != puzzle.getValue(i, j))
                        return 1;
                }
            }
            return 0;
        }
        return 1;
    }

    public String print() {
        String a = "";
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                a += elements[i][j] + ",";
            }
            a += "\n";
        }
        return a;
    }
}
