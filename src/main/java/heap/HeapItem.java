package main.java.heap;

/**
 * Represents the Heap item to be used in the Heap.
 * 
 * @author Manpreet Nanreh
 */
public interface HeapItem extends Comparable<HeapItem>{
	
	/**
	 * Sets the index of the Heap item in the Heap.
	 * 
	 * @param index
	 */
	public void setHeapIndex(int index);
	
	/**
	 * Gets the index of the Heap item in the Heap.
	 * 
	 * @return int The Heap index of the item. 
	 */
	public int getHeapIndex();

}
