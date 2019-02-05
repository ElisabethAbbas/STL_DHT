import java.util.HashMap;

public class Test {
	public static void main(String[] args) {
		HashMap<Integer,String> h=new HashMap<Integer,String>();
		h.put(1, "node1");
		h.put(5, "node2");
		h.put(9, "node3");
		h.put(10, "node4");
		h.put(8, "node5");
		DHT_administrator a=new DHT_administrator(15, h);
		DHT_node n=new DHT_node(3);
		a.join(n);
		System.out.println(a);
		n.stabilisation();
		System.out.println(a);
		a.getRing()[1].stabilisation();
		System.out.println(a);
	}
}