<?php  
 require "init.php";  

$PaymentMode="mobile cash";
$RefNo="mobile ref";
$Bank="app bank";
$Branch="N/A";
$shopNo="1";
//$InvoiceN=$_POST"InvoiceNo"];
$Month=$_POST["Month"];
$Year=$_POST["Year"];
$DistributorCD=$_POST["DistributorCD"];
$DistributorName=$_POST["DistributorName"];
$ProductCD=$_POST["ProductCD"];
$Description=$_POST["Description"];
$UnitPrice=$_POST["UnitPrice"];
$Quantity=$_POST["Quantity"];
$BV=$_POST["BV"];
//$Sbx=$_POST["SubTotBV"];
$SubTotBV=$BV*$Quantity;
$SubTotal=$_POST["SubTotal"];
$CashTend=$_POST["CashTend"];
$CashChange=".00";
$DateEncoded=$_POST["DateEncoded"];
$EncodedBy="admin";
//$uniques=$_POST["unique"];
		

	

/*$PaymentMode="mobile cash";
$RefNo="mobile ref";
$Bank="app bank";
$Branch="N/A";
$shopNo="1";
//$InvoiceN="23";
$Month="October";
$Year="2017";
$DistributorCD="1";
$DistributorName="buda";
$ProductCD="78";
$Description="matic";
$UnitPrice="20";
$Quantity="5";
$BV="3";
$SubTotBV=$Quantity*$BV;
$SubTotal="60";
$CashTend="60";
$CashChange="N/A";
$DateEncoded="date";
$EncodedBy="admin";
//$uniques="avv";*/

$sql = "select Auto from mobile_receipt ORDER BY Auto DESC LIMIT 1;";  
 $result = mysqli_query($con,$sql);  
  if(mysqli_num_rows($result) >0 )  
 {  
 $row = mysqli_fetch_assoc($result);  
 $Invoice=$row["Auto"]+1; 
 $InvoiceNo='RCPT'.$Invoice;
  }  	
		
	
			$query="insert into tblsales(PaymentMode,RefNo,Bank,Branch,shopNo,InvoiceNo, Month, Year, DistributorCD, DistributorName, ProductCD, Description, UnitPrice, Quantity, BV, SubTotBV, SubTotal, CashTend, CashChange, DateEncoded, EncodedBy)
			values('$PaymentMode','$RefNo','$Bank','$Branch','$shopNo','$InvoiceNo', '$Month','$Year','$DistributorCD','$DistributorName','$ProductCD','$Description','$UnitPrice','$Quantity','$BV', '$SubTotBV','$SubTotal','$CashTend', '$CashChange','$DateEncoded','$EncodedBy');";
					mysqli_query($con, $query) or die (mysqli_error($con));
					$json['success'] = 'Succes';
						echo json_encode($json);
					mysqli_close($con);
				
				
			

			
			
			
				
			
			  
	

 ?>  