package info.jafe;

public class BlockHeap {
	private int count = 0;
	private Block head;
	private Block tail;
	private boolean isBase = true;// true means max count is 9; false means max
									// count is 1;

	BlockHeap(boolean isBase) {
		this.isBase = isBase;
	}

	public boolean isEmpty() {
		if (count == 0)
			return true;
		else
			return false;
	}

	public boolean isFull() {
		if (isBase) {
			if (count < HanoiPanel.getHanoiNumber())
				return false;
			else
				return true;
		} else {
			if (count < 1)
				return false;
			else
				return true;
		}
	}

	public boolean add(Block block) {
		if (isFull())
			return false;
		else {
			if (isEmpty()) {
				head = tail = block;
				count = 1;
				// Block.link(block, block);
				return true;
			} else {
				if (block.getSize() < tail.getSize()) {
					Block.link(tail, block);
					tail = block;
					count++;
					return true;
				} else {
					return false;
				}
			}

		}
	}

	public int getCount() {
		return count;
	}

	public Block getTail() {
		return tail;
	}

	public Block getHead() {
		return head;
	}

	public Block pull() {
		Block block;
		if (isEmpty()) {
			return null;
		} else {
			if (!isBase) {
				block = tail;
				head = null;
				tail = null;
				count = 0;
				return block;
			} else {
				if (count != 1) {
					block = tail;
					tail = block.getLeft();
					count--;
					return block;
				} else {
					block = tail;
					tail = null;
					head = null;
					count = 0;
					return block;
				}
			}
		}
	}
}
