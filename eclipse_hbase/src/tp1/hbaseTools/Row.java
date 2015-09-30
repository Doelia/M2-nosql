package tp1.hbaseTools;

import java.util.ArrayList;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.util.Bytes;

public class Row {

	public String numRow;
	public String table = "Undefined";
	public String family;
	public String column;
	public String value;
	
	public Row(KeyValue keyValue) {
		this.numRow = Bytes.toString(keyValue.getRow());
		this.column = Bytes.toString(keyValue.getQualifier());
		this.family = Bytes.toString(keyValue.getFamily());
		this.value = Bytes.toString(keyValue.getValue());
	}
	
	@Override
	public String toString() {
		return "Row #"+numRow+" : Family="+this.family+", Column="+column+", Value="+value+"\n";
	}
	
	public static ArrayList<String> getOnlyValues(ArrayList<Row> rows) {
		ArrayList<String> list = new ArrayList<String>();
		for (Row r : rows) {
			list.add(r.value);
		}
		return list;
	}
	
	
}
