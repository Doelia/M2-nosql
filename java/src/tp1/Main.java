package tp1;

import java.io.IOException;

import tp1.communes.Creator;
import tp1.hbaseTools.Show;

public class Main {

	public static void main(String[] args) {
		Show s = new Show("Communes");
		try {
			System.out.println(s.getValuesForRow("identity", "name"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		Creator c = new Creator();
		try {
			c.createTable();
			c.insertCommunes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}