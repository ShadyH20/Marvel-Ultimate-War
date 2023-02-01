package engine;

import model.world.Champion;
import model.world.Hero;

public class PriorityQueue {

	private Comparable[] elements;
	private int nItems;
	private int maxSize;

	public PriorityQueue(int size) {
		maxSize = size;
		elements = new Comparable[maxSize];
		nItems = 0;
	}

	public void insert(Comparable item) {

		int i;
		for (i = nItems - 1; i >= 0 && item.compareTo(elements[i]) > 0; i--)
			elements[i + 1] = elements[i];

		elements[i + 1] = item;
		nItems++;
	}

	public Comparable remove() {
		nItems--;
		return elements[nItems];
	}

	public boolean isEmpty() {
		return (nItems == 0);
	}

	public boolean isFull() {
		return (nItems == maxSize);
	}

	public Comparable peekMin() {
		return elements[nItems - 1];
	}

	public int size() {
		return nItems;
	}
	public static void main(String[]args) {
		
		// Check That compareTo working
	
		Hero h1 =  new Hero("Spiderman", 1,1,1,23,1,1);
		Hero h2 =  new Hero("Superman", 1,1,1,230,1,1);
		PriorityQueue pq = new PriorityQueue(5);
		pq.insert(h1);
		pq.insert(h2);
		System.out.println(((Champion)pq.remove()).getName());
		
	}
}
