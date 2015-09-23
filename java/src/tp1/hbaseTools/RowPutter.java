package tp1.hbaseTools;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class RowPutter {

	private String id;
	private Put put;
	private byte[] tableName;
	
	/**
	 * 
	 * @param id l'identifiant de la nouvelle affectation
	 */
	public RowPutter(byte[] tableName, String id) {
		this.id = id;
		this.tableName = tableName;
		this.put = new Put(Bytes.toBytes(this.id));
	}
	
	public void addValue(byte[] family, String row, String value) {
		this.put.add(family, Bytes.toBytes(row), Bytes.toBytes(value));
	}
	
	public void run() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTableFactory factory = new HTableFactory();
		HTableInterface pi = factory.createHTableInterface(conf, tableName);
		pi.put(this.put);
		pi.flushCommits();
		pi.close();
	}
	
}
