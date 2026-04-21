import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;

public class LangtonsAntSwing extends JPanel {

    private static final int WIDTH = 150;
    private static final int HEIGHT = 90;

    private int cellSize = 10;

    private final boolean[][] grid = new boolean[WIDTH][HEIGHT];
    private int x = WIDTH / 2;
    private int y = HEIGHT / 2;
    private int dir = 0;

    private Timer timer;

    private Color userColor;

    public Color getColor() {
        return JColorChooser.showDialog(null, "Pick a Color", Color.WHITE);
    }




    public LangtonsAntSwing(int speed) {
        userColor = getColor();
        setBackground(userColor);

        timer = new Timer(speed, e -> {
            for (int i = 0; i < 5; i++) {
                updateAnt();
            }
            repaint();
        });
        timer.start();
    }

    private void updateAnt() {
        if (grid[x][y]) {
            dir = (dir + 3) % 4;
        } else {
            dir = (dir + 1) % 4;
        }

        grid[x][y] = !grid[x][y];

        switch (dir) {
            case 0: y--; break;
            case 1: x++; break;
            case 2: y++; break;
            case 3: x--; break;
        }

        x = (x + WIDTH) % WIDTH;
        y = (y + HEIGHT) % HEIGHT;
    }

    public void setSpeed(int delay) {
        timer.setDelay(delay);
    }

    public void setCellSize(int size) {
        this.cellSize = size;
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH * cellSize, HEIGHT * cellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                g.setColor(grid[i][j] ? userColor : Color.WHITE);
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }

        g.setColor(Color.RED);
        g.fillOval(x * cellSize, y * cellSize, cellSize, cellSize);
    }

    public static void main(String[] args) {
        int speed = 50;

        JFrame frame = new JFrame("Langton's Ant - Swing");
        LangtonsAntSwing panel = new LangtonsAntSwing(speed);

        // --- SPEED SLIDER ---
        JSlider speedSlider = new JSlider(1, 200, speed);
        speedSlider.setBorder(BorderFactory.createTitledBorder("Speed (ms delay)"));
        speedSlider.addChangeListener(e -> {
            panel.setSpeed(speedSlider.getValue());
        });

        // Controls panel
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2, 1));
        controls.add(speedSlider);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}