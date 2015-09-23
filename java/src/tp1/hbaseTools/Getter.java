package tp1.hbaseTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class Getter {

	byte[] tableName;
	
	public Getter(String tableName) {
		this.tableName = Bytes.toBytes(tableName);
	}
	
	public static List<Filter> createFilterColumnEquals(String family, String column, String value) {
		List<Filter> list = new ArrayList<Filter>();
		list.add(new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(family))));
		list.add(new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(column))));
		list.add(new ValueFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(value))));
		return list;
	}
	
	public static List<Filter> createFilterUpperTo(String family, String column, double value) {
		List<Filter> list = new ArrayList<Filter>();
		list.add(new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(family))));
		list.add(new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(column))));
		list.add(new ValueFilter(CompareFilter.CompareOp.GREATER, new BinaryComparator(Bytes.toBytes(value))));
		return list;
	}
	
	public ArrayList<Row> getWithFilters(List<Filter> filters) throws IOException {
		ArrayList<Row> list = new ArrayList<Row>();
		
		Configuration conf = HBaseConfiguration.create();
		HTable hTable = new HTable(conf, tableName);
		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
		Scan scan = new Scan();
		scan.setFilter(fl);
		
		ResultScanner scanner = hTable.getScanner(scan);
		for (Result result : scanner) {
			for (KeyValue keyValue : result.raw()) {
				list.add(new Row(keyValue));
			}
		}
		
		hTable.close();
		return list;
	}
	
	
	public ArrayList<Row> getAlls() throws IOException {
		
		ArrayList<Row> list = new ArrayList<Row>();
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, this.tableName);
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		for (Result r : scanner) {
			for (KeyValue keyValue : r.list()) {
				list.add(new Row(keyValue));
			}
		}
		
		table.close();
		
		return list;
	}
	
	public ArrayList<Row> getRowsForColmnw(String family, String column) throws IOException {
		
		ArrayList<Row> list = new ArrayList<Row>();
		
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, this.tableName);
		byte[] general = Bytes.toBytes(family);
		byte[] nom = Bytes.toBytes(column);

		Scan scan = new Scan();
		scan.addColumn(general, nom);
		ResultScanner scanner = table.getScanner(scan);
		for (Result r : scanner) {
			for (KeyValue keyValue : r.list()) {
				list.add(new Row(keyValue));
			}
		}
		
		table.close();
		
		return list;
	}
	
	public ArrayList<String> getValuesForRow(String family, String column) throws IOException {
		return Row.getOnlyValues(this.getRowsForColmnw(family, column));
	}
}
