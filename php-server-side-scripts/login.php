<?php  
 require "init.php";  
 $user_name = $_POST["login_name"];
 $user_pass = $_POST["login_pass"];
 $shop_no = $_POST["shop_no"];
   
  
 $sql_query = "select Fullname from tbluser where Username like '$user_name' and Password like '$user_pass' and shopNo like '$shop_no';";  
 $result = mysqli_query($con,$sql_query);  
 if(mysqli_num_rows($result) >0 )  
		{  
			 $row = mysqli_fetch_assoc($result);  
			 $name =$row["Fullname"];  
			 $json['reply1'] = 'success';
			 $json['reply'] = ' Welcome '.$name;
				echo json_encode($json);

			}  
 else  
 {   
 $json['reply1'] = 'fail';
$json['reply'] = "Login Failed.......Try Again.."; 
	echo json_encode($json);

 }  
 ?>  