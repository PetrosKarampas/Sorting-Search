import java.util.*;

public class UCS {

    public void computeBestSolution(List<Integer> input, List<Integer> goal) {
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
                    if (newState.isTerminal(goal)){
                        printResults(input, newState, expansions);
                        return;
                    }
                    priorityQueue.add(newState);
                }
            }
        }
    }

    private void printResults(List<Integer> input, State current, int expansions) {
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

    public List<Integer> getBestMoves(State goalState) {
        List<Integer> moves = new ArrayList<>();
        for (State state = goalState; state != null; state = state.getParent()) {
            moves.add(state.getMoveApplied());
        }
        Collections.reverse(moves);
        moves.remove(0);
        return moves;
    }

    public static void main(String[] args) {

        List<Integer> input = Arrays.asList(9,1,8,2,7,3,6,4,5);
//        6,5,4,7,3,2,8,1,9
//        Scanner in = new Scanner(System.in);
//        String seq = in.next();
//
//        String[] numbers = seq.split(",");
//
//        List<Integer> input = new ArrayList<>();
//        for (String number : numbers) {
//            input.add(Integer.parseInt(number));
//        }
//
        List<Integer> goal = new ArrayList<>(input);
        Collections.sort(goal);

        UCS ucs = new UCS();
        System.out.println("|============- UCS -============|");
        ucs.computeBestSolution(input, goal);
        System.out.println("\n|=============- A* -============|");
        AStar.computeBestSolution(input, goal);
    }
}
