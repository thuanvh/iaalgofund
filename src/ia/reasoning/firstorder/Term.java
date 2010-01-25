package ia.reasoning.firstorder;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 21:25:17
 * To change this template use File | Settings | File Templates.
 */
public class Term implements Comparable{
    
    private Predicate predicate;
    private Vector<Literal> parameters;
    private String name;

    public Vector<Literal> getParameters() {
        return parameters;
    }

    public void setParameters(Vector<Literal> parameters) {
        this.parameters = parameters;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }
    public Term(Term term){
        this.predicate=term.predicate;
        this.parameters = (Vector<Literal>) term.getParameters().clone();
        this.name=term.name;
    }
    public Term(){
        parameters =new Vector();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String a=predicate.getName()+"(";
        for(int i=0; i<parameters.size(); i++){
            a+=parameters.get(i).getName();
            if(i<parameters.size()-1)
                a+=",";
        }
        a+=")";
        return a;
    }

    public int compareTo(Object o) {
        if(o instanceof Term){
            Term a=(Term)o;
            if(a.getPredicate().name.compareTo(this.getPredicate().name)!=0)
                return 1;
            for(int i=0; i<a.getParameters().size(); i++){
                if(a.getParameters().get(i).name.compareTo(this.getParameters().get(i).name)!=0){
                    return 1;
                }
            }
            return 0;
        }
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
