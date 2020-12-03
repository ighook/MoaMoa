<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$sql = "SELECT title, content, date from board order by date desc limit 2";
	$res = mysqli_query($conn, $sql);
	$result = array();

   	while($row = mysqli_fetch_array($res)) { 
   		array_push($result, array('title'=>$row[0], 'content'=>$row[1], 'date'=>$row[2])); 
   	}

	echo json_encode(array("result"=>$result), JSON_UNESCAPED_UNICODE);
?>