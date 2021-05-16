import java.util.*;

public class AStar {
    public static void computeBestSolution(List<Integer> input, List<Integer> goal) {
        int N = input.size();
        State initial = new State(null, 0, 0, input);
        PriorityQueue<State> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(initial);
        State current = null;
        int expansions = 0;
        while (!priorityQueue.isEmpty()) {
            current = priorityQueue.poll();
            expansions++;

            for (int move = 2; move <= N; move++) {
                if (move != current.getMoveApplied()) {
                    ArrayList<Integer> newSequence = new ArrayList<>(current.getSequence());
                    State newState = new State(current, current.getCost() + 1, move, newSequence);
                    newState.apply_T(move);
                    newState.findHeuristicFunction(goal);
                    if (newState.isTerminal(goal)){
                        printResults(input, newState, expansions);
                        return;
                    }
                    priorityQueue.add(newState);
                }
            }
        }
    }

    private static void printResults(List<Integer> input, State current, int expansions) {
        StringBuilder builder =  new StringBuilder();
        for (Integer move : getBestMoves(current)) {
            builder.append("T(").append(move.toString()).append(")").append(", ");
        }
        String moves = builder.toString();
        System.out.println("Initial Sequence: " + input);
        System.out.println("Best Moves: " + moves.substring(0, moves.length() - 2));
        assert current != null;
        System.out.println("Expansions: " + expansions);
        System.out.println("Cost: " + current.getCost());
        System.out.println("Goal Sequence: " + current.getSequence().toString());
    }

    public static List<Integer> getBestMoves(State goalState) {
        List<Integer> moves = new ArrayList<>();

        for (State state = goalState; state != null; state = state.getParent()) {
            moves.add(state.getMoveApplied());
        }
        Collections.reverse(moves);
        moves.remove(0);
        return moves;
    }
}
