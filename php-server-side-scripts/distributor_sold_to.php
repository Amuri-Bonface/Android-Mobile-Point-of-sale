<?php  
 require "init.php";  
 
	//$owner_email=$_POST["owner_email"];
		
	$sql_check="select DistributorCD,DistributorName,MobileNo from tbldistributor;";
	$res = mysqli_query($con,$sql_check);

	 if(mysqli_num_rows($res) >0 )  
	 {  		
			$sql_check_again="select DistributorCD,DistributorName,MobileNo from tbldistributor;";
			$second_result = mysqli_query($con,$sql_check_again);
			  if(mysqli_num_rows($second_result) >0 )  
					{  
						
						 $result=array();
						 while($row = mysqli_fetch_array($second_result))
						array_push($result,array('DistributorCD'=>$row[0],'DistributorName'=>$row[1],'MobileNo'=>$row[2]));
						 echo json_encode(array("distributors"=>$result));
						 mysqli_close($con);
					}
	}  
	 else  
	 {   
	 $json['reply'] = "Error";  
	 echo json_encode($json);
		mysqli_close($con);
	 }

 ?>  