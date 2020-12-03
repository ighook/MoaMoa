<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$sql = "SELECT * FROM board";
	$res = mysqli_query($conn, $sql);
	$result = array();

   	while($row = mysqli_fetch_array($res)) { 
   		array_push($result, array('index'=>$row[0], 'title'=>$row[1], 'content'=>$row[2], 'writer'=>$row[3], 'date'=>$row[4])); 
   	}

	echo json_encode(array("result"=>$result), JSON_UNESCAPED_UNICODE);
?>