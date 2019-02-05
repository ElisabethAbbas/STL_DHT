import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DHT_administrator {
	protected HashMap<Integer, String> uris;//for later, link node index to JVM uri
	protected int size;//maybe useless?
	protected int nbNodes;
	protected DHT_node[] ring;//choisir entre liste et tableau.
	//protected ArrayList<DHT_node> ring;//choisir entre liste et tableau.
	
	public DHT_administrator (int size, HashMap<Integer, String> nodes){//constructeur avec uris des JVM et index desires dans la DHT, pour l'instant noms et index des nodes
		this.size = size;//maybe useless?
		nbNodes=0;
		ring=new DHT_node[size];
		for(int i=0;i<size;i++){
			ring[i]=new DHT_node(-1);
		}
		uris = nodes;
		for (int ind : nodes.keySet()) {
			ring[ind]=new DHT_node(ind);
			nbNodes++;
		}
		//initialisation ses bons liens de pred et succ
		ArrayList<Integer> sortedIndex=new ArrayList<Integer>(nodes.keySet());
		Collections.sort(sortedIndex);
		int first=-1;
		int tmp=-1;
		for(int i=0;i<ring.length;i++){
			if(ring[i].getIndex()!=-1){
				if(first==-1){
					first=i;
					tmp=i;
				}
				else{
					ring[i].setPred(ring[(tmp)]);
					ring[tmp].setSucc(ring[i]);
					tmp=i;
				}
			}
		}
		if((first!=-1)&&(first!=tmp)){//on exclut le  cas où 0 ou 1 seule node
			ring[first].setPred(ring[tmp]);
			ring[tmp].setSucc(ring[first]);
		}
		
	}
	public DHT_node[] getRing(){
		return ring;
	}
	public void join(DHT_node n){
		int tmp=n.getIndex();
		ring[tmp]=n;
		nbNodes++;
		if(nbNodes!=1){
			tmp=(tmp+1)%size;
			while(ring[tmp].getIndex()==-1){
				tmp=(tmp+1)%size;
			}
			n.setSucc(ring[tmp]);
		}
	}
	public String toString(){//a faire pour les tests
		String s="listes des nodes : ";
		for(int i=0;i<size;i++){
			if(ring[i].getIndex()!=-1){
				s+=ring[i].toString()+", ";
			}
		}
		return s;
	}
}
