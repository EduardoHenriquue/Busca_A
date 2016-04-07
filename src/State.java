import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eduardo on 04/04/2016.
 */
public class State {
    private String name;
    private int estimate;
    private Map<State, Integer> actions; // Array de cidades que se conectam ao estado


    public State(String name, int estimate){
        this.name = name;
        this.estimate = estimate;
        this.actions = new HashMap<State, Integer>();
    }

    public String getName() {
        return name;
    }

    public int getEstimate() {
        return this.estimate;
    }

    public void addActions(State state, int cost){
        this.actions.put(state, cost);
    }

    public Map<State, Integer> getActions(){
        return this.actions;
    }

}
