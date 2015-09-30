package eclipse_neo4j;

public class Main {
	
	public static final void main(String args[]) {
		Communes c = new Communes();
		c.createGraph();
		
		System.out.println("Liste des communes :");
		c.printCommunes();
		
		System.out.println("Liste des communes dans le 34 :");
		c.printCommmuneInDep(34);
		
		System.out.println("Plus grosse commune en 2010 :");
		c.printMostPop2010City();
	}
	
}
