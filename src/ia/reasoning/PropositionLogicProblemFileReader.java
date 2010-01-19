package ia.reasoning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 23:12:24
 * To change this template use File | Settings | File Templates.
 */
public class PropositionLogicProblemFileReader {
    public static final String BF = "#BF";
    public static final String BR = "#BR";
    public static final String F = "#F";
    private static final int BFIndex = 0;
    private static final int BRIndex = 1;
    private static final int FIndex = 2;

    public static PropositionLogicProblem read(String file) throws IOException {

        PropositionLogicProblem problem = null;
        BufferedReader in = new BufferedReader(new FileReader(file));
        problem = new PropositionLogicProblem();
        int phaseIndex = -1;

        while (in.ready()) {
            String line = in.readLine();
            if (line.compareToIgnoreCase(BF) == 0) {
                phaseIndex = BFIndex;
                continue;
            } else if (line.compareToIgnoreCase(BR) == 0) {
                phaseIndex = BRIndex;
                continue;
            } else if (line.compareToIgnoreCase(F) == 0) {
                phaseIndex = FIndex;
                continue;
            }
            switch (phaseIndex) {
                case BFIndex:
                    String[] termstr = line.split("[,\\s]");
                    for (int i = 0; i < termstr.length; i++) {
                        if (termstr[i].trim().compareTo("") != 0) {
                            Term term = new Term();
                            term.setName(termstr[i].trim());
                            problem.getBF().add(term);
                        }
                    }
                    break;
                case BRIndex:
                    termstr = line.split("[=,\\s]");
                    Clause clause = new Clause();
                    if (termstr.length > 0) {
                        Term term = new Term();
                        term.setName(termstr[0].trim());
                        clause.setConclusion(term);
                    }
                    for (int i = 1; i < termstr.length; i++) {
                        if (termstr[i].trim().compareTo("") != 0) {
                            Term term = new Term();
                            term.setName(termstr[i].trim());
                            clause.getPropositions().add(term);
                        }
                    }
                    problem.getBR().add(clause);
                    break;
                case FIndex:
                    termstr = line.split("[,\\s]");
                    for (int i = 0; i < termstr.length; i++) {
                        if (termstr[i].trim().compareTo("") != 0) {
                            Term term = new Term();
                            term.setName(termstr[i].trim());
                            problem.getF().add(term);
                        }
                    }
                    break;
            }
            problem.sort();
        }


        return problem;
    }

    public static void main(String args[]) {
        try {
            PropositionLogicProblem problem = PropositionLogicProblemFileReader.read("/home/thuan/sandbox/ia-tp-java/kb/proposition/1");
            System.out.println(problem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
