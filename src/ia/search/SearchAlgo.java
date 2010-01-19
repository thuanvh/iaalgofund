package ia.search;

/**
 * Created by IntelliJ IDEA.
 * User: thuan
 * Date: 6 janv. 2010
 * Time: 20:12:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class SearchAlgo {
    Puzzle stateCurrent;
    Puzzle stateFinish;
    int cost;

    public Puzzle getStateCurrent() {
        return stateCurrent;
    }

    public void setStateCurrent(Puzzle stateCurrent) {
        this.stateCurrent = stateCurrent;
    }

    public Puzzle getStateFinish() {
        return stateFinish;
    }

    public void setStateFinish(Puzzle stateFinish) {
        this.stateFinish = stateFinish;
    }

    public SearchAlgo() {
    }

    ;

    public SearchAlgo(Puzzle init, Puzzle fin) {
        stateCurrent = init;
        stateFinish = fin;
    }

    protected abstract void initSearch();

    protected abstract Puzzle doSearch();

    public void Search() {
        initSearch();
        doSearch();
    }
}
