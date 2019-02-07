import java.util.ArrayList;

public class DHT_node {
	int index;//Chord ring index,-1 si seulement un emplacement sur le crecle MAIS PAS une node
	protected DHT_node pred;
	protected DHT_node succ;
	protected FingerTable ft; 
	protected ArrayList<String> elts;//elements dans la node (JVM plus tard), eventuellement changer le type, uri, ou alors indexes mais dans ce cas ajouter une variable hashmap qui link les index aux uris des components

	// ou passer la FingerTable en paramètre, 
	// ou passer la size de la ring, au choix
	public DHT_node(int index, FingerTable ft){
		this.index = index;
		this.pred = null;
		this.succ = null;
		this.ft = ft;
		this.elts = new ArrayList<String>();
		//appel a une methode style clock qui appelle stabilisation periodiquement, decider comment implementer
	}

	public void setPred(DHT_node n){
		pred=n;
	}
	public void setSucc(DHT_node n){
		succ=n;
	}
	public void setIndex(int i){
		index=i;
	}
	public DHT_node getPred(){
		return pred;
	}
	public DHT_node getSucc(){
		return succ;
	}
	public int getIndex(){
		return index;
	}

	// ask node n to find the successor of id
	public DHT_node findSuccessor(int id) {
		if (getPred() != null && (id > pred.getIndex() 
				&& id < getIndex())) 
			return this;
		else if (id > index && index > succ.getIndex())
			return succ;
		else { // forward the query around the circle
			DHT_node m = closestPrecedingNode(id);
			return m.findSuccessor(id);
		}
	}

	// search locally for the highest predecessor of id
	private DHT_node closestPrecedingNode(int id) {
		for (int i = ft.getSize(); i > 0; i++)
			if (ft.getFinger()[i].getIndex() > index 
					&& ft.getFinger()[i].getIndex() < id) 
				return ft.getFinger()[i];
		return this;
	}

	public void stabilisation(){//called periodically --> stabilisation
		DHT_node v=succ.getPred();
		if((v!=this)&&((v.getIndex()>index)&&(v.getIndex()<succ.getIndex()))){//remplacer v!=this par this.equals(v)
			succ=v;
		}
		succ.notifyPred(this);
	}
	public void notifyPred(DHT_node n){
		if((pred==null)||((n.getIndex()<index)&&(n.getIndex()>pred.getIndex()))){
			pred=n;
		}
	}

	// when receiving notify(p) at n:
	public void fixFingers() {
		ft.setNext(ft.getNext()+1);
		if (ft.getNext() > ft.getSize()) 
			ft.setNext(1);
		ft.getFinger()[ft.getNext()] = findSuccessor(index^(1<<(ft.getNext()-1)));
	}

	public String toString(){
		/*System.out.println(index);
		System.out.println(pred.getIndex());
		System.out.println(succ.getIndex());*/
		String s="\n   -node "+index;
		if(pred!=null){
			s+=", pred : "+pred.getIndex();

		}
		if(succ!=null){
			s+=", succ : "+succ.getIndex();
		}
		return s;
	}
	//a faire : methode equals
}
