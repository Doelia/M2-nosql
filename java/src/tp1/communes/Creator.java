package tp1.communes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import tp1.hbaseTools.Putter;


public class Creator {
	
	private class Insertor {
		public String insee;
		public Creator cr;
		public Insertor addPopOnCommune(int year, double pop) throws IOException {
			return cr.addPopOnCommune(insee, year, pop);
		}
	}

	public void createTable() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor table = new HTableDescriptor(Constantes.TableName);
		table.addFamily(new HColumnDescriptor(Constantes.FamilyIdentity));
		table.addFamily(new HColumnDescriptor(Constantes.FamilyPopulation));
		if (!admin.tableExists(Constantes.TableName)) {
			admin.createTable(table);
		}
		admin.close();
	}
	
	public void insertCommunes() {
		try {
			createCommune("34248", "SAINTE-CROIX-DE-QUINTILLARGUE")
				.addPopOnCommune(1975, 127.033553)
				.addPopOnCommune(2010, 561.720409);
			
			createCommune("34249", "SAINT-DREZERY")
				.addPopOnCommune(1975, 587.63955)
				.addPopOnCommune(2010, 2207.40611);
			
			createCommune("34025", "BASSAN")
				.addPopOnCommune(1975, 795.304324)
				.addPopOnCommune(2010, 1599.4016);
			
			createCommune("34250", "SAINT-ETIENNE-D'ALBAGNAN")
			.addPopOnCommune(1975, 253.31857)
			.addPopOnCommune(2010, 287.52352);
			
			createCommune("34251", "SAINT-ETIENNE-DE-GOURGAS")
				.addPopOnCommune(1975, 216.105575)
				.addPopOnCommune(2010, 489.314637);
			
			createCommune("34252", "SAINT-ETIENNE-ESTRECHOUX")
			.addPopOnCommune(1975, 406.570257)
			.addPopOnCommune(2010, 245.980104);
			
		} catch (IOException e) {
		}
	}
	
	private Insertor createCommune(String insee, String name) throws IOException {
		Putter commune = new Putter(Constantes.TableName, insee);
		commune.addValue(Constantes.FamilyIdentity, "name", name);
		commune.addValue(Constantes.FamilyIdentity, "insee", insee);
		commune.run();
		
		Insertor i = new Insertor();
		i.insee = insee;
		i.cr = this;
		return i;
	}
	
	private Insertor addPopOnCommune(String insee, int year, double pop) throws IOException {
		Putter commune = new Putter(Constantes.TableName, insee);
		commune.addValue(Constantes.FamilyPopulation, "pop_"+year, ""+pop);
		commune.run();
		
		Insertor i = new Insertor();
		i.insee = insee;
		i.cr = this;
		return i;
	}
	
}
