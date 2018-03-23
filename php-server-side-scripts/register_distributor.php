<?php


//INSERT_RECORD "insert into tbldistributor(DistributorCD, distributorname, mobileno, dob, postalcd, businessno, email, town, physicaladdr, county,passportphoto, description, SponsorCD,kitcd, regamnt,dateencoded, encodedby,status, searchterm, remarks) 

/*main variables
$DistributorCD=$_POST["distributorNo"];
$distributorname=$_POST["distributorName"];
$mobileNo=$_POST["mobileNo"];
$dateencoded=$_POST["regDate"];
$town=$_POST["town"];
$SponsorCD=$_POST["sponsorNo"];*/
	
require "init.php";

/*$DistributorCD="361";
$distributorname="amuri";
$mobileno="+254717264871";
$dateencoded="07/14/2017";
$town="juja";
$SponsorCD="314";*/

//main variables
$DistributorCD=$_POST["distributorNo"];
$distributorname=$_POST["distributorName"];
$mobileNo=$_POST["mobileNo"];
$dateencoded=$_POST["regDate"];
$town=$_POST["town"];
$SponsorCD=$_POST["sponsorNo"];

			//additional variables
			$dob=date('m/d/Y');
			$postalcd="N/A";
			$businessno="+254";
			$email="N/A";
			$physicaladdr="N/A";
			$county=$town;
			$passportphoto="N/A";
			$description="N/A";
			$kitcd="N/A";
			$regamnt="N/A";
			
			$encodedby="mobile app";
			$status="Active";
			$searchterm="N/A";
			$remarks="N/A";
$sql_check="select * from tbldistributor;";
 $result = mysqli_query($con,$sql_check);
 
	if(mysqli_num_rows($result) >0 )  
	 { 
	 $sql_query="insert into tbldistributor(DistributorCD, distributorname, MobileNo, dob, postalcd, businessno, email, town, physicaladdr, county,passportphoto, description, SponsorCD,kitcd, regamnt,dateencoded, encodedby,status, searchterm, remarks) 
	 values('$DistributorCD', '$distributorname', '$mobileNo', '$dob', '$postalcd', '$businessno', '$email', '$town', '$physicaladdr', '$county','$passportphoto', '$description', '$SponsorCD','$kitcd', '$regamnt','$dateencoded', '$encodedby','$status', '$searchterm','$remarks');";
		mysqli_query($con, $sql_query) or die (mysqli_error($con));
		mysqli_close($con); 
	 
}
	/*
       //     ''''checking and updating levels to be modified with a loop''''''''''''''''''''''''''''''''''''''''''''
                                            
		*/
	
//lets get  sponsor details
require "init.php";
$sql_query00="select * from tbldistributor where DistributorCD ='$DistributorCD';";
$result00 = mysqli_query($con,$sql_query00);
if(mysqli_num_rows($result00) >0 )
			{
 
 //Updating sponsors details
$sql_query1 ="Update tbldistributor SET SPL1='$SponsorCD' WHERE DistributorCD ='$DistributorCD';";
	mysqli_query($con, $sql_query1) or die 
			(mysqli_error($con));
					mysqli_close($con); 
					//$json['reply']="No more sponsors ahead";
					//echo json_encode($json);
			}	
//lets get 2nd sponsor details
require "init.php";
$sql_query2="select * from tbldistributor where DistributorCD ='$SponsorCD';";
$result = mysqli_query($con,$sql_query2);
if(mysqli_num_rows($result) >0 )
			{
				$row = mysqli_fetch_assoc($result); 
				$sponsCD1 =$row["SponsorCD"];
				
				if ($sponsCD1!=null)
					{
						$sql_query3 ="Update tbldistributor SET SPL2='$sponsCD1' WHERE DistributorCD ='$DistributorCD';";
							mysqli_query($con, $sql_query3) or die 
								(mysqli_error($con));
									mysqli_close($con); 
								//	$json['reply']="No more sponsors ahead";
					//echo json_encode($json);
					}
			}
			
//lets get 3rd sponsor details
require "init.php";
$sql_query4="select * from tbldistributor where DistributorCD ='$sponsCD1';";
$result4 = mysqli_query($con,$sql_query4);
if(mysqli_num_rows($result4) >0 )
			{
				$row = mysqli_fetch_assoc($result4); 
				$sponsCD2 =$row["SponsorCD"];
				
				if ($sponsCD2!=null)
					{
							$sql_query5 ="Update tbldistributor SET SPL3='$sponsCD2' WHERE DistributorCD ='$DistributorCD';";
							mysqli_query($con, $sql_query5) or die;
								
				$sql_query6="select * from tbldistributor where DistributorCD ='$sponsCD2';";
				$result5 = mysqli_query($con,$sql_query6);
				if(mysqli_num_rows($result5) >0 )
						{
							$row = mysqli_fetch_assoc($result5); 
							$sponsCD3 =$row["SponsorCD"];
				
				if ($sponsCD3!=null)
					{
						$sql_query7 ="Update tbldistributor SET SPL4='$sponsCD3' WHERE DistributorCD ='$DistributorCD';";
							mysqli_query($con, $sql_query7) or die 
								(mysqli_error($con));
									
					$sql_query8="select * from tbldistributor where DistributorCD ='$sponsCD3';";
					$result6 = mysqli_query($con,$sql_query8);
					if(mysqli_num_rows($result6) >0 )
								{
									$row = mysqli_fetch_assoc($result6); 
									$sponsCD4 =$row["SponsorCD"];
				
				if ($sponsCD4!=null)
					{
						$sql_query9 ="Update tbldistributor SET SPL5='$sponsCD4' WHERE DistributorCD ='$DistributorCD';";
							mysqli_query($con, $sql_query9) or die ;
																		
					$sql_query10="select * from tbldistributor where DistributorCD ='$sponsCD4';";
					$result7 = mysqli_query($con,$sql_query10);
					if(mysqli_num_rows($result7) >0 )
								{
									$row = mysqli_fetch_assoc($result7); 
									$sponsCD5 =$row["SponsorCD"];
									
				if ($sponsCD5!=null)
					{
						$sql_query11 ="Update tbldistributor SET SPL6='$sponsCD5' WHERE DistributorCD ='$DistributorCD';";
							mysqli_query($con, $sql_query11) or die; 
								
							$sql_query12="select * from tbldistributor where DistributorCD ='$sponsCD5';";
							$result8 = mysqli_query($con,$sql_query12);
							if(mysqli_num_rows($result7) >0 )
										{
											$row = mysqli_fetch_assoc($result8); 
											$sponsCD6 =$row["SponsorCD"];
				
				if ($sponsCD6!=null)
					{
						$sql_query13 ="Update tbldistributor SET SPL7='$sponsCD6' WHERE DistributorCD ='$DistributorCD';";
							mysqli_query($con, $sql_query13) or die 
								(mysqli_error($con));
									mysqli_close($con); 
									//$json['reply']="No more sponsors ahead";
									//echo json_encode($json);
					}
					}}}}}}}}
			}	
		$json['reply']="Record Saved Succesfully";
			echo json_encode($json);	




?> 