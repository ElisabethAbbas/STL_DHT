public class FingerTable {
	private int size;
	DHT_node[] finger;
	private int next;
	
	public FingerTable(int m){
		size=m;
		finger = new DHT_node[m];
		next=0;
	}
	
	// returns the finger table 
	public DHT_node[] getFinger(){
		return finger;
	}
	
	// returns the number of nodes in the finger table 
	public int getSize() {
		return size;
	}	
	
	public void setNext(int n){
		next=n;
	}
	
	public int getNext(){
		return next;
	}
}
