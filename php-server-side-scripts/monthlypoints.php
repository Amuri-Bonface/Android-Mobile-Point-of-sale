<?php  
 require "init.php";  
 
$distCode=$_POST["distributorCode"];
// where apbvmpbv.DistributorCD='$distCode' and apbv.DistributorCD='$distCode'
		
	$sql_check="select apbvmpbv.DistributorName,apbvmpbv.totalMPBV,apbvmpbv.Month,apbvmpbv.Year,apbv.APBV from apbvmpbv,apbv;";
	$res = mysqli_query($con,$sql_check);

	 if(mysqli_num_rows($res) >0 )  
	 {  		
			$sql_check_again="select apbvmpbv.DistributorName,apbvmpbv.totalMPBV,apbvmpbv.Month,apbvmpbv.Year,apbv.APBV from apbvmpbv,apbv where apbvmpbv.DistributorCD='$distCode' and apbv.DistributorCD='$distCode';";
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