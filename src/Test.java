import java.util.HashMap;

public class Test {
	public static void main(String[] args) {
		HashMap<Integer,String> h=new HashMap<Integer,String>();
		h.put(1, "node1");
		h.put(5, "node2");
		h.put(9, "node3");
		h.put(10, "node4");
		h.put(8, "node5");
		DHT_administrator a=new DHT_administrator(16, h); // j'ai remplacé 15 par 16 pour que ce soit une puissance de 2
		
		DHT_node n=new DHT_node(3, 16); // correspond à log2(15), c'est le m de n=2^m dans le poly
		FingerTable.initialize(a, n);
		a.join(n);
		System.out.println(a);
		System.out.println(n.ft);
		n.stabilisation();
		System.out.println(a);
		a.getRing()[1].stabilisation();
		System.out.println(a);
		n.fixFingers();
		a.getRing()[1].fixFingers();
		//a.getRing()[2].fixFingers();
		System.out.println(n.ft);
	}
}