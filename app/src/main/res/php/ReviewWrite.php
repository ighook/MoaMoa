<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$index 		= $_POST["index"];
	$name 		= $_POST["name"];
	$content 	= $_POST["content"];
	$writer 	= $_POST["writer"];
	$date 		= $_POST["date"];
	$eval1 		= $_POST["eval1"];
	$eval2 		= $_POST["eval2"];
	$eval3 		= $_POST["eval3"];
	$eval4 		= $_POST["eval4"];
	$image1 	= $_POST["image1"];
	$image2 	= $_POST["image2"];
	$image3 	= $_POST["image3"];
	$image4 	= $_POST["image4"];

	/*$sql = "select * from review";
    $result = mysqli_query($conn, $sql);
    $count = mysqli_num_rows($result);
    $num = $count + 1;
    echo $num."\n";*/

	/*$content 	= "맛있어3";	
	$name 		= "코시";
	$writer 	= "운영자";
	$date 		= "2020-11-12";
	$eval1 		= 2;
	$eval2 		= 3;
	$eval3 		= 4.5;
	$eval4 		= 1.5;
	$image1 	= "null";
	$image2 	= "null";
	$image3 	= "null";
	$image4 	= "null";*/

	$statemente = mysqli_prepare($conn, "INSERT INTO review VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statemente, "sssssssssssss", 
		$index, $name, $content, $writer, $date, 
		$eval1, $eval2, $eval3, $eval4, 
		$image1, $image2, $image3, $image4);
	mysqli_stmt_execute($statemente);

	$response = array();
	$response["success"] = true;
	$response["num"] = $num;

	echo json_encode($response);
?>