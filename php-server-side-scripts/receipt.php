<?php
require "init.php"; 
 
$sql = "select Auto from mobile_receipt ORDER BY Auto DESC LIMIT 1;";  
 
$result = mysqli_query($con,$sql);  
 
 if(mysqli_num_rows($result) >0 )  
 {  
 $row = mysqli_fetch_assoc($result);  
 $x=$row["Auto"]+1; 
 mysqli_close($con);
 echo json_encode($x);
	
 }  
 else  
 {   
 $json['error'] = "Error..";  
 echo json_encode($json);
 }  
//mysqli_close($con);
   
 
?>