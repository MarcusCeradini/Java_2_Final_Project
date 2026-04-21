import javax.swing.*;      // Swing components (JFrame, JPanel, Timer, etc.)
import java.awt.*;         // Graphics, Color, Dimension, etc.
import javax.swing.JOptionPane;

public class LangtonsAntSwing extends JPanel {

    // Grid size (number of cells, not pixels)
    private static final int WIDTH = 300;
    private static final int HEIGHT = 180;

    // Size of each square cell in pixels
    private int cellSize = 5;

    // 2D grid: true = white (visited), false = black (unvisited)
    private final boolean[][] grid = new boolean[WIDTH][HEIGHT];

    // Ant's current position (starts in center)
    private int x = WIDTH / 2;
    private int y = HEIGHT / 2;

    // Direction the ant is facing:
    // 0 = up, 1 = right, 2 = down, 3 = left
    private int dir = 0;

    // Timer to repeatedly update the simulation
    private Timer timer;

    // Color chosen by the user
    private Color userColor;

    // Opens a color picker dialog and returns the selected color
    public Color getColor() {
        return JColorChooser.showDialog(null, "Pick a Color", Color.decode("#00FF41"));
    }

    // Constructor: sets up simulation and timer
    public LangtonsAntSwing(int speed) {
        userColor = getColor();         // Ask user for a color
        setBackground(userColor);       // Set background color

        // Timer runs every "speed" milliseconds
        timer = new Timer(speed, e -> {
            // Run multiple steps per tick for faster movement
            for (int i = 0; i < 5; i++) {
                updateAnt();
            }
            repaint(); // Redraw the panel
        });
        timer.start(); // Start simulation
    }

    // Updates the ant's position and grid state
    private void updateAnt() {

        // If current square is white (true), turn left
        if (grid[x][y]) {
            dir = (dir + 3) % 4; // equivalent to -1 mod 4
        }
        // If black (false), turn right
        else {
            dir = (dir + 1) % 4;
        }

        // Flip the color of the current cell
        grid[x][y] = !grid[x][y];

        // Move forward one step based on direction
        switch (dir) {
            case 0: y--; break; // up
            case 1: x++; break; // right
            case 2: y++; break; // down
            case 3: x--; break; // left
        }

        // Wrap around edges (toroidal grid)
        x = (x + WIDTH) % WIDTH;
        y = (y + HEIGHT) % HEIGHT;
    }

    // Allows changing simulation speed dynamically
    public void setSpeed(int delay) {
        timer.setDelay(delay);
    }

    // Allows changing size of each cell
    public void setCellSize(int size) {
        this.cellSize = size;
        revalidate(); // Update layout
        repaint();    // Redraw
    }

    // Tells Swing how big this panel should be
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH * cellSize, HEIGHT * cellSize);
    }

    // Draws the grid and the ant
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw each cell
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                // If true → user color, else black
                g.setColor(grid[i][j] ? userColor : Color.BLACK);
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }

        // Draw the ant as a red circle
        g.setColor(Color.RED);
        g.fillOval(x * cellSize, y * cellSize, cellSize, cellSize);
    }

    // Main method: sets up window and controls
    public static void main(String[] args) {
        int speed = 50; // initial delay in ms

        JFrame frame = new JFrame("Langton's Ant - Swing");

        // Create simulation panel
        LangtonsAntSwing panel = new LangtonsAntSwing(speed);

        // --- SPEED SLIDER ---
        JSlider speedSlider = new JSlider(1, 1000, speed);
        speedSlider.setBorder(BorderFactory.createTitledBorder("Speed (ms delay)"));

        // When slider changes, update speed
        speedSlider.addChangeListener(e -> {
            panel.setSpeed(speedSlider.getValue());
        });

        // Panel to hold controls
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2, 1));
        controls.add(speedSlider);

        // Layout setup
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER); // simulation
        frame.add(controls, BorderLayout.SOUTH); // controls

        frame.pack(); // size window correctly
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true); // show window
    }
}