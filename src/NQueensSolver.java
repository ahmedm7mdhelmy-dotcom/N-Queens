import java.util.ArrayList;
import java.util.List;

public class NQueensSolver {
    private int N;
    private int[] board;
    public static class Step {
        int row, col;
        boolean isPlacing;
        Step(int r, int c, boolean p) { this.row = r; this.col = c; this.isPlacing = p; }
    }

    private List<Step> steps = new ArrayList<>();

    public NQueensSolver(int N) {
        this.N = N;
        this.board = new int[N];
        for(int i=0; i<N; i++) board[i] = -1;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col) return false;
            if (Math.abs(board[i] - col) == Math.abs(i - row)) return false;
        }
        return true;
    }

    public boolean solve(int row) {
        if (row == N) return true;

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                board[row] = col;
                steps.add(new Step(row, col, true));

                if (solve(row + 1)) return true;


                steps.add(new Step(row, col, false));
                board[row] = -1;
            }
        }
        return false;
    }

    public List<Step> getSteps() { return steps; }
}