package eclipse_neo4j;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.io.fs.FileUtils;

public class Communes {
	
	public enum RelTypes implements RelationshipType {
		COM_IS_IN_DEP
	}

	// Fichier de sauvegarde des données
	private final String COMMUNE_DB = "target/communes-db";
	
	// Pointeur vers le grapsh créé
	private GraphDatabaseService graphDb;
	
	public Communes() {
		try {
			this.setUp();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Créé le graph dans le fichier associé
	 */
	public void setUp() throws IOException {
		FileUtils.deleteRecursively(new File(COMMUNE_DB));
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(COMMUNE_DB);
	}
	
	public void createGraph() {
		Transaction tx = graphDb.beginTx();
		
		// Création des communes
		Node c_34248 = createCommune("34248", "SAINTE-CROIX-DE-QUINTILLARGUE", 127.033553, 561.720409);
		Node c_34249 = createCommune("34249", "SAINT-DREZERY", 587.63955, 2207.40611);
		Node c_34025 = createCommune("34025", "BASSAN", 795.304324, 1599.4016);
		Node c_34250 = createCommune("34250", "SAINT-ETIENNE-D'ALBAGNAN", 253.31857, 287.52352);
		Node c_34251 = createCommune("34251", "SAINT-ETIENNE-DE-GOURGAS", 216.105575, 489.314637);
		Node c_34252 = createCommune("34252", "SAINT-ETIENNE-ESTRECHOUX", 406.570257, 245.980104);
		Node c_30123 = createCommune("30123", "GALLARGUES", 1406.570257, 2245.980104);
		
		// Création des départements
		Node herault = createDepartement(34, "Herault");
		Node gard = createDepartement(30, "Gard");
		
		// Associations communes -> departements
		c_34248.createRelationshipTo(herault, RelTypes.COM_IS_IN_DEP);
		c_34249.createRelationshipTo(herault, RelTypes.COM_IS_IN_DEP);
		c_34025.createRelationshipTo(herault, RelTypes.COM_IS_IN_DEP);
		c_34250.createRelationshipTo(herault, RelTypes.COM_IS_IN_DEP);
		c_34251.createRelationshipTo(herault, RelTypes.COM_IS_IN_DEP);
		c_34252.createRelationshipTo(herault, RelTypes.COM_IS_IN_DEP);
		c_30123.createRelationshipTo(gard, RelTypes.COM_IS_IN_DEP);
		
		tx.success();
	}
	
	/**
	 * Crée un node commune dans le graph
	 */
	private Node createCommune(String insee, String name, double pop_1975, double pop_2010) {
		Node commune = graphDb.createNode();
		commune.addLabel(DynamicLabel.label("Commune"));
		commune.setProperty("name", name);
		commune.setProperty("insee", insee);
		commune.setProperty("pop_1975", pop_1975);
		commune.setProperty("pop_2010", pop_2010);
		return commune;
	}
	
	/**
	 * Crée un node Departement dans le graph
	 */
	private Node createDepartement(int numDep, String name) {
		Node node = graphDb.createNode();
		node.addLabel(DynamicLabel.label("Departement"));
		node.setProperty("numDep", numDep);
		node.setProperty("name", name);
		return node;
	}
	
	private String nodeCommuneToString(Node commune) {
		return "Commune # "+commune.getProperty("insee")+" "+commune.getProperty("name");
	}
	
	/**
	 * Affiche la liste les communes
	 */
	public void printCommunes() {
		Transaction tx = graphDb.beginTx();
		ResourceIterator<Node> communes = graphDb.findNodes(DynamicLabel.label("Commune"));
		while (communes.hasNext()) {
			Node c = communes.next();
			System.out.println(this.nodeCommuneToString(c));
		}
		tx.close();
	}
	
	/**
	 * Affiche les communes qui appartiennent à un département
	 * @param dep
	 */
	public void printCommmuneInDep(int dep) {
		Transaction tx = graphDb.beginTx();
		Node nodeDep = this.getDepartement(dep);
		
		TraversalDescription td = graphDb.traversalDescription().breadthFirst()
				.relationships(RelTypes.COM_IS_IN_DEP, Direction.INCOMING).evaluator(Evaluators.excludeStartPosition());
		
		Traverser communes = td.traverse(nodeDep);
		
		for (Path comPath : communes) {
			Node commune = comPath.endNode();
			System.out.println(this.nodeCommuneToString(commune));
		}
		
		tx.close();
	}
	
	/**
	 * Retoune le noeud d'un département à partir de son numéro
	 */
	private Node getDepartement(int dep) {
		Transaction tx = graphDb.beginTx();
		ResourceIterator<Node> communes = graphDb.findNodes(DynamicLabel.label("Departement"));
		while (communes.hasNext()) {
			Node c = communes.next();
			if ((Integer) c.getProperty("numDep") == dep) {
				tx.close();
				return c;
			}
		}
		tx.close();
		return null;
	}
	
	public void printMostPop2010City() {
		Transaction tx = graphDb.beginTx();
		ResourceIterator<Node> communes = graphDb.findNodes(DynamicLabel.label("Commune"));
		Node best = null;
		while (communes.hasNext()) {
			Node c = communes.next();
			if (best == null || ((Double) best.getProperty("pop_2010") < (Double) c.getProperty("pop_2010"))) {
				best = c;
			}
		}
		System.out.println(this.nodeCommuneToString(best));
		tx.close();
	}
}
