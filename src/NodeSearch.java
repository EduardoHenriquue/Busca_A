import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 04/04/2016.
 */
public class NodeSearch implements Comparable<NodeSearch>{

    private State state;
    private State parent;
    private int costG; // custo entre dois nós


    public NodeSearch(State state, int cost){
        this.state = state;
        this.costG = cost;

    }

    public int getCostG() {
        return costG;
    }

    public void setParent(State parent){
        this.parent = parent;
    }

    public State getState() {
        return state;
    }

    public State getParent() {
        return parent;
    }

    @Override
    public int compareTo(NodeSearch node) {
        if(this.getState().getEstimate()+this.costG < node.getCostG()+node.getState().getEstimate()){
            return -1;
        } else if(this.costG+this.getState().getEstimate() > node.getCostG()+node.getState().getEstimate()){
            return 1;
        }
        return 0;
    }
}
