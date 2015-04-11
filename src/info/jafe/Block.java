package info.jafe;

import java.awt.Color;

public class Block {
	public static final int HEIGHT = 40;
	private static int steps = 0;
	private static int stepsMax = 0;
	private int size;
	private Block left;
	private Block right;

	Block(int size) {
		this.size = size;
		steps = 0;// maximum size is 9;
		// this.left = left;
		// this.right = right;
	}

	public static void addStep() {
		if (steps < Integer.MAX_VALUE/10) {
			steps++;
		}
	}

//	public static int getScore() {
//		return Math.abs(stepsMax - steps);
//	}
	public static int getSteps(){
		return steps;
	}
	public static void link(Block left, Block right) {
		left.right = right;
		right.left = left;
	}

	public static void setStepsMax(int hanoiNumber) {
		stepsMax = (int) Math.pow(2.0, (double) hanoiNumber);
	}

	public Color getColor() {
		switch (this.size) {
		case 1:
			return Color.RED;
		case 2:
			return Color.ORANGE;
		case 3:
			return Color.YELLOW;
		case 4:
			return Color.GREEN;
		case 5:
			return Color.BLUE;
		case 6:
			return Color.CYAN;
		case 7:
			return Color.PINK;
		case 8:
			return Color.GRAY;
		case 9:
			return Color.BLACK;
		default:
			return Color.WHITE; // something wrong happened!!
		}
	}

	public Block getLeft() {
		return this.left;
	}

	public Block getRight() {
		return this.right;
	}

	public int getSize() {
		return this.size;
	}

	public int getWidth() {
		return this.size * 20;
	}
}
