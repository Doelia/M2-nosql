package eclipse_neo4j;

import java.io.IOException;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Communes {

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
	
	public void createCommune(String name) {
		Node commune = graphDb.createNode();
		commune.setProperty("name", name);
		commune.addLabel(DynamicLabel.label("Commune"));
	}
	
	public void createCommunes() {
		Transaction tx = graphDb.beginTx();
		createCommune("SAINTE-CROIX-DE-QUINTILLARGUES");
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
