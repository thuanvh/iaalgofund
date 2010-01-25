package ia.reasoning.firstorder;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 20 janv. 2010
 * Time: 22:02:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class Literal {
    protected String name;

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
}
