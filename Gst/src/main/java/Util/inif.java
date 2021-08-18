package Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import java.util.zip.CRC32;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.json.JSONException;

import Controller.MD5;

public class inif {
	//private static final String SAMPLE_INI_FILE = "config.ini";
	
	/*public static void main(String[] args) throws Exception {
	  /*	ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classloader.getResourceAsStream(SAMPLE_INI_FILE);
		Wini ini = new Wini(inputStream);
		
		
		String name = ini.get("Author", "name", String.class);
		String updateDate = ini.get("Author", "updateDate", String.class);
		System.out.println("name=" + name);
		System.out.println("updateDate=" + updateDate);
		String server = ini.get("database", "server", String.class);
		Integer port = ini.get("database", "port", Integer.class);
		String username = ini.get("database", "username", String.class);
		
		System.out.println("server= " + server);
		System.out.println("port = " + port);
		System.out.println("username = " + username); 
	}    000000  asdfasdf asdf sadf 
	*/ 
	
	public  static String ReadIni(String File,String Header01,String iniVar, String Defult) throws InvalidFileFormatException, IOException  {
		
		 Wini ini;
		 String ret = "";
		 try {
		 ini = new Wini(new File("c:/temp1/settings.ini"));
		
		 ret = ini.get(Header01, iniVar, String.class);
		 } catch (InvalidFileFormatException e) {
			 System.out.println("Invalid file format.");
			 ret = "Invalid file format.";
			 } catch (IOException e) {
			 System.out.println("Problem reading file.");
			 ret = "Problem reading file";
			 }
			 finally {
				 
			}
		
		 return ret;
		
	}
	
public  static String iniWrite(String File,String Header01,String iniVar,String Val01) throws InvalidFileFormatException, IOException  {
		
	  	
		
		 String ret = "";
		 try {
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				InputStream inputStream = classloader.getResourceAsStream(File);
				Wini ini = new Wini(inputStream);
		  ini.put(Header01, iniVar, Val01);
		 ini.store();
		 ret = "ok";
		 } catch (InvalidFileFormatException e) {
		 System.out.println("Invalid file format.");
		 ret = "Invalid file format.";
		 } catch (IOException e) {
		 System.out.println("Problem reading file.");
		 ret = "Problem reading file";
		 }
		 finally {
			
		}
		
		 return ret;
		
	}

public  static String Session_Put (String tokan, String Var_01,String Val_01) throws InvalidFileFormatException, IOException  {
		 String ret = "";
	 try {
		
		 File f=new File("C:\\temp1\\tokan\\"+tokan+".ini");
		
		 if (!f.exists()) {
			 ret = "Session Timeout or Invalid Tokan";
		 }else {
		 Wini ini = new Wini(f);
		 
		 long Timeout = ini.get(tokan, "Timeout", long.class);
		 //long currentTimeSecond = ini.get(tokan, "currentTimeSecond", long.class);
		
		 long timediff = System.currentTimeMillis()/1000-ini.get(tokan, "currentTimeSecond", long.class);
		 
		 System.out.println("time diff:"+timediff);
		 System.out.println("time out:"+Timeout);
		 
		
		 if (timediff>Timeout) {
			 ret = "Session Timeout";
			 if (f.delete()) { 
		    	 ret = "Session Timeout File Deleted";
		       } else {
		    	 ret = "Session Timeout Error in delete file";
		      } 
					 
		 }else {
			 
			 ini.put(tokan, Var_01,Val_01);
			 ini.put(tokan, "currentTimeSecond",System.currentTimeMillis()/1000);
			 ini.store();
			 ret = "ok";
			 System.out.println(0);
			 
		 }}
		 
	 } catch (InvalidFileFormatException e) {
	 System.out.println("Invalid file format.");
	 ret = "Invalid file format.";
	 } catch (IOException e) {
	 System.out.println("Problem reading file.");
	 ret = "Problem reading file";
	 }
	 finally {
	}
	 return ret;
	
}
public  static String Session_Get (String tokan, String Var_01) throws InvalidFileFormatException, IOException  {
	 String ret = "";
try {
	
	 File f=new File("C:\\temp1\\tokan\\"+tokan+".ini");
	
	 if (!f.exists()) {
		 ret = "Session Timeout or Invalid Tokan";
	 }else {
	 Wini ini = new Wini(f);
	 
	 long Timeout = ini.get(tokan, "Timeout", long.class);
	
	 long timediff = System.currentTimeMillis()/1000-ini.get(tokan, "currentTimeSecond", long.class);
	 
	 System.out.println("time diff:"+timediff);
	 System.out.println("time out:"+Timeout);
	 if (timediff>Timeout) {
		 ret = "Session Timeout";
		
		    if (f.delete()) { 
		    	 ret = "Session Timeout File Deleted";
		       } else {
		    	 ret = "Session Timeout Error in delete file";
		      } 
		 
				 
	 }else {
		    ret = ini.get(tokan, Var_01, String.class);
		    ini.put(tokan, "currentTimeSecond",System.currentTimeMillis()/1000);
		 	ini.store();

	 }}
	 
} catch (InvalidFileFormatException e) {
System.out.println("Invalid file format.");
ret = "Invalid file format.";
} catch (IOException e) {
System.out.println("Problem reading file.");
ret = "Problem reading file";
}
finally {
}
return ret;

}
	
public  static String Session_New (int Timeout) throws InvalidFileFormatException, IOException  {
  	
	String tokan = Timeout+"-"+ Crc32_(new Date().toString()+new Random().nextInt(99999999))+"-"+new Random().nextInt(100000) ;
	System.out.println(new Date().toString()+new Random().nextInt(99999999)+" md5 tokan:  " +tokan);
	 String ret = "";
	 try {
		
		 File f=new File("C:\\temp1\\tokan\\"+tokan+".ini");
		
		 if (!f.exists()) {
		   f.getParentFile().mkdirs();
		   f.createNewFile();
		 }
		 
		 Wini ini = new Wini(f);
		
		
	 ini.put(tokan, "CreateDateTime", new Date().toString());
	 ini.put(tokan, "Timeout", Timeout);
	 ini.put(tokan, "currentTimeSecond",System.currentTimeMillis()/1000);
	 
	 ini.store();
	 
	 ret = "ok";
	 } catch (InvalidFileFormatException e) {
	 System.out.println("Invalid file format.");
	 ret = "Invalid file format.";
	 } catch (IOException e) {
	 System.out.println("Problem reading file.");
	 ret = "Problem reading file";
	 }
	 finally {
		
	}
	
	 return tokan;
	
}
public static String Crc32_(String crcstring) {

	CRC32 crc = new CRC32();
	crc.update(crcstring.getBytes());
	return Long.toHexString(crc.getValue());

}
	
}
