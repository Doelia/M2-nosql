package tp1.exemple;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class Personne {

	private static final byte[] Personne = Bytes.toBytes("Personne");
	private static final byte[] identite = Bytes.toBytes("identite");
	private static final byte[] loisirs = Bytes.toBytes("loisirs");

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);

		HTableDescriptor pe = new HTableDescriptor(Personne);
		pe.addFamily(new HColumnDescriptor(identite));
		pe.addFamily(new HColumnDescriptor(loisirs));
		Put arthur = new Put(Bytes.toBytes("1902605382213"));
		arthur.add(Bytes.toBytes("identite"), Bytes.toBytes("numSS"), Bytes.toBytes("1902605382213"));
		arthur.add(Bytes.toBytes("identite"), Bytes.toBytes("prenom"), Bytes.toBytes("Montpellier"));
		arthur.add(Bytes.toBytes("identite"), Bytes.toBytes("nom"), Bytes.toBytes("Sorel"));
		arthur.add(Bytes.toBytes("identite"), Bytes.toBytes("age"), Bytes.toBytes("25"));
		arthur.add(Bytes.toBytes("loisirs"), Bytes.toBytes("sport"), Bytes.toBytes("hockey"));

		System.out.println("Table " + Personne + " exist: " + admin.tableExists(Personne));
		HTableFactory factory = new HTableFactory();

		if (!admin.tableExists(Personne)) {
			admin.createTable(pe);
		}

		HTableInterface pi = factory.createHTableInterface(conf, Personne);
		pi.put(arthur);
		System.out.println("creation et insertion achevees");
		pi.flushCommits();
		pi.close();
		admin.close();

	}
}