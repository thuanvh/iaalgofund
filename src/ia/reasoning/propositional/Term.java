package ia.reasoning.propositional;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 18 janv. 2010
 * Time: 21:25:17
 * To change this template use File | Settings | File Templates.
 */
public class Term implements Comparable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Object o) {
        if(o instanceof Term){
            return this.name.compareTo(((Term)o).name);
        }
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
