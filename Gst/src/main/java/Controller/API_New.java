package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.zip.CRC32;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Util.ConnectionUtil;

/**
 * Servlet implementation class API_New
 */
@WebServlet("/API_New")
public class API_New extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public API_New() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String Api = "0", Otp = "0", key = "0", mobile = "0", tal_cd = "0", gram_cd = "0", akarnidata = "0", yr = "0",
				msg = "", db_name = "0", cheksum = "0", x = "0";
		String ootp = "0";
		String lastdt = "0";
		String appid = "0";
		String StrSql01 = "";
		String Responce1 = "";
		Connection con = ConnectionUtil.getConnection_reg();
		PrintWriter out = response.getWriter();

		if (request.getParameter("key") != null) {
			key = request.getParameter("key");
		}
		if (request.getParameter("otp") != null) {
			Otp = request.getParameter("otp");
		}
		if (request.getParameter("mobile") != null) {
			mobile = request.getParameter("mobile");
		}
		if (request.getParameter("tal_cd") != null) {
			tal_cd = request.getParameter("tal_cd");
		}
		if (request.getParameter("gram_cd") != null) {
			gram_cd = request.getParameter("gram_cd");
		}
		if (request.getParameter("akarnidata") != null) {
			akarnidata = request.getParameter("akarnidata");
		}
		if (request.getParameter("yr") != null) {
			yr = request.getParameter("yr");
		}
		if (request.getParameter("db_name") != null) {
			db_name = request.getParameter("db_name");
		}

		if (request.getParameter("api") != null) {
			Api = request.getParameter("api");
			
		}
		
		if (request.getParameter("lastdt") != null) {
			lastdt = request.getParameter("lastdt");
		}
		if (request.getParameter("appid") != null) {
			appid = request.getParameter("appid");
		}

		String Randomnum = getRandomNumberString();
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		String uuid="0";
		
		System.out.println("APi - " + Api);
		System.out.println("mobile - " + mobile);
		
		
		// oNLY mOBILE nO oTP-------------------------
		try {

			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}

			
			uuid = Get_Data(con, "SELECT reg.fun_uuid('uu_id_log')");

			
			
			int aa = SqlExc(con,
					"INSERT INTO logfile.api_log (id,ip, Typ) VALUES (" + uuid + ",'" + ip + "','e-gam APP' )");

			
			
			//System.out.println("0001" + uuid);

			// int aa1= SqlExc(con,"INSERT INTO logfile.api_log (id,Api_String,ip, Typ)
			// VALUES
			// ("+uuid+",'"+Api+","+mobile+","+Otp+","+yr+","+tal_cd+","+gram_cd+","+db_name+","+akarnidata+","+lastdt+","+appid+"','"+ip+"','e-gam
			// APP' )" );
			// int aa1= SqlExc(con,"update logfile.api_log set
			// Api_String='"+Api+","+mobile+","+Otp+","+yr+","+tal_cd+","+gram_cd+","+db_name+","+akarnidata+","+lastdt+","+appid+"'
			// where id= "+uuid+")");

			
			System.out.println("0001 " + mobile);
			if (Api.equals("11") || Api.equals("22") || Api.equals("33") || Api.equals("44") || Api.equals("55")) {
				//out.write(" api " + api + " " + getRandomNumberString() + "  ");
			}
			System.out.println("0002 " + Api);
			api01: {
				
			
				if (Api.equals("1") || Api.equals("11")) // 1 Only Mobile No =============================
				{
  
					if (!mobile.equals("")) {
						
							
						int aa01 = SqlExc(con, "update logfile.api_log set Api_String='" + Api + "|" + mobile
								+ "|' where id= " + uuid);

						PreparedStatement ps = con.prepareStatement(
								"SELECT mob_no,Tal_Cd,active,otp FROM app_user WHERE mob_no = '" + mobile + "'");
						ResultSet rs = ps.executeQuery();

						
						
						if (rs.next()) {
							
							Otp=rs.getString("otp");
							//out.write("Otp"+Otp+"Otp");
							if (!rs.getString("active").toUpperCase().equals("Y")) {
								obj.put("status", "error");
								obj.put("code", 401);
								obj.put("message", "inactinve user");
								//obj.put("data", "N");
								obj.put("otp", "");
								Responce1 = obj.toString();
								out.write(obj.toString());
								obj = null;

								break api01;
							}
							
							System.out.println("SELECT IFNULL(NOW()-otpdt,500)  FROM app_user WHERE mob_no ='"+mobile+"' UNION SELECT 500 LIMIT 1");

							long otp11 =  Long.parseLong(Get_Data(con,"SELECT IFNULL(NOW()-otpdt,500)  FROM app_user WHERE mob_no ='"+mobile+"' UNION SELECT 500 LIMIT 1"));
							
							System.out.println("0006 " + mobile);
							
							if  (otp11 > 500)
							{
								Otp = Randomnum;
								//out.write(" NEw Otp ");
							}
							//out.write("x"+Otp+"x");
							
							
							//String strSMS = "Your OTP is " + Otp + " For E-gam APP, Krishna Software ";
							
							String strSMS = "OTP FOR User REG IS "+Otp+" FROM KRISHNA COMPUTER";
							
							
							strSMS = URLEncoder.encode(strSMS, "UTF-8");
							
							//String Main1 = "http://mobizz.hginfosys.co.in/sendsms.jsp?user=DZling12&password=e06c72d4a2XX&mobiles="+mobile+ "&sms=" +strSMS+ "&senderid=Krisna&unicode=1";
							

							String Main1 ="http://sms.krishnasoftware.com/sendsms.jsp?user=Kim001&password=9827177854XX&senderid=KlSHNA&mobiles=+91"+mobile+"&tempid=1207162288031458872&sms="+strSMS;


							URL url = new URL(Main1);
							HttpURLConnection uc = (HttpURLConnection) url.openConnection();

							BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

							// uc.disconnect();

							String Rslt1 = "";
							String inputLine;
							while ((inputLine = in.readLine()) != null) {
								Rslt1 = Rslt1 + inputLine.toString();
							}
							// System.out.println("0001"+Rslt1);
							in.close();
							if (Rslt1.indexOf("<status>success</status>") > 0) {

								// String to="ketan@krishnasoftware.com";
								// String subject="Sms Send";
								// String masg=Rslt1+"\n"+strSMS;
								// Mailer.send(to, subject, masg);
							} else {

								String to = "ketan@krishnasoftware.com";
								String subject = "api Problem";
								String masg = Rslt1 + "\n" + strSMS;
								Mailer.send(to, subject, masg);

								Otp = mobile.substring(0, 2) + mobile.substring(mobile.length() - 2, mobile.length());

								PreparedStatement ps1 = con.prepareStatement(
										"update app_user set otp =" + Otp + ", otpdt = now() WHERE `mob_no` = '" + mobile + "'");
								int a = ps1.executeUpdate();
								ps1.close();

								obj.put("status", "error");
								obj.put("code", 401);
								obj.put("message", "SMS api Problem - "+Rslt1);
								//obj.put("data","N" );
								obj.put("otp", "");
								Responce1 = obj.toString();
								out.write(obj.toString());
								break api01;
							}

							PreparedStatement ps11 = con.prepareStatement(
									"update app_user set otp =" + Otp + ", otpdt = now() WHERE `mob_no` = '" + mobile + "'");
							int a23 = ps11.executeUpdate();
							ps11.close();

							obj.put("status", "success");
							obj.put("code", 200);
							obj.put("message", "");
							//obj.put("data","Y");
							obj.put("tal_cd", rs.getString("tal_cd"));

							if (Api.equals("11")) {
								obj.put("otp", Otp);
							} // remove latter
							Responce1 = obj.toString();
							out.write(obj.toString());

							obj = null;

						} else {
							obj.put("status", "error");
							obj.put("code", 401);
							obj.put("message", "Mob no Not Register...");
							//obj.put("data", "N");
							obj.put("otp", "");
							Responce1 = obj.toString();
							out.write(obj.toString());
							obj = null;
						}
					}
				} // if api 1
			} // 1 Only Mobile No =============================

			// -------start Mobile
			// +otp----------------------------------------------------------------------------------------------
			if (Api.equals("2") || Api.equals("22")) // 2 Only Mobile+Otp =============================
			{

				if (!mobile.equals("0") && !Otp.equals("0") && !tal_cd.equals("0")) {
					int aa02 = SqlExc(con, "update logfile.api_log set api_String='" + Api + "|" + mobile + "|" + Otp
							+ "|" + tal_cd + "' where id= " + uuid);
					x = mobile + Otp + tal_cd;

					if (checkdigit(key, x) == true) { // Chesum Faild

						/*
						 * PreparedStatement
						 * ps9=con.prepareStatement("SELECT otp FROM reg.app_user WHERE mob_no = '"
						 * +mobile+"'"); ResultSet rs9=ps9.executeQuery(); rs9.next(); ootp =
						 * rs9.getString("otp"); rs9.close(); ps9.close();
						 */

						ootp = Get_Data(con, "SELECT otp FROM reg.app_user WHERE mob_no = '" + mobile + "'");

						if (!Otp.equals(ootp)) {
							obj.put("status", "error");
							obj.put("code", 404);
							obj.put("message", "OTP incorrect");
							Responce1 = obj.toString();
							out.write(obj.toString());
						} else {
							PreparedStatement ps1 = con
									.prepareStatement("Call SP_App('" + mobile + "'," + tal_cd + ")");
							// System.out.println(ps1);
							ResultSet rs1 = ps1.executeQuery();
							while (rs1.next()) {
								JSONObject obj1 = new JSONObject();
								obj1.put("Gram_Name", rs1.getString("Gram_Name"));
								obj1.put("Tal_Name", rs1.getString("Tal_Name"));
								obj1.put("Gram_Cd", rs1.getString("Gram_Cd"));
								obj1.put("Tal_Cd", rs1.getString("Tal_Cd"));
								obj1.put("Host", rs1.getString("Host"));
								obj1.put("Db_Name", rs1.getString("Db_Name"));
								obj1.put("Yr", rs1.getString("Yr"));
								obj1.put("UserNM", rs1.getString("usernm"));
								obj1.put("Dist", rs1.getString("dist"));
								obj1.put("Year", rs1.getString("Yyr"));
								arr.put(obj1);
							}

							obj.put("status", "success");
							obj.put("code", 200);
							obj.put("message", new JSONArray());

							obj.put("Table", "Village_Master");
							Responce1 = obj.toString();
							obj.put("Data", arr);
							out.write(obj.toString());

							obj = null;
							arr = null;
							rs1.close();
							ps1.close();

						}
					} else {
						obj.put("status", "error..");
						obj.put("code", 404);
						obj.put("message", "Checksum Faield");

						if (Api.equals("22")) {
							obj.put("data", Crc32_(x)); // remove latter
							obj.put("string", x); // remove latter
						} else {
							obj.put("data", ""); // remove latter
						}
						// System.out.println(x);
						Responce1 = obj.toString();
						out.write(obj.toString());
						obj = null;
					} // Chesum Faild
				} else // Not Complate peramiter
				{
					obj.put("status", "error..");
					obj.put("code", 404);
					obj.put("message", "Requied Peramiter Missing");
					if (Api.equals("22")) {
						obj.put("data", "mobile,otp,tal_cd");
					}
					// System.out.println(x);
					Responce1 = obj.toString();
					out.write(obj.toString());
					obj = null;
				}
			} // -----End mobile + OTP------------------------------------------------
				// -----Strart Vera------------------------------------------------

			if (Api.equals("3") || Api.equals("33")) {
				if (!gram_cd.equals("0") && !db_name.equals("0") && akarnidata.equals("veraname") && !key.equals("0")) {
					int aa03 = SqlExc(con, "update logfile.api_log set Api_String='" + Api + "|" + gram_cd + "|"
							+ db_name + "|" + tal_cd + "|" + akarnidata + "|" + key + "' where id= " + uuid);
					x = gram_cd + db_name + akarnidata;
					// out.write(x);
					if (checkdigit(key, x) == true) {
						PreparedStatement ps2 = con.prepareStatement(
								"SELECT  mst_taxsetting.tax_cd as tex_no,tax_nmg\r\n" + "				FROM " + db_name
										+ ".mst_taxname," + db_name + ".mst_taxsetting \r\n"
										+ "				WHERE mst_taxsetting.tcode=mst_taxname.Tax_cd AND mst_taxsetting.gram_cd="
										+ gram_cd + " ORDER BY mst_taxsetting.tax_cd");

						ResultSet rs1 = ps2.executeQuery();

						// System.out.println(ps2);
						if (!rs1.isBeforeFirst()) {
							obj.put("status", "error");
							obj.put("code", 204);
							obj.put("message", "Record Not Found");
							obj.put("Table", "Village_Master");
							Responce1 = obj.toString();
							out.write(obj.toString());
							obj = null;
							rs1.close();
							ps2.close();
						} else {
							while (rs1.next()) {
								JSONObject obj1 = new JSONObject();
								obj1.put("Vero" + rs1.getString("tex_no"), rs1.getString("tax_nmg"));
								arr.put(obj1);

							}
							obj.put("status", "success");
							obj.put("code", 200);
							obj.put("message", "Update veraname");
							obj.put("Table", "Village_Master");
							Responce1 = obj.toString();
							obj.put("Data", arr);

							out.write(obj.toString());
							obj = null;

							rs1.close();
							ps2.close();
						}
					} else {
						obj.put("status", "error");
						obj.put("code", 404);
						obj.put("message", "Checksum Faield");

						if (Api.equals("33")) {
							obj.put("data", Crc32_(x)); // remove latter
						}
						Responce1 = obj.toString();
						out.write(obj.toString());
						obj = null;
					}

				} else

				{
					obj.put("status", "error");
					obj.put("code", 404);

					// obj.put("data", ""); //remove latter
					obj.put("message", "api=3&gram_cd=00&db_name=xxxx&akarnidata=veraname&key=123654");
					if (Api.equals("33")) {
						obj.put("message", "api=3&gram_cd=00&db_name=xxxx&akarnidata=veraname&key=123654");
						obj.put("data", Crc32_(x)); // remove latter
						obj.put("string", x); // remove latter
					}
					Responce1 = obj.toString();
					out.write(obj.toString());
					obj = null;
				}
			} // ====end Vera ==============================

			// -----Strart Akarni Data------------------------------------------------

			if (Api.equals("4") || Api.equals("44")) {
				if (!gram_cd.equals("0") && !db_name.equals("0") && akarnidata.equals("data") && !lastdt.equals("")) {
					x = gram_cd + db_name + akarnidata + lastdt;

					// if (day(now()) )
					// lastdt="2001-01-01 00:00:01";

					if (checkdigit(key, x) == true) {
						int aa04 = SqlExc(con, "update logfile.api_log set Api_String='" + Api + "|" + gram_cd + "|"
								+ db_name + "|" + lastdt + "|" + akarnidata + "|" + key + "' where id= " + uuid);

						String StrSql = " CALL reg.Sp_APP_export_Data('" + db_name + "'," + gram_cd + ",'" + lastdt
								+ "')";

						PreparedStatement ps4 = con.prepareStatement(StrSql);

					//	System.out.println(ps4);

						ResultSet rs4 = ps4.executeQuery();
						ResultSetMetaData metaData = rs4.getMetaData();

						int rr = 0;

						if (!rs4.isBeforeFirst()) {
							obj.put("status", "Error");
							obj.put("code", 204);
							obj.put("message", "Record Not Found");
							obj.put("Table", "AkarniData");
							Responce1 = obj.toString();
							out.write(obj.toString());
						} else {
							obj.put("status", "success");
							obj.put("code", 200);
							obj.put("message", "Get Akarni Data..");
							obj.put("Table", "AkarniData");
							while (rs4.next()) {
								JSONObject obj1 = new JSONObject();
								for (int i = 1; i <= metaData.getColumnCount(); i++) {
									
									if (rs4.getString(i) == null) {
										obj1.put(metaData.getColumnLabel(i), "");
									} else {
										if (metaData.getColumnLabel(i).equals("Lastdt"))
										{
											obj1.put(metaData.getColumnLabel(i), rs4.getString(i).substring(0,19));
										}else {
											
										obj1.put(metaData.getColumnLabel(i), rs4.getString(i));
										}
									}
									
																	}
								obj1.put("db_name", db_name);
								arr.put(obj1);
								rr += 1;
							}

							ps4.getMoreResults();
							ResultSet rsSec = ps4.getResultSet();
							if (rsSec.next()) {
								obj.put("RowsCount", rsSec.getInt(1));
							}

							if (rsSec.getInt(1) == rr) {
								StrSql01 = "OK Akarni Data " + rsSec.getInt(1) + "-" + rr + " " + gram_cd + "-"
										+ db_name;
							} else {
								StrSql01 = "Diff Akarni Data " + rsSec.getInt(1) + "-" + rr + " " + gram_cd + "-"
										+ db_name;
							//	System.out.println(StrSql01);
							}
							String uuid1 = Get_Data(con, "SELECT reg.fun_uuid('uu_id_log')");
							int a1b = SqlExc(con, "INSERT INTO logfile.api_log (id,Api_String,ip, Typ) VALUES (" + uuid1
									+ ",'" + StrSql01 + "','" + ip + "','e-gam APP' )");

							obj.put("Rows", rr);

							Responce1 = obj.toString();
							obj.put("Data", arr);
							out.write(obj.toString());

							obj = null;
							arr = null;
							ps4.close();
							rs4.close();
						}

					} else {
						obj.put("status", "error");
						obj.put("code", 404);
						obj.put("message", "Checksum Faield");

						// obj.put("data", "");
						if (Api.equals("44")) {
							obj.put("data", Crc32_(x));
							obj.put("String", x);
						}
						Responce1 = obj.toString();
						out.write(obj.toString());
					}

				} else {
					obj.put("status", "error");
					obj.put("code", 404);
					obj.put("message", "Required Perameter Missing");
					obj.put("data", "gram_cd,db_name,akarnidata,lastdt,key");
					Responce1 = obj.toString();
					out.write(obj.toString());
				}
				// -----end Akarni Data------------------------------------------------

			} // api 4

			api5: {

				if (Api.equals("5") || Api.equals("55")) // Version check strat
				{
					//int aa03 = SqlExc(con, "update logfile.api_log set Api_String='" + Api + "' where id= " + uuid);
				//	if (appid.equals("0")) {
				//		obj.put("status", "error");
				//		obj.put("code", 404);
				//		obj.put("message", "reqiure Perameter messing");
				//		obj.put("data", "[]");
				//		Responce1 = obj.toString();
				//		out.write(obj.toString());
				//		obj = null;
				//		break api5;
				//	}

					PreparedStatement ps01 = con.prepareStatement(
							"SELECT appversion,ifnull(link,'0') as Link  ,upd_typ FROM reg.app_ver where app_name = 'e-gam' order by appversion desc limit 1  ");
					ResultSet rs01 = ps01.executeQuery();

					if (rs01.next()) {

						if (rs01.getInt("appversion") <= (Integer.parseInt(appid))) {
							obj.put("status", "success");
							obj.put("code", 203);
							obj.put("message", "No Update");
							Responce1 = obj.toString();
							out.write(obj.toString());
							obj = null;
						} else {
							obj.put("status", "success");
							obj.put("code", 200);
							obj.put("message", "New Update");
							obj.put("data", rs01.getInt("appversion"));
							obj.put("Link", rs01.getString("link"));
							obj.put("updatetype", rs01.getString("upd_typ"));
							Responce1 = obj.toString();
							out.write(obj.toString());
						}

						ps01.close();
						rs01.close();
					} else {
						obj.put("status", "error");
						obj.put("code", 404);
						obj.put("message", "Sql Error");
						obj.put("data", "[]");
						Responce1 = obj.toString();
						out.write(obj.toString());
						obj = null;
						ps01.close();
						rs01.close();
					}

				} // Version check over

			} // api5

			if (Api.equals("6")) {
				if (!mobile.equals("")) {
					int aa01 = SqlExc(con,
							"update logfile.api_log set Api_String='" + Api + "|" + mobile + "|' where id= " + uuid);

					String Yn = Get_Data(con, "SELECT active FROM app_user WHERE mob_no = '" + mobile + "'");
					//out.write(Yn.toUpperCase());
					if (Yn.toUpperCase().equals("Y")) {
						obj.put("status", "success");
						obj.put("code", 200);
						obj.put("message", "Active");
						obj.put("isactive", "Y");
						Responce1 = obj.toString();
						out.write(obj.toString());
						obj = null;
					} else {
						obj.put("status", "error");
						obj.put("code", 404);
						obj.put("message", "inactive user");
						obj.put("isactive", "N");
						Responce1 = obj.toString();
						out.write(obj.toString());
						obj = null;
					}
				}
			} // if api06

			if (Responce1.length() > 500) {
				Responce1 = Responce1.substring(1, 500);
			}

			int aa14 = SqlExc(con, "update logfile.api_log set responce='" + Responce1 + "' where id= " + uuid);
		} catch (Exception e) {
			
			e.printStackTrace();
			try {

				obj.put("status", "error");
				obj.put("code", 204);
				obj.put("message", e.getMessage());
				obj.put("Data", "SQLError");
				Responce1 = obj.toString();
				
				out.write(obj.toString());
				obj = null;

				

			} catch (JSONException e1) {
				e1.printStackTrace();
			} 
		}

		/*
		 * if (Api.equals("5")) {
		 * 
		 * try { ResultSet aaa=
		 * Get_Data_all(con,"SELECT mob_no,Tal_Cd FROM reg.app_user"); aaa.next();
		 * out.write(aaa.getString(1)); out.write(aaa.getString(2)); aaa.close();
		 * 
		 * } catch (SQLException e) { writeFile(e.getLocalizedMessage()
		 * +" -- "+e.getErrorCode()); } }
		 */

	}// Last

	/*
	 * public static void writeFile(String value){ String directoryName =
	 * "d:\\egamlog";
	 * 
	 * Date dt = new Date(); String fileName =
	 * dt.toGMTString().trim().replaceAll(":","") +".txt";
	 * System.out.println(fileName); File directory = new File(directoryName); if
	 * (!directory.exists()){ directory.mkdir(); }
	 * 
	 * File file = new File(directoryName + "/" + fileName); try{ FileWriter fw =
	 * new FileWriter(file.getAbsoluteFile()); BufferedWriter bw = new
	 * BufferedWriter(fw); bw.write(value); bw.close(); } catch (IOException e){
	 * e.printStackTrace();
	 * 
	 * } }
	 */

	public static boolean checkdigit(String key, String crcstring) {
		// System.out.println(key);
		CRC32 crc = new CRC32();
		crc.update(crcstring.getBytes());
		String enc = Long.toHexString(crc.getValue());

		// System.out.println(enc);
		if (enc.equals(key)) {

			return true;
		}
		return false;
	}

	public static String Crc32_(String crcstring) {

		CRC32 crc = new CRC32();
		crc.update(crcstring.getBytes());
		return Long.toHexString(crc.getValue());

	}

	public static String Get_Data(Connection con1, String StrSql) throws SQLException {
		PreparedStatement ps001 = con1.prepareStatement(StrSql);
		ResultSet RecSet = ps001.executeQuery();
		RecSet.next();

		String result = RecSet.getString(1);
		// System.out.println(result);

		RecSet.close();
		ps001.close();
		return result;
	}

	public static ResultSet Get_Data_all(Connection con1, String StrSql) throws SQLException {
		PreparedStatement ps001 = con1.prepareStatement(StrSql);
		ResultSet RecSet = ps001.executeQuery();
		return RecSet;
	}

	public static int SqlExc(Connection con1, String StrSql) throws SQLException {
	//	System.out.println(StrSql);
 
		Statement st = null;
		st = con1.createStatement();
		try {	
		int a = st.executeUpdate(StrSql);
		return a;
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	public static String getRandomNumberString() {
		Random rnd = new Random();
		int number = rnd.nextInt(99999);
		if (number < 100000) {
			number = number + 100000;
		}
		return String.format("%06d", number);
	}
}
/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * ========================Sp Export data
 DELIMITER $$

USE `reg`$$

DROP PROCEDURE IF EXISTS `Sp_APP_export_Data`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Sp_APP_export_Data`(IN Db_name_ VARCHAR(20),IN Gram_cd_ INT,IN Last_dt_ VARCHAR(20))
BEGIN
    
  -- DROP  TABLE  IF EXISTS  Tmp001;
CREATE TABLE IF NOT EXISTS test.Tmp001 (
  
  Ak_M_Entno INT(11) DEFAULT NULL,
  gram_Cd TINYINT(11) DEFAULT NULL,
  tal_Cd INT(11) DEFAULT NULL,
  Ak_M_MilkatNo VARCHAR(100) CHARACTER SET utf8 DEFAULT NULL,
  ak_M_kram VARCHAR(100) CHARACTER SET utf8 DEFAULT NULL,
  street VARCHAR(150) CHARACTER SET utf8 COLLATE utf8_latvian_ci DEFAULT NULL,
  Gharno VARCHAR(50) CHARACTER SET utf8 DEFAULT NULL,
  Milkat_typ VARCHAR(200) CHARACTER SET utf8 DEFAULT NULL,
  Owner_Nm TEXT CHARACTER SET utf8,
  vibhag VARCHAR(200) CHARACTER SET utf8 DEFAULT NULL,
  Milkat_Nm VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_latvian_ci DEFAULT NULL,
  milkat_V VARCHAR(1000) CHARACTER SET utf8 DEFAULT NULL,
  MobNo VARCHAR(20) CHARACTER SET utf8 NOT NULL DEFAULT '',
  Ak_Value INT(9) DEFAULT NULL,
  iseditdel CHAR(1),
  o1 FLOAT DEFAULT '0',
  o2 FLOAT DEFAULT '0',
  o3 FLOAT DEFAULT '0',
  o4 FLOAT DEFAULT '0',
  o5 FLOAT DEFAULT '0',
  o6 FLOAT DEFAULT '0',
  o7 FLOAT DEFAULT '0',
  o8 FLOAT DEFAULT '0',
  o9 FLOAT DEFAULT '0',
  o10 FLOAT DEFAULT '0',
  c1 FLOAT DEFAULT '0',
  c2 FLOAT DEFAULT '0',
  c3 FLOAT DEFAULT '0',
  c4 FLOAT DEFAULT '0',
  c5 FLOAT DEFAULT '0',
  c6 FLOAT DEFAULT '0',
  c7 FLOAT DEFAULT '0',
  c8 FLOAT DEFAULT '0',
  c9 FLOAT DEFAULT '0',
  c10 FLOAT DEFAULT '0',
  Lastdt DATETIME,
  RecLastdt DATETIME,
  db_name VARCHAR(20),
  Note1 TEXT CHARACTER SET utf8,
  Note2 TEXT CHARACTER SET utf8,
  Occupire TEXT CHARACTER SET utf8
) ENGINE=INNODB DEFAULT CHARSET=latin1 ;
DELETE FROM  test.Tmp001;
 
IF Last_dt_ = '' THEN
  SET Last_dt_ = '2000-01-01 01:01:01';
END IF  ;
 SET @yr= RIGHT(Db_name_,4);
     SET @Qry1 =  CONCAT("SET @AkarnoTot = (SELECT count(*) FROM ",db_name_,".trn_akarni_m  where gram_cd=",gram_Cd_," 	AND IsEditDel = 'c' and User_dt>'",Last_dt_,"' )") ;       
             PREPARE st1 FROM @Qry1;      
             EXECUTE st1;
      SET @Qry3 = CONCAT("insert into test.Tmp001 (iseditdel,lastdt,db_name,Ak_M_Entno,gram_Cd,tal_Cd,Ak_M_MilkatNo,ak_M_kram,street,Gharno,Milkat_typ,Owner_Nm,
                        vibhag,Milkat_Nm,milkat_V,MobNo,Ak_Value,o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10) " );
                        
                                               
      SET @Qry3 =  CONCAT(@Qry3," SELECT m.iseditdel,user_dt as Lastdt, '",db_name_,"',op.Ak_M_Entno,m.gram_Cd , m.tal_Cd ,m.Ak_M_MilkatNo,m.ak_M_kram,s.Street_NM_G AS street,
			Ak_Serve AS Gharno, lbl.Name_G AS Milkat_typ, GROUP_CONCAT(Lo.Ak_L_Nm) AS Owner_Nm, V.Name_G AS vibhag,
			mi.Milkat_Nm_G AS Milkat_Nm,m.Ak_Milkat_Detail_G AS milkat_V,IFNULL(Ak_M_Mobile1,0) AS MobNo,
			m.Ak_Value,o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,
			c1,c2,c3,c4,c5,c6,c7,c8,c9,c10
			FROM ",db_name_,".trn_akarni_m AS M,",db_name_,".trn_akarni_l_owner AS Lo,",db_name_,".trn_akarni_opn AS Op ,",db_name_,".mst_vibhag AS V,",db_name_,".mst_street AS s,",db_name_,".mst_milkat AS mi,",db_name_,".lbl_taxnm AS lbl
			WHERE Lo.Ak_M_Entno=m.Ak_M_Entno AND  m.gram_cd=",gram_Cd_," 
			AND mi.Milkat_Cd=m.Milkat_Cd
			AND lbl.Val=m.Ak_Tax_Type
			AND lbl.Gram_cd=",gram_Cd_,"
			AND s.Street_Cd=m.Street_Cd 
			AND s.Gram_Cd=",gram_Cd_,"
			AND Lo.Ak_L_Type='o'
			AND V.Auto_No=op.Vibhag_Cd AND V.Gram_Cd = ",gram_Cd_," 
			AND Lo.Gram_Cd = ",gram_Cd_," AND Lo.IsEditDel = 'c'
			AND op.Ak_M_Entno=m.Ak_M_Entno AND  op.IsEditDel='c' AND op.Gram_Cd = ",gram_Cd_," 
			AND m.User_dt>'",Last_dt_,"' 
			GROUP BY m.Ak_M_Entno  order by  user_dt " ) ;             
            
      PREPARE st3 FROM @Qry3;  
     EXECUTE st3;
     
     
     
      SET @Qry4 = CONCAT(" UPDATE test.Tmp001 SET Occupire = ( SELECT GROUP_CONCAT(Ak_L_Nm) AS Occupire FROM ",db_name_,".trn_akarni_l_owner 
			WHERE  test.Tmp001.ak_m_entno=",db_name_,".trn_akarni_l_owner.`Ak_M_Entno` AND   gram_cd=",gram_Cd_," AND IsEditDel='C' AND Ak_L_Type='B' GROUP BY Ak_M_Entno )  "); 
     PREPARE st4 FROM @Qry4;  
     EXECUTE st4;
     
        
SET @QryNote1 =CONCAT("UPDATE test.Tmp001 SET note1=(
SELECT note1 FROM (SELECT ak_m_entno,GROUP_CONCAT(CONCAT(tharav_no,':',Tharav_date,':',Remarks)) AS Note1
FROM ",db_name_,".trn_akarni_l_tharav WHERE iseditdel = 'c'  AND gram_Cd = ",gram_Cd_," GROUP BY Ak_M_EntNo )AS a1a WHERE a1a.ak_m_entno=test.Tmp001.ak_m_entno)");
    
SET @QryNote2 =CONCAT("UPDATE test.Tmp001 SET note2=(
SELECT note1 FROM (SELECT ak_m_entno,GROUP_CONCAT(CONCAT(AK_L_Bojo_Agency,':',AK_L_Bojo_Amt)) AS Note1
FROM ",db_name_,".trn_akarni_l_bojo WHERE iseditdel = 'c'  AND gram_Cd = ",gram_Cd_," GROUP BY Ak_M_EntNo )AS a1a WHERE a1a.ak_m_entno=test.Tmp001.ak_m_entno)");
    PREPARE QryNote1 FROM @QryNote1;  
     EXECUTE QryNote1;   
       PREPARE QryNote2 FROM @QryNote2;  
     EXECUTE QryNote2;
       		
SET @QryNotev =CONCAT("UPDATE test.Tmp001 SET RecLastdt=( SELECT entrydt FROM ",db_name_,".trn_akarni_vasulaat AS v   
WHERE  v.gram_cd = ",gram_Cd_," AND v.ak_m_entno=test.Tmp001.ak_m_entno  LIMIT 1) ");
 		
     			
   PREPARE QryNotev FROM @QryNotev;  
     EXECUTE QryNotev;   

UPDATE  test.Tmp001 SET lastdt= reclastdt WHERE  lastdt< reclastdt;


			
SET @Qry5 = CONCAT(" select iseditdel, aaa.Lastdt, ",@yr," as Yr ,concat(",@yr,",Ak_M_Entno) as Ak_M_Entno,gram_Cd,tal_Cd,Ak_M_MilkatNo,ak_M_kram,street,Gharno,Milkat_typ,
  Owner_Nm,vibhag,Milkat_Nm,milkat_V,MobNo,Ak_Value,o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,db_name,
  Note1,Note2,Occupire,r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,Rec_no,Rec_dt from  (SELECT * FROM test.Tmp001,
(SELECT ak_m_entno AS v_entno,
SUM(ROUND(ro1+rc1))AS r1,SUM(ROUND(ro2+rc2)) AS r2,SUM(ROUND(ro3+rc3)) AS r3,
SUM(ROUND(ro4+rc4)) AS r4,SUM(ROUND(ro5+rc5)) AS r5,SUM(ROUND(ro6+rc6)) AS r6,
SUM(ROUND(ro7+rc7)) AS r7,SUM(ROUND(ro8+rc8)) AS r8,SUM(ROUND(ro9+rc9)) AS r9,
SUM(ROUND(ro10+rc10)) AS r10,GROUP_CONCAT(R_Sr_No,R_No) AS Rec_no,R_date AS Rec_dt ,entrydt
FROM ",db_name_,".trn_akarni_vasulaat WHERE   IsEditDel='C' AND gram_Cd = ",gram_Cd_," 
GROUP BY ak_m_entno 
UNION SELECT ak_m_entno , 0,0,0,0,0,0,0,0,0,0,0,0,0 FROM ",db_name_,".trn_akarni_m 
WHERE  gram_cd=",gram_Cd_," AND  IsEditDel='C' AND  ak_m_entno 
NOT IN (SELECT ak_m_entno FROM ",db_name_,".trn_akarni_vasulaat WHERE   IsEditDel='C' AND gram_Cd = ",gram_Cd_,")) AS v 
WHERE test.Tmp001.`Ak_M_Entno` = v.v_entno ) as aaa   order by Lastdt  ") ;
     PREPARE st5 FROM @Qry5;  
     EXECUTE st5;
  
SELECT @AkarnoTot;
    END$$

DELIMITER ;
 * 
 * 
 * =============================================================================
 * ===
 * ===============================SP_app========================================
 * ======= DELIMITER $$
 * 
 * 
 * USE `reg`$$
 * 
 * DROP PROCEDURE IF EXISTS `SP_App`$$
 * 
 * CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_App`( IN Mobno_ VARCHAR(15),
 * IN Tal_Cd_ INT ) BEGIN
 * 
 * DECLARE i INT; DECLARE v_finished INTEGER DEFAULT 0; DECLARE db_Name_
 * VARCHAR(100); DECLARE HOST_ VARCHAR(100); DECLARE yr_ VARCHAR(100); DECLARE
 * T_cursor CURSOR FOR SELECT db_Name,HOST,CONCAT(From_nm,'-',to_nm) AS yr FROM
 * reg.`e_gramdbline` WHERE tal_cd =Tal_Cd_ AND RIGHT(db_name,4) >=2021 ORDER BY
 * db_name DESC ; DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1; SET
 * i = 1 ; SET @G_dist=(SELECT dist FROM reg.app_user WHERE mob_no =Mobno_);
 * SET @G_usernm=(SELECT usernm FROM reg.app_user WHERE mob_no =Mobno_);
 * SET @G_Cd=(SELECT gram_cd FROM reg.app_user WHERE mob_no =Mobno_);
 * SET @Tal_Nm=(SELECT `Tal_NameG` FROM reg.`tal_reg` WHERE `Auto_No`=Tal_Cd_);
 * DELETE FROM reg.app_temp; OPEN T_cursor; get_deta: LOOP FETCH T_cursor INTO
 * db_Name_,HOST_,yr_;
 * 
 * IF v_finished = 1 THEN LEAVE get_deta; END IF;
 * 
 * -- SET @Qry=
 * CONCAT("insert into reg.app_temp(Gram_Name,Tal_Name,Gram_Cd,Tal_Cd,HOST,Db_Name,Yr) select Gram_NM_G,'"
 * ,@Tal_Nm,"',Gram_Cd,",Tal_Cd_,",'",HOST_,"','",db_Name_,"','",yr_,"' from "
 * ,db_Name_,".mst_gram where gram_cd IN (",@G_Cd,")");
 * 
 * SET @Qry=
 * CONCAT("insert into reg.app_temp(Gram_Name,Tal_Name,Gram_Cd,Tal_Cd,HOST,Db_Name,Yr,dist,usernm) select Gram_NM_G,'"
 * ,@Tal_Nm,"',Gram_Cd,",Tal_Cd_,",'",HOST_,"','",db_Name_,"','",yr_,"','",@
 * G_dist,"','",@G_usernm,"' from "
 * ,db_Name_,".mst_gram where gram_cd IN (",@G_Cd,")");
 * 
 * PREPARE st1 FROM @Qry; EXECUTE st1;
 * 
 * 
 * END LOOP get_deta; CLOSE T_cursor;
 * 
 * 
 * SELECT * FROM reg.app_temp;
 * 
 * END$$
 * 
 * DELIMITER ;
 * ALTER TABLE `reg`.`app_user`   ADD COLUMN `OtpDt` DATETIME NULL AFTER `usernm`;
 */