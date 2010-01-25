package ia.reasoning.firstorder;

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
public class FirstOrderLogicProblemFileReader {
    public static final String BF = "#BF";
    public static final String BR = "#BR";
    public static final String F = "#F";
    public static final String VARIABLE = "#Variable";
    public static final String PREDICATE = "#Predicate";
    public static final String CONSTANT = "#Constant";

    private static final int BFIndex = 0;
    private static final int BRIndex = 1;
    private static final int FIndex = 2;
    private static final int VARIABLEIndex=3;
    private static final int PREDICATEIndex=4;
    private static final int CONSTANTIndex=5;

    public static FirstOrderLogicProblem read(String file) throws IOException {

        FirstOrderLogicProblem problem = null;
        BufferedReader in = new BufferedReader(new FileReader(file));
        problem = new FirstOrderLogicProblem();
        int phaseIndex = -1;

        while (in.ready()) {
            String line = in.readLine();
            if(line.trim().isEmpty())
                continue;
            
            if (line.compareToIgnoreCase(BF) == 0) {
                phaseIndex = BFIndex;
                continue;
            } else if (line.compareToIgnoreCase(BR) == 0) {
                phaseIndex = BRIndex;
                continue;
            } else if (line.compareToIgnoreCase(F) == 0) {
                phaseIndex = FIndex;
                continue;
            } else if (line.compareToIgnoreCase(PREDICATE) == 0) {
                phaseIndex = PREDICATEIndex;
                continue;
            }else if (line.compareToIgnoreCase(VARIABLE) == 0) {
                phaseIndex = VARIABLEIndex;
                continue;
            }else if (line.compareToIgnoreCase(CONSTANT) == 0) {
                phaseIndex = CONSTANTIndex;
                continue;
            }
            String[] termstr=null;
            switch (phaseIndex) {
                case VARIABLEIndex:
                    termstr = line.split("[,\\s]");
                    for(int i=0; i<termstr.length; i++){
                        if(!termstr[i].trim().isEmpty()){
                            Variable var=new Variable();
                            var.setName(termstr[i].trim());
                            problem.getVariables().add(var);
                        }
                    }
                    break;
                case CONSTANTIndex:
                    termstr = line.split("[,\\s]");
                    for(int i=0; i<termstr.length; i++){
                        if(!termstr[i].trim().isEmpty()){
                            Constant con=new Constant();
                            con.setName(termstr[i].trim());
                            problem.getConstants().add(con);
                        }
                    }
                    break;
                case PREDICATEIndex:
                    termstr = line.split("[,\\s]");
                    for(int i=0; i<termstr.length; i++){
                        if(!termstr[i].trim().isEmpty()){
                            Predicate pre=new Predicate();
                            pre.setName(termstr[i].trim());
                            problem.getPredicates().add(pre);
                        }
                    }
                    break;
                case BFIndex:
                    termstr = line.split("[;]");
                    for (int i = 0; i < termstr.length; i++) {
                        if (!termstr[i].trim().isEmpty()) {
                            Term term = parseTerm(termstr[i].trim(),problem);
                            if(term!=null){
                                problem.getBaseFactInitial().add(term);
                            }                            
                        }
                    }
                    break;
                case BRIndex:
                    termstr = line.split("[=;]");
                    Clause clause = new Clause();
                    if (termstr.length > 0) {
                        Term term = parseTerm(termstr[0].trim(),problem);
                        if(term!=null)
                            clause.setConclusion(term);
                    }
                    for (int i = 1; i < termstr.length; i++) {
                        if (termstr[i].trim().compareTo("") != 0) {
                            Term term = parseTerm(termstr[i].trim(),problem);
                            if(term!=null)
                                clause.getPropositions().add(term);
                        }
                    }
                    problem.getBR().add(clause);
                    break;
                case FIndex:
                    termstr = line.split("[;]");
                    for (int i = 0; i < termstr.length; i++) {
                        if (termstr[i].trim().compareTo("") != 0) {
                            Term term = parseTerm(termstr[i].trim(),problem);
                            if(term!=null)
                                problem.getF().add(term);
                        }
                    }
                    break;
            }
            //problem.sort();
        }


        return problem;
    }
    public static Term parseTerm(String termString,FirstOrderLogicProblem problem){
        String[] items=termString.split("[(),\\s]");
        if(items.length>0){
            Term term=new Term();
            Predicate pre=problem.getPredicate(items[0].trim());
            if(pre!=null)
                term.setPredicate(pre);
            else
                return null;
            for(int i=1; i<items.length; i++){
                if(!items[i].isEmpty()){
                    Variable var=problem.getVariable(items[i].trim());
                    if(var!=null)
                        term.getParameters().add(var);
                    else {
                        Constant cons=problem.getConstant(items[i].trim());
                        term.getParameters().add(cons);
                    }

                }
            }
            return term;
        }
        return null;
    }
    public static void main(String args[]) {
        try {
            FirstOrderLogicProblem problem = FirstOrderLogicProblemFileReader.read("/home/thuan/sandbox/ia-tp-java/kb/firstorder/1");
            System.out.println(problem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
