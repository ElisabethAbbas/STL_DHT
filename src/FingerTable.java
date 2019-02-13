public class FingerTable {
	private int size;
	private DHT_node n;
	DHT_node[] finger;
	private int next;
	
	public FingerTable(int m, DHT_node n){
		size=m;
		finger = new DHT_node[m];
		next=0;
		this.n=n;
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
	
	public static void initialize(DHT_administrator a, DHT_node n) {
		for(int j=0; j<n.ft.finger.length; j++)				
			for(int k=a.size-1; k>=0; k--) {
				if(a.getRing()[k].getIndex()!=-1) {
					if(k<(((n.index-1)+(1<<j))%a.size))
						break;
					n.ft.finger[j]=a.getRing()[k];
				}
			}
	}
	
	public String toString(){
		String s2="", s = "Finger table node "+n.index+" : "
				+ "[";
				for(int i=0; i<size; i++) {
					s2=finger[i]+((i<(size-1))?";\n                       ":"");
					s+=n.index+"+2^"+i+" -> "+s2.substring(5);
				}
				s+="]";
		return s;
	}
}
