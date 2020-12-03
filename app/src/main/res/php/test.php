<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	

	$array = array();
    $response["index"] = 2;
    echo json_encode($response);
?>