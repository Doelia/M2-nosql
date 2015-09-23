package tp1.hbaseTools;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class Show {

	byte[] tableName;
	
	public Show(String tableName) {
		this.tableName = Bytes.toBytes(tableName);
	}
	
	public ArrayList<String> getValuesForRow(String family, String row) throws IOException {
		
		ArrayList<String> list = new ArrayList<String>();
		
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, this.tableName);
		byte[] general = Bytes.toBytes(family);
		byte[] nom = Bytes.toBytes(row);

		Scan scan = new Scan();
		scan.addColumn(general, nom);
		ResultScanner scanner = table.getScanner(scan);
		for (Result r : scanner) {
			for (KeyValue keyValue : r.list()) {
				//String nameNow = Bytes.toString(keyValue.getQualifier());
				String value = Bytes.toString(keyValue.getValue());
				list.add(value);
			}
		}
		
		table.close();
		
		return list;
	}
}
