package tp1.communes;

import org.apache.hadoop.hbase.util.Bytes;

public class Constantes {
	
	public static final byte[] TableName = Bytes.toBytes("Communes");
	
	// Groups rows
	public static final byte[] FamilyIdentity = Bytes.toBytes("identity");
	public static final byte[] FamilyPopulation = Bytes.toBytes("pop");
	
	
}
