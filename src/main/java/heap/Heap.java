package main.java.heap;

/**
 * Represents the Heap data structure.
 * 
 * @author Manpreet Nanreh
 *
 * @param <T>
 */
public class Heap<T extends HeapItem> {

	protected T[] items;
	protected int maxHeapSize;
	protected int currentItemCount;

	/**
	 * Create a new Heap object with the given size.
	 * 
	 * @param maxHeapSize
	 */
	@SuppressWarnings("unchecked")
	public Heap(int maxHeapSize) {
		this.maxHeapSize = maxHeapSize;
		items = (T[]) new HeapItem[maxHeapSize];
		currentItemCount = 0;
	}

	/**
	 * Return true if the Heap is empty.
	 * 
	 * @return	true if the Heap is empty;
	 * 			false otherwise.
	 */
	public boolean isEmpty() {
		return currentItemCount == 0;
	}

	/**
	 * Return true of Heap is full.
	 * 
	 * @return	true if the Heap is full;
	 * 			false otherwise.
	 */
	public boolean isFull() {
		return currentItemCount == maxHeapSize;
	}

	/**
	 * Adds the item to its correct position in the Heap.
	 * 
	 * @param item
	 * @throws HeapFullException
	 */
	public void add(T item) throws HeapFullException {
		if (isFull())
			throw new HeapFullException();
		else {
			item.setHeapIndex(currentItemCount);
			items[currentItemCount] = item;
			sortUp(item);
			currentItemCount++;
		}
	}

	/**
	 * Return true if the Heap contains the item.
	 * 
	 * @param item
	 * @return true if the item is contained in the Heap;
	 * 		   false otherwise.
	 */
	public boolean contains(T item)	{
		return items[item.getHeapIndex()].equals(item);
	}

	/**
	 * Gets the number of items currently in the Heap.
	 * 
	 * @return int The number of items in the Heap.
	 */
	public int count() {
		return currentItemCount;
	}

	/**
	 * Update the location of item in the Heap.
	 * 
	 * @param item
	 */
	public void updateItem(T item) {
		sortUp(item);
	}

	/**
	 * Removes and returns the element at the top of Heap.
	 * 
	 * @return T The generic item at the top of Heap.
	 * @throws HeapEmptyException
	 */
	public T removeFirst() throws HeapEmptyException {
		if (isEmpty())
			throw new HeapEmptyException();
		else {
			T firstItem = items[0];
			currentItemCount--;
			items[0] = items[currentItemCount];
			items[0].setHeapIndex(0);
			sortDown(items[0]);
			return firstItem;
		}
	}
	
	/**
	 * Sort the item up to its correct position in the Heap.
	 * 
	 * @param item
	 */
	private void sortUp(T item) {
		int parentIndex = (item.getHeapIndex() - 1)/2;
		while(true) {
			T parentItem = items[parentIndex];
			if(item.compareTo(parentItem) > 0) 
				swap(item, parentItem);
			else break;
			parentIndex = (item.getHeapIndex()-1)/2;
		}
	}
	
	/**
	 * Sort the item down to its correct position in the Heap.
	 * 
	 * @param item
	 */
	private void sortDown(T item) {
		while(true) {
			int childIndexLeft = 2 * item.getHeapIndex() + 1;
			int childIndexRight = 2 * item.getHeapIndex() + 2;
			int swapIndex = 0;
			if(childIndexLeft < currentItemCount) {
				swapIndex = childIndexLeft;
				if(childIndexRight < currentItemCount) {
					if(items[childIndexLeft].compareTo(items[childIndexRight]) < 0) {
						swapIndex = childIndexRight;
					}
				}
				if(item.compareTo(items[swapIndex]) < 0) {
					swap(item,items[swapIndex]);
				} else
					return;
			} else //no children
				return;
		}
	}
	
	/**
	 * Swap the position of two items in the Heap.
	 * 
	 * @param itemA
	 * @param itemB
	 */
	private void swap(T itemA, T itemB) {
		items[itemA.getHeapIndex()] = itemB;
		items[itemB.getHeapIndex()] = itemA;
		int itemAIndex = itemA.getHeapIndex();
		itemA.setHeapIndex(itemB.getHeapIndex());
		itemB.setHeapIndex(itemAIndex);
	}
	
	/**
	 * Gets the maximum size of Heap.
	 * 
	 * @return int Max Heap size.
	 */
	public int getMaxHeapSize() {
		return maxHeapSize;
	}

	/**
	 * Gets the current number of items in the Heap.
	 * 
	 * @return int Current number of items in Heap.
	 */
	public int getCurrentItemCount() {
		return currentItemCount;
	}
}