import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class State implements Comparable<State>{
    final private State parent;
    private List<Integer> sequence;
    private int moveApplied;
    int cost;
    int prediction = 0;

    public State(State parent, int cost, int moveApplied, List<Integer> sequence) {
        this.parent = parent;
        this.cost = cost;
        this.sequence = sequence;
        this.moveApplied = moveApplied;
    }

    public void apply_T(int move) {
        List<Integer> elements = new ArrayList<>();
        for(int i=0; i<move; i++) {
            int element = this.sequence.remove(0);
            elements.add(element);
        }

        Collections.reverse(elements);

        List<Integer> newSeq = new ArrayList<>();
        for(int i=0; i<move; i++) {
            newSeq.add(elements.get(i));
        }

        newSeq.addAll(this.sequence);
        this.sequence = newSeq;
        this.moveApplied = move;
    }

    public void findHeuristicFunction(List<Integer> goal) {

        int x = 1;
        int y = 0;

        for (int i=0; i<this.sequence.size(); i++) {
            x *= Math.abs(this.sequence.get(i) - 1 - i);
        }

        this.prediction = x;

    }

    public boolean isTerminal(List<Integer> goal) { return goal.equals(this.sequence); }

    public State getParent() { return parent; }

    public List<Integer> getSequence() { return sequence; }

    public int getMoveApplied() { return moveApplied; }

    public int getCost() { return cost; }

    public void setSequence(List<Integer> sequence) { this.sequence = sequence; }

    @Override
    public int compareTo(State o) { return Integer.compare(this.cost + prediction, o.getCost() + o.prediction); }

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", sequence=" + sequence +
                ", moveApplied=" + moveApplied +
                ", cost=" + cost +
                '}';
    }
}
