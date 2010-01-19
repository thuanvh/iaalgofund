package ia.search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 12 janv. 2010
 * Time: 10:57:03
 * To change this template use File | Settings | File Templates.
 */
public class PuzzleFileReader {
    public static Puzzle[] read(String file) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(file));
        Puzzle[] problem = new Puzzle[2];
        String a = in.readLine();
        int size = Integer.parseInt(a);
        int puzzleIndex = 0;

        while (in.ready()) {
            a = in.readLine();
            //System.out.println(a);
            String[] splits = a.split("[{}]");
            Puzzle puzzle = new Puzzle(size);

            for (int i = 0, rowindex = 0; i < splits.length; i++) {
                if (splits[i].trim().compareTo("") == 0) {
                    continue;
                }
                String[] digits = splits[i].trim().split(",");
                for (int j = 0, colindex = 0; j < digits.length; j++) {
                    if (digits[j].trim().compareTo("") != 0) {
                        int value = Integer.parseInt(digits[j].trim());
                        puzzle.elements[rowindex][colindex] = value;
                        //System.out.println(rowindex+","+colindex+","+value);
                        colindex++;
                    }
                }
                //System.out.println(splits[i]);
                rowindex++;
            }
//            puzzle.print();
            if (puzzleIndex < problem.length) {
                problem[puzzleIndex] = puzzle;
                puzzleIndex++;
            }
        }

        return problem;
    }

    public static void main(String args[]) {
        try {
            Puzzle[] a = PuzzleFileReader.read("/home/thuan/sandbox/ia-tp-java/puzzle/8/3");
            for (int i = 0; i < a.length; i++) {
                System.out.println(a[i].print());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
