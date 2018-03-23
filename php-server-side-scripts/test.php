<?php  
 require "init.php";  
 
  
 $sql_query = "select DistributorCD from tbldistributor ORDER BY Auto DESC LIMIT 1;";  
 $result = mysqli_query($con,$sql_query);  
 if(mysqli_num_rows($result) >0 )  
		{  
			 $row = mysqli_fetch_assoc($result);  
			 $name =$row["DistributorCD"];  
			 $json['reply'] = $name;
				echo json_encode($json);

			}  
  
 ?>  