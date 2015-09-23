package tp1;

import java.io.IOException;
import java.util.HashMap;

import tp1.communes.Creator;
import tp1.hbaseTools.Getter;
import tp1.hbaseTools.Row;

public class Main {

	public static void main(String[] args) {
		//init();
		Getter s = new Getter("Communes");
		try {
			//System.out.println(s.getAlls());
			//System.out.println(s.getRowsForColmnw("identity", "insee"));
			
			//System.out.println(s.getWithFilters(
			//		Getter.createFilterColumnEquals("identity", "name", "SAINTE-CROIX-DE-QUINTILLARGUE")));
			
			//System.out.println(s.getWithFilters(
			//		Getter.createFilterUpperTo("pop", "pop_1975", 10)));
			
			count();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void count() throws IOException {
		Getter s = new Getter("Communes");
		HashMap<String, Integer> deps = new HashMap<String, Integer>();
		for (Row r : s.getRowsForColmnw("identity", "insee")) {
			String dep = r.value.substring(0, 2);
			if (deps.get(dep) == null) {
				deps.put(dep, 1);
			} else {
				deps.put(dep, deps.get(dep)+1);
			}
		}
		System.out.println(deps);
	}
	
	public static void init() {
		Creator c = new Creator();
		try {
			c.createTable();
			c.insertCommunes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
