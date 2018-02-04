public class BudgetList {
	private class Entry {
		public double amount;
		public Entry prev;
		public Entry next;

		public Entry(double amount) {
			this.amount = amount;
			prev = null;
			next = null;
		}

		public double get()
		{
			return amount;
		}
	}
	private double totalAmount = 0;
	private int numitems = 0;
	private int index;
	private Entry front;
	private Entry back;
	private Entry cursor;

	public void Budget() {
		front = back = null;
		cursor = null;
		totalAmount = 0;
		numitems = 0;
		index = 0;
	}

	public int length() {
		return numitems;
	}

	public int index() { 
		if (cursor != null) {
			return index; 
		}
		return -1;
	}

	double front() {
		if (isEmpty()) {
			throw new RuntimeException("List Error: Front() called on empty List");
		}
		return front.amount;
	}

	double back() {
		if (isEmpty()) {
			throw new RuntimeException("List Error: back() called on empty List");
		}
		return back.amount;
	}

	double get() {
		if (isEmpty() || index() < 0) {
			throw new RuntimeException("List Error: get() called on empty List");
		}
		return cursor.amount;
	}

	double total() {
		return totalAmount;
	}

	boolean isEmpty() {
		return numitems == 0;
	}
	void moveFront() {    
		if (isEmpty()) {
			throw new RuntimeException("List Error: moveFront() called on empty List");
		}
		else {
			cursor = front;
			index = 0;
		}
	}

	void moveBack() {  
		if (isEmpty()) {
			throw new RuntimeException("List Error: moveback() called on empty List");
		}
		else {
			cursor = back;
			index = length()-1;
		}
	}
	void movePrev() {   
		if (cursor == front) {
			index = -1;
			cursor = null;
		}
		else if (cursor != null) {
			cursor = cursor.prev;
			index--;
		}
	}

	void moveNext() {    
		if (cursor == back) {
			index = -1;
			cursor = null;
		}
		else {
			cursor = cursor.next;
			index++;
		}
	}
	
	void add(double amount) {	
		Entry newData = new Entry(amount);
		if (isEmpty()) {
			front = back = newData;
			numitems++;
		}
		else {
			back.next = newData;
			newData.prev = back;
			back = newData;
			numitems++;
		}
		totalAmount += amount;
	}

	void deleteFront() {  

		if (isEmpty()) {
			throw new RuntimeException("List Error: deleteFront() called on empty List");
		} 
		else if (front == back) {
			cursor = front = back = null;
		}
		else {
			if (cursor == front) {
				cursor = null;
			}
			front = front.next;
			front.prev = null;
		}
		numitems--;
		index--;
	}

	void deleteBack() {

		if (isEmpty()) {
			throw new RuntimeException("List Error: deleteBack() called on empty List");	
		} 
		else if (front == back) {
			cursor = front = back = null;
		} 
		else {
			if (cursor == back) {
				cursor = null;
			}
			back = back.prev;
			back.next = null;
		}
		numitems--;
	}

	void delete(int index) {

		for(moveFront(); index() != -1; moveNext()) {
			if(index() == index) {
				if (isEmpty() || index() < 0) {
					throw new RuntimeException("List Error: delete() called on empty List");
				} 
				else if (cursor == front && cursor == back) {
					cursor = front = back = null;
				} 
				else if (cursor == front) {
					deleteFront();
				} 
				else if (cursor == back) {
					deleteBack();		
				} 
				else if (cursor.prev == null && cursor.next == null) {
					front = back = null;
					index--;
					numitems--;
				} 
				else {
					cursor.prev.next = cursor.next;
					cursor.next.prev = cursor.prev;
					cursor = null;
					index--;
					numitems--;
				}
			}
		}	
	}

 	void changeEntry(int index, double value) {
 		int ind = 1;
 		double temp;
 		System.out.println("ind: " + ind);

 		Entry n = front;
 		while(n != null)
 		{
 			//System.out.println("index: " + index());
 			if(value == 0.0) {
 				if(ind == index) {
 					delete(ind);
 					break;
 				}
 			}
 			else if(ind == index && value > 0) {
 				temp = n.amount;
 				n.amount = value;
 				totalAmount += (-temp) + value;
 				System.out.println("total: " + total());
 			}
 			n = n.next;
 			ind++;
 		}
 	}

	public String toString() {  
		StringBuffer sb = new StringBuffer();
      	Entry n = front;
     	while (n != null ) {
     		sb.append("\n$" + n.amount);
     		if (n.next != null) {
				sb.append(" ");
     		}	
        	n = n.next;
     	}
     	sb.append(" ");
    	return new String(sb);
	}	
}