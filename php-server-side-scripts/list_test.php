<?php  
 require "init.php";  
 

	$sql_check="select Auto,Description,SellingPrice,Remarks,image from tblproduct;";
	$res = mysqli_query($con,$sql_check);
	
	 if(mysqli_num_rows($res) >0 )  
	 {  		
			$sql_check_again="select Auto,Description,UnitPrice,Remarks,image,BV from tblproduct;";
			$second_result = mysqli_query($con,$sql_check_again);
			
			  if(mysqli_num_rows($second_result) >0 )  
					{  
					
						 $result=array();
						 while($row = mysqli_fetch_array($second_result))
						array_push($result,array('Auto'=>$row[0],'Description'=>$row[1],'SellingPrice'=>$row[2],'Remarks'=>$row[3],'image'=>$row[4],'BV'=>$row[5]));
						 echo json_encode(array("products"=>$result));
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