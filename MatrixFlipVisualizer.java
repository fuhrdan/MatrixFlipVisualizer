import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MatrixFlipVisualizer extends JFrame {

    private int[][] matrix;
    private int rowSize;
    private int colSize;

    public MatrixFlipVisualizer(int[][] matrix) {
        this.matrix = matrix;
        this.rowSize = matrix.length;
        this.colSize = matrix[0].length;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Matrix Flip Visualizer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for displaying the matrix
        JPanel matrixPanel = new JPanel();
        matrixPanel.setLayout(new GridLayout(rowSize, colSize));
        JLabel[][] cellLabels = new JLabel[rowSize][colSize];

        // Initialize matrix display
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                JLabel cell = new JLabel(String.valueOf(matrix[i][j]), SwingConstants.CENTER);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cellLabels[i][j] = cell;
                matrixPanel.add(cell);
            }
        }

        // Button to perform transformation
        JButton transformButton = new JButton("Transform");
        transformButton.addActionListener(e -> performTransformation(cellLabels));

        // Add components to frame
        add(matrixPanel, BorderLayout.CENTER);
        add(transformButton, BorderLayout.SOUTH);
    }

    private void performTransformation(JLabel[][] cellLabels) {
        Map<String, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < rowSize; i++) {
            // Perform XOR transformation
            int[] transformedRow = new int[colSize];
            for (int j = 0; j < colSize; j++) {
                transformedRow[j] = matrix[i][j] ^ matrix[i][0];
            }

            // Convert row to a string key for hashing
            String key = Arrays.toString(transformedRow);
            hashTable.put(key, hashTable.getOrDefault(key, 0) + 1);

            // Update visual representation
            for (int j = 0; j < colSize; j++) {
                cellLabels[i][j].setText(String.valueOf(transformedRow[j]));
                cellLabels[i][j].setBackground(Color.LIGHT_GRAY);
                cellLabels[i][j].setOpaque(true);
            }
        }

        // Display result
        int maxCount = Collections.max(hashTable.values());
        JOptionPane.showMessageDialog(this, "Max Equal Rows After Flips: " + maxCount);
    }

    public static void main(String[] args) {
        // Sample matrix
        int[][] matrix = {
            {0, 1, 1},
            {1, 0, 0},
            {0, 1, 1}
        };

        // Run the application
        SwingUtilities.invokeLater(() -> {
            MatrixFlipVisualizer app = new MatrixFlipVisualizer(matrix);
            app.setVisible(true);
        });
    }
}
