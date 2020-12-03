<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$index 		= $_POST["index"];
	$title 		= $_POST["title"];
	$content 	= $_POST["content"];
	$writer 	= $_POST["writer"];
    $date 		= $_POST["date"];
    
    /*$index 		= "zz";
	$name 		= "zz";
	$content 	= "zz";
	$writer 	= "zz";
	$date 		= "zz";*/


	$statemente = mysqli_prepare($conn, "INSERT INTO board VALUES(?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statemente, "sssss", 
		$index, $title, $content, $writer, $date);
	mysqli_stmt_execute($statemente);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>