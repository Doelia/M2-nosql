package tp1.hbaseTools;

import java.io.IOException;
import java.util.List;

public class AdvancedGetter {

	public AdvancedGetter(String tableName) {
		super(tableName);
	}

	/**
	 * SELECT * FROM <table>;
	 */
	public List<Row> selectAllFrom(String table) {
		Getter g = new Getter(table);
		return g.getAlls();
	}
	
	/**
	 * SELECT <family>.<column> FROM <table>;
	 */
	public List<Row> selectColumnFrom(String table, String family, String column) {
	}
	
	/**
	 * SELECT * FROM <table> WHERE <family>.<column>=<value>
	 */
	public List<Row> selectAllFromWhere(String table, String family, String column, String value) {
		
	}
	
	/**
	 * SELECT <family1>.<column1> FROM <table> WHERE <family2>.<column2>=<value> LIMIT 1
	 */
	public  List<Row> selectColumnFromWhere(String table, String family1, String column1, String value,
			String family2, String column2) {
		
	}
	
	
}
