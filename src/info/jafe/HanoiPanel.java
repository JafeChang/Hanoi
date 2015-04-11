package info.jafe;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HanoiPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1306328112546257716L;
	public static final int LINEWIDTH = 200;
	public static final int GAPWIDTH = 50;
	public static final int LINEUP = 480;
	public static final int RECTUP = 80;
	public static final int RECTWIDTH = 2;
	public static final int ARROWLENGTH = 30;
	public static final int ARROWUP = 0;
	public static final int ARROWGAP = 5;
	private BlockHeap[] blockHeap = new BlockHeap[6];
	private Block[] block = new Block[9];
	private int arrowPosition = 0;
	private long seconds = 0l;
	private long startTime = 0l;
	private HanoiWindow hanoiWindow;
	private static int hanoiNumber = 3;
	private boolean paused=false;

	/**
	 * Create a new panel which contains the Hanoi blocks.
	 * 
	 * @param hanoiNumber
	 *            Numbers of Hanoi blocks, which needs to be no more than 9.
	 * 
	 * 
	 */
	HanoiPanel(int hanoiNumber, HanoiWindow hanoiWindow) {
		init(hanoiNumber);

		this.hanoiWindow = hanoiWindow;

	}

	public void init(int hanoiNumber) {
		HanoiPanel.hanoiNumber = hanoiNumber;
		Block.setStepsMax(hanoiNumber);
		for (int i = 0; i < 6; i++) {// 0, 1, 2 are handed heap, 3, 4, 5 are
			// base heap;
			if (i < 3) {
				blockHeap[i] = new BlockHeap(false);
			} else {
				blockHeap[i] = new BlockHeap(true);
			}
		}
		for (int i = 0; i < hanoiNumber; i++) {
			block[i] = new Block(hanoiNumber - i);
			blockHeap[3].add(block[i]);
		}
		seconds = 0;
		startTime = System.currentTimeMillis();
		Timer timer = new Timer();
		Thread timerThread = new Thread(timer);
		timerThread.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		repeatPaint(g);
		g.setColor(c);
		drawArrow(g, arrowPosition);
		drawBlock(g);
		g.drawString("Score: " + getScore(), 10, this.getHeight() - 20);
		g.drawString("Time: " + seconds, this.getWidth() / 2 - 20,
				this.getHeight() - 20);
		g.drawString("Steps: " + Block.getSteps(), this.getWidth() - 100,
				this.getHeight() - 20);
	}

	void repeatPaint(Graphics g) {
		g.drawLine(GAPWIDTH + 1, LINEUP + 1, LINEWIDTH + GAPWIDTH, LINEUP + 1);
		g.drawLine(LINEWIDTH + GAPWIDTH * 2 + 1, LINEUP + 1, 2 * LINEWIDTH + 2
				* GAPWIDTH, LINEUP + 1);
		g.drawLine(2 * LINEWIDTH + 3 * GAPWIDTH + 1, LINEUP + 1, 3 * LINEWIDTH
				+ 3 * GAPWIDTH, LINEUP + 1);
		g.fillRect(LINEWIDTH / 2 + GAPWIDTH, RECTUP + 1, RECTWIDTH,
				Block.HEIGHT * 10);
		g.fillRect(3 * LINEWIDTH / 2 + 2 * GAPWIDTH, RECTUP + 1, RECTWIDTH,
				Block.HEIGHT * 10);
		g.fillRect(5 * LINEWIDTH / 2 + 3 * GAPWIDTH, RECTUP + 1, RECTWIDTH,
				Block.HEIGHT * 10);

	}

	public void restart(int hanoiNumber) {
		init(hanoiNumber);
		arrowPosition = 0;
	}

	public static int getHanoiNumber() {
		return HanoiPanel.hanoiNumber;
	}

	private void drawArrow(Graphics g, int position) {
		int x = (2 * position + 1) * LINEWIDTH / 2 + (position + 1) * GAPWIDTH;
		g.fillRect(x, ARROWUP, RECTWIDTH, ARROWLENGTH);
		g.drawLine(x - (int) ((double) ARROWLENGTH * 0.354),
				(int) ((double) ARROWLENGTH * 0.646), x + 1, ARROWLENGTH); // 0.354=Math.sqrt(2)/4
		g.drawLine(x - (int) ((double) ARROWLENGTH * 0.354) - 1,
				(int) ((double) ARROWLENGTH * 0.646), x, ARROWLENGTH); // 0.646=1-0.354
		g.drawLine(x + RECTWIDTH + (int) ((double) ARROWLENGTH * 0.354) - 1,
				(int) ((double) ARROWLENGTH * 0.646), x + RECTWIDTH - 2,
				ARROWLENGTH); // 0.354=Math.sqrt(2)/4
		g.drawLine(x + RECTWIDTH + (int) ((double) ARROWLENGTH * 0.354),
				(int) ((double) ARROWLENGTH * 0.646), x + RECTWIDTH - 1,
				ARROWLENGTH); // 0.646=1-0.354

	}

	private void drawBlock(Graphics g) {
		int[] x = new int[6];
		x[0] = LINEWIDTH / 2 + GAPWIDTH + 1;
		x[1] = 3 * LINEWIDTH / 2 + 2 * GAPWIDTH + 1;
		x[2] = 5 * LINEWIDTH / 2 + 3 * GAPWIDTH + 1;
		for (int i = 0; i < 3; i++) {
			if (blockHeap[i].isFull()) {
				Color c = g.getColor();
				g.setColor(blockHeap[i].getHead().getColor());
				g.fillRect(x[i] - blockHeap[i].getHead().getSize() * 10,
						ARROWLENGTH + ARROWGAP, blockHeap[i].getHead()
								.getSize() * 20, Block.HEIGHT);
				g.setColor(c);
			}
		}
		for (int i = 3; i < 6; i++) {
			if (!blockHeap[i].isEmpty()) {
				Block block = blockHeap[i].getHead();
				for (int j = 0; j < blockHeap[i].getCount(); j++) {
					Color c = g.getColor();
					g.setColor(block.getColor());
					g.fillRect(x[i - 3] - block.getSize() * 10, LINEUP
							- Block.HEIGHT * (j + 1) + 1, block.getSize() * 20,
							Block.HEIGHT);
					block = block.getRight();
					g.setColor(c);
				}
			}
		}
	}

	public void up() {
		int i = arrowPosition;
		if (blockHeap[i].isEmpty()) {
			if (!blockHeap[i + 3].isEmpty()) {
				blockHeap[i].add(blockHeap[i + 3].pull());
			}
		}
		// repaint();
	}

	public void down() {
		int i = arrowPosition;
		if (!blockHeap[i].isEmpty()) {
			if (blockHeap[i + 3].isEmpty()
					|| blockHeap[i].getHead().getSize() < blockHeap[i + 3]
							.getTail().getSize()) {
				blockHeap[i + 3].add(blockHeap[i].pull());
				Block.addStep();
			}
		}
		if (blockHeap[5].isFull()) {
			hanoiWindow.success();
		}
	}

	public void left() {
		int i = arrowPosition;
		if (i == 0) {
			arrowPosition = 2;
		} else {
			arrowPosition--;
		}
		if (!blockHeap[i].isEmpty()) {
			blockHeap[arrowPosition].add(blockHeap[i].pull());
		}
	}

	public void right() {
		int i = arrowPosition;
		if (i == 2) {
			arrowPosition = 0;
		} else {
			arrowPosition++;
		}
		if (!blockHeap[i].isEmpty()) {
			blockHeap[arrowPosition].add(blockHeap[i].pull());
		}
	}

	private int getScore() {
		double score = (10 - (10 / ((double) seconds / 120 + 1)))
				* Block.getSteps();
		return (int) score;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	class Timer implements Runnable {
		@Override
		public void run() {
			long currentTime;
			while (true) {
				currentTime = System.currentTimeMillis();
				seconds = (currentTime - startTime) / 1000l;
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(paused){
					while(paused){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}
}
