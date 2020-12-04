<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$sql = "SELECT Name, Address, Telephone, Info FROM restaurant";
	$res = mysqli_query($conn, $sql);
   	$result = array();

   	while($row = mysqli_fetch_array($res)) { 
   		array_push($result, array('name'=>$row[0], 'address'=>$row[1], 'telephone'=>$row[2], 'info'=>$row[3])); 
   	}

	echo json_encode(array("result"=>$result),JSON_UNESCAPED_UNICODE);
?>