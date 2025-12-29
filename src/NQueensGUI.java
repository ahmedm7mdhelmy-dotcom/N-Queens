import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NQueensGUI extends JFrame {
    private int N = 8;
    private JButton[][] cells;
    private List<NQueensSolver.Step> steps;
    private int stepIndex = 0;
    private Timer timer;
    private JLabel successLabel;
    private JSlider speedSlider;

    public NQueensGUI() {
        setTitle("N-Queens Backtracking Visualizer");
        setSize(600, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        JPanel boardPanel = new JPanel(new GridLayout(N, N));
        cells = new JButton[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j] = new JButton();
                cells[i][j].setFocusable(false);
                cells[i][j].setFont(new Font("Segoe UI Symbol", Font.BOLD, 36));
                resetCellColor(i, j);
                boardPanel.add(cells[i][j]);
            }
        }

        successLabel = new JLabel("Algorithm Working...", SwingConstants.CENTER);
        successLabel.setPreferredSize(new Dimension(100, 40));
        successLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        successLabel.setVisible(false);

        speedSlider = new JSlider(50, 1000, 400);
        speedSlider.setInverted(true);
        JLabel sliderLabel = new JLabel("Speed Control: ");

        JButton solveBtn = new JButton("Visualise Solve");
        JButton resetBtn = new JButton("Reset");

        solveBtn.setBackground(new Color(70, 130, 180));
        solveBtn.setForeground(Color.WHITE);

        JPanel controlPanel = new JPanel();
        controlPanel.add(sliderLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(solveBtn);
        controlPanel.add(resetBtn);

        solveBtn.addActionListener(e -> startAnimation());
        resetBtn.addActionListener(e -> resetBoard());

        add(successLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startAnimation() {
        resetBoard();
        NQueensSolver solver = new NQueensSolver(N);
        solver.solve(0);
        steps = solver.getSteps();
        stepIndex = 0;
        successLabel.setText("Finding Solution...");
        successLabel.setVisible(true);
        successLabel.setBackground(Color.ORANGE);

        timer = new Timer(speedSlider.getValue(), e -> showNextStep());
        timer.start();
    }

    private void showNextStep() {
        if (stepIndex >= steps.size()) {
            timer.stop();
            successLabel.setText("✔ Solution Found!");
            successLabel.setBackground(new Color(40, 167, 69));
            successLabel.setForeground(Color.WHITE);
            successLabel.setOpaque(true);
            return;
        }

        NQueensSolver.Step step = steps.get(stepIndex);
        if (step.isPlacing) {
            cells[step.row][step.col].setText("♛");
            cells[step.row][step.col].setForeground(new Color(0, 51, 102));
            cells[step.row][step.col].setBackground(new Color(144, 238, 144));
        } else {
            // BACKTRACKING EFFECT
            cells[step.row][step.col].setText("✘");
            cells[step.row][step.col].setBackground(new Color(255, 99, 71));

            Timer blinker = new Timer(150, ev -> {
                cells[step.row][step.col].setText("");
                resetCellColor(step.row, step.col);
            });
            blinker.setRepeats(false);
            blinker.start();
        }

        timer.setDelay(speedSlider.getValue());
        stepIndex++;
    }

    private void resetCellColor(int i, int j) {
        if ((i + j) % 2 == 0) cells[i][j].setBackground(Color.WHITE);
        else cells[i][j].setBackground(new Color(220, 220, 220));
    }

    private void resetBoard() {
        if (timer != null) timer.stop();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j].setText("");
                resetCellColor(i, j);
            }
        }
        successLabel.setVisible(false);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(NQueensGUI::new);
    }
}