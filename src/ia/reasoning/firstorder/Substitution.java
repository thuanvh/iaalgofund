package ia.reasoning.firstorder;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 20 janv. 2010
 * Time: 22:09:12
 * To change this template use File | Settings | File Templates.
 */
public class Substitution {
    private Hashtable<Variable,Literal> replacement;
    public Substitution(){
        replacement=new Hashtable();
    }

    public Hashtable<Variable,Literal> getReplacement() {
        return replacement;
    }

    public void setReplacement(Hashtable<Variable,Literal> replacement) {
        this.replacement = replacement;
    }
    public Substitution compose(Substitution s){
        Substitution newSub=new Substitution();
        if(s!=null)
            newSub.replacement.putAll(s.replacement);
        newSub.replacement.putAll(this.replacement);
        return newSub;
    }

    @Override
    public String toString() {
        Enumeration<Variable> iter= this.replacement.keys();
        String a="{";
        while(iter.hasMoreElements()){
            Variable var=iter.nextElement();
            Literal replacement=this.replacement.get(var);
            a+=var+"/"+replacement;
            a+=",";
        }
        a+="}";
        return a;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
