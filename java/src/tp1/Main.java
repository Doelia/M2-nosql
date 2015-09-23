package tp1;

import java.io.IOException;

import tp1.communes.Creator;
import tp1.hbaseTools.Getter;

public class Main {

	public static void main(String[] args) {
		//init();
		Getter s = new Getter("Communes");
		try {
			//System.out.println(s.getAlls());
			System.out.println(s.getRowsForColmnw("identity", "insee"));
			System.out.println(s.getKeys("identity", "name", "SAINTE-CROIX-DE-QUINTILLARGUE"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
