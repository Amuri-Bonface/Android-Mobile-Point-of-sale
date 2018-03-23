<?php  
 require "init.php";  
 
	//$owner_email=$_POST["owner_email"];
		
	$sql_check="select DistributorCD,DistributorName,MobileNo from tbldistributor;";
	$result = mysqli_query($con,$sql_check);

	 if(mysqli_num_rows($result) >0 )  
	 {  		
			$sql_check_again="select DistributorCD,DistributorName,MobileNo from tbldistributor;";
			$second_result = mysqli_query($con,$sql_check_again);
			  if(mysqli_num_rows($second_result) >0 )  
					{  
						$json=array();		
						while($row = mysqli_fetch_assoc($second_result))
						{					 
							$json[]=$row;
						}
													
						 echo json_encode($json);
					}
	}  
	 else  
	 {   
	 $json['reply'] = "Error";  
	 echo json_encode($json);
		mysqli_close($con);
	 }

 ?>  