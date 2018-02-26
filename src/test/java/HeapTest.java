package test.java;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.heap.Heap;
import main.java.heap.HeapEmptyException;
import main.java.heap.HeapFullException;
import main.java.heap.HeapItem;

public class HeapTest {
	//
	//
	// Functionality tests
	
	@Test(timeout = 100)
	public void testHeap() {
		// Testing constructor and checking values set for fields
		Heap<HItem> h = new Heap<>(1);
		assertEquals(h.getMaxHeapSize(), 1);
		assertEquals(h.getCurrentItemCount(), 0);
	}

	@Test(timeout = 100)
	public void testIsEmpty() throws HeapFullException {
		// empty heap with size 0
		Heap<HItem> h1 = new Heap<>(0);
		assertTrue(h1.isEmpty());

		// empty heap with non-zero size
		Heap<HItem> h2 = new Heap<>(2);
		assertTrue(h2.isEmpty());

		// non-empty heap
		HItem n = new HItem("test");
		h2.add(n);
		assertFalse(h2.isEmpty());
	}

	@Test(timeout = 100)
	public void testIsFull() throws HeapFullException {
		// zero-size heap
		Heap<HItem> h1 = new Heap<>(0);
		assertTrue(h1.isFull());

		// not full heap with non-zero size
		Heap<HItem> h2 = new Heap<>(1);
		assertFalse(h2.isFull());

		// testing a heap during each step of adding items until it is full
		Heap<HItem> h3 = new Heap<>(2);
		HItem n = new HItem("abc");
		h3.add(n);
		assertFalse(h3.isFull());
        
		HItem n1 = new HItem("aaa"); 
		h3.add(n1);
		assertTrue(h3.isFull());

	}

	@Test(timeout = 100)
	public void testAdd() throws HeapFullException {
		// adding first item to the heap

		Heap<HItem> h = new Heap<>(2);
		HItem n = new HItem("bc");
		h.add(n);

		assertEquals(h.getCurrentItemCount(), 1);
		// adding last item to the heap
		HItem n2 = new HItem("bb");
		h.add(n2);

		assertEquals(n2.getHeapIndex(), 0);
		assertEquals(h.getCurrentItemCount(), 2);

		// adding extra element
		HItem n3 = new HItem("dd");
		boolean thrown = false;
		try {
			h.add(n3);
		} catch (HeapFullException e) {
			//fail("Not enough space in heap to add extra element");
			thrown = true;
		}
		assertTrue("Not enough space in heap to add extra element",thrown);
		try {
			HItem r1 = h.removeFirst();
			assertEquals(r1, n2);
		} catch (Exception e) {
			fail("Unable to remove existing element!");
		}
		try {
			HItem r2 = h.removeFirst();
			assertEquals(r2, n);
		} catch (Exception e) {
			fail("Unable to remove existing element!");
		}
	}

	@Test(timeout = 100)
	public void testContains() throws HeapFullException {
		Heap<HItem> h = new Heap<>(2);
		HItem n = new HItem("abc");
		HItem n2 = new HItem("ced");
		h.add(n2);

		assertTrue(h.contains(n2));
		assertFalse(h.contains(n));

		h.add(n);
		assertTrue(h.contains(n));
	}

	@Test(timeout = 100)
	public void testCount() throws HeapFullException {
		// size zero
		Heap<HItem> h = new Heap<>(0);
		assertEquals(h.count(), 0);

		// adding first item
		Heap<HItem> h1 = new Heap<>(2);
		HItem n = new HItem("abc");
		HItem n2 = new HItem("aaa");
		h1.add(n2);
		assertEquals(h1.count(), 1);

		// adding second item
		h1.add(n);
		assertEquals(h1.count(), 2);
	}

	@Test(timeout = 100)
	public void testUpdateItem() throws HeapFullException {
		// adding first item
		HItem n = new HItem("bbb");
		Heap<HItem> h = new Heap<>(3);
		h.add(n);
		h.updateItem(n);

		assertEquals(n.getHeapIndex(), 0);
		assertEquals(h.getCurrentItemCount(), 1);

		// adding and updating last item to the heap
		HItem n2 = new HItem("ccc");
		h.add(n2);
		n2.value = "aaa";
		h.updateItem(n2);

		assertEquals(n2.getHeapIndex(), 0);
		assertEquals(h.getCurrentItemCount(), 2);
		try {
			HItem r1 = h.removeFirst();
			assertEquals(r1, n2);
		} catch (Exception e) {
			fail("Unable to remove existing element!");
		}
		try {
			HItem r2 = h.removeFirst();
			assertEquals(r2, n);
		} catch (Exception e) {
			fail("Unable to remove existing element!");
		}
	}

	@Test(timeout = 100)
	public void testRemoveFirst() throws HeapFullException, HeapEmptyException {
		// removing from heap of size 0
		Heap<HItem> h = new Heap<>(3);
		boolean thrown = false;
		try {
			h.removeFirst();
		} catch (HeapEmptyException e) {
			thrown = true;
		}
		assertTrue(thrown);
		// removing from heap of size 1
		HItem n2 = new HItem("aaa");
		h.add(n2);
		assertEquals(h.removeFirst(), n2);
		assertEquals(h.getCurrentItemCount(), 0);

		// removing from heap of size 2
		HItem n = new HItem("bbb");
		h.add(n);
		HItem n1 = new HItem("ccc");
		h.add(n1);
		assertEquals(h.removeFirst(), n);
		assertEquals(h.getCurrentItemCount(), 1);
	}

	/**
	 * Heap item which will be used in the test cases for Heap object.
	 */
	private class HItem implements HeapItem {
		int heapIndex;
		String value;

		/**
		 * Create an Hitem object with the given value.
		 * 
		 * @param value
		 */
		public HItem(String value) {
			this.value = value;
		}

		@Override
		public int compareTo(HeapItem o) {
			// TODO Auto-generated method stub
			String s = ((HItem) o).value;
			return -value.compareTo(s);
		}

		@Override
		public void setHeapIndex(int index) {
			// TODO Auto-generated method stub
			heapIndex = index;
		}

		@Override
		public int getHeapIndex() {
			// TODO Auto-generated method stub
			return heapIndex;
		}

		@Override
		public boolean equals(Object o) {
			String s = ((HItem) o).value;
			return value.equals(s);
		}
	}
}