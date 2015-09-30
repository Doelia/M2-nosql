package eclipse_neo4j;

import java.io.IOException;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Communes {
	
	public enum RelTypes implements RelationshipType {
		COM_IS_IN_DEP
	}

	private final String COMMUNE_DB = "target/communes-db";
	
	private GraphDatabaseService graphDb;
	
	public Communes() {
		try {
			this.setUp();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUp() throws IOException {
		//FileUtils.deleteRecursively(new File(COMMUNE_DB));
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(COMMUNE_DB);
	}
	
	public Node createCommune(String insee, String name, double pop_1975, double pop_2010) {
		Node commune = graphDb.createNode();
		commune.addLabel(DynamicLabel.label("Commune"));
		commune.setProperty("name", name);
		commune.setProperty("insee", insee);
		commune.setProperty("pop_1975", pop_1975);
		commune.setProperty("pop_2010", pop_2010);
		return commune;
	}
	
	public Node createDepartement(int numDep, String name) {
		Node node = graphDb.createNode();
		node.addLabel(DynamicLabel.label("Departement"));
		node.setProperty("numDep", numDep);
		node.setProperty("name", name);
		return node;
	}
	
	public void createCommunes() {
		Transaction tx = graphDb.beginTx();
		Node steCroix = createCommune("34248", "SAINTE-CROIX-DE-QUINTILLARGUE", 127.033553, 561.720409);
		createCommune("34249", "SAINT-DREZERY", 587.63955, 2207.40611);
		createCommune("34025", "BASSAN", 795.304324, 1599.4016);
		createCommune("34250", "SAINT-ETIENNE-D'ALBAGNAN", 253.31857, 287.52352);
		createCommune("34251", "SAINT-ETIENNE-DE-GOURGAS", 216.105575, 489.314637);
		createCommune("34252", "SAINT-ETIENNE-ESTRECHOUX", 406.570257, 245.980104);
		
		Node herault = createDepartement(34, "Herault");
		
		steCroix.createRelationshipTo(herault, RelTypes.COM_IS_IN_DEP);
		
		tx.success();
	}
	
	public void printCommunes() {
		Transaction tx = graphDb.beginTx();
		ResourceIterator<Node> communes = graphDb.findNodes(DynamicLabel.label("Commune"));
		while (communes.hasNext()) {
			Node c = communes.next();
			System.out.println("\t" + c.getProperty("name"));
		}
		tx.close();
	}
}
