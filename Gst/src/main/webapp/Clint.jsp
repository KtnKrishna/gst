<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="Util.ConnectionUtil"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
::-webkit-scrollbar { 
    display: none; 
}

input{
width: 80%;
height: 25px;
border-radius: 4px;
margin-bottom: 5px;
}
.mm{
width: 80%;height: 45vh
 

}
.main{
width: 35%;
height: 42vh;
background-color: white;
border:  2px solid #1868E3;
float: left;
}
.inner{
width: 100%;
height: 5vh; text-align: center;color: white;font-weight: bold;
background-color: #1868E3;
}
.main1{
width: 35%;
height: 42vh;
background-color: white;
border:  2px solid #1868E3;
float: right;
}
#customers{
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 6px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}

#customers tr:hover {background-color: #ddd;}

#customers th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}
td{text-align: center;}
</style>


<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<div class="mm">


<div  class="main">
<div class="inner">
Create User
</div>
<br>
<form action="CreateC" method="post">
<input type="text" name="name" placeholder="Name...."> 
<input type="email" name="email" placeholder="Email....">
<input type="number" name="mob" placeholder="Number....">
<input type="text" name="credit" placeholder="Credit....">
<input type="text" name="gst" placeholder="GstNumber......">
<input type="hidden" name="No" id="IdNumber">
<input type="Submit" style="border: none;color: white;background-color:#1868E3;width: 48%;" id="btn" name="btn" value="Save"> <input type="Submit" style="display:none ;border: none;color: white;background-color:red;width: 48%;" id="dlt" name="btn" value="Delete"> 
</form>
<p style="color: red"> ${param.msg}</p>
</div>
<div class="main1">

<div class="inner" style="width: 50%;float: left;">
User Name
</div>
<div class="inner" style="width: 50%;float: right;">
Left Credit
</div>

<div style="width: 100%;height: 33vh;overflow: scroll;">
<table style="width: 100%;" id="customers">

<%Connection con=ConnectionUtil.getConnection();
PreparedStatement ps=con.prepareStatement("select Auto_no,name,credit,email,phone,gst from user order by name");
ResultSet rs=ps.executeQuery();
if(rs.next()){
do{%>
<tr onclick="Fun(this)"><td style="width: 50%;"><%=rs.getString(2) %></td> <td style="width: 50%;"><%=rs.getString(3) %></td><td style="display:none;"><%=rs.getInt(1) %></td><td style="display: none;"><%=rs.getString(4)%></td><td style="display: none;"><%=rs.getString(5)%></td> <td style="display: none;"><%=rs.getString(6) %></td><tr>
<%
}while(rs.next());
rs.close();ps.close();con.close();
}
%>


</table>
<script type="text/javascript">
function Fun(a) {
var frm=document.forms[0];
frm.name.value=a.cells[0].innerHTML;
 frm.email.value=a.cells[3].innerHTML;
frm.mob.value=a.cells[4].innerHTML;
frm.credit.value=a.cells[1].innerHTML;
frm.gst.value=a.cells[5].innerHTML;
frm.No.value=a.cells[2].innerHTML;
document.getElementById('btn').value='Update';
document.getElementById('dlt').style.display='';
	
}

</script>
</div>
</div>
 </div></center>
</body>
</html>