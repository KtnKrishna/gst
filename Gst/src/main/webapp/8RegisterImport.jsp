<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>8 Register</title>
<style type="text/css">
	*,
	*:before,
	*:after {
	  -webkit-box-sizing: border-box;
	  -moz-box-sizing: border-box;
	  box-sizing: border-box;
	}
	
	body {
	  margin: 0;
	  padding: 2rem 1.5rem;
	  font: 1rem/1.5 "PT Sans", Arial, sans-serif;
	  color: #5a5a5a;
	}
	form {
		border: 1px solid black;
		padding: 15px;
	}
	
	 input[type='submit'] {
		  background-color: white; /* Green */
		  border: 2px solid #0984e3;
		  color: black;
		  padding: 16px 32px;
		  text-align: center;
		  text-decoration: none;
		  display: inline-block;
		  font-size: 16px;
		  margin: 4px 2px;
		  transition-duration: 0.4s;
		  cursor: pointer;
		  border-radius: 4px;
		  margin-right: 71px
	}
	
	input[type='submit']:hover {
	  background-color: #0984e3;
	  color: white;
	}
</style>
</head>
<body>
<center>
	<form action="readExcelMehsul" method="post" enctype="multipart/form-data">
		<label style="font-size: 20px;color: black;"><b>	ગામ નમુનો નંબર - ૮ નું રજીસ્ટરની એક્સેલ	ફાઈલ	 સિલેક્ટ કરવી		</b>	</label><br><br>
		<label class="file">
			<input type="file" name="file" id="file1" required="required" aria-label="File browser example" >	  
			<span class="file-custom"></span>
		</label><br><br>
		<input type="submit" value="Submit">	 
	</form>
	
	
		<form action="readTextMehsul" method="post" enctype="multipart/form-data">
		<label style="font-size: 20px;color: black;"><b>	ગામ નમુનો નંબર - ૮ નું રજીસ્ટરની એક્સેલ	ફાઈલ	 સિલેક્ટ કરવી		</b>	</label><br><br>
		<label class="file">
			<input type="file" name="file" id="file1" required="required" aria-label="File browser example" >	  
			<span class="file-custom"></span>
		</label><br><br>
		<input type="submit" value="Submit">	 
	</form>
	
</center>
</body>

</html>