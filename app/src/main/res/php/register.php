<?php
	$conn = new mysqli("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn, 'SET NAMES utf8');

	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$password_hash = hash("sha256", $userPassword);
    $userName = $_POST["userName"];
	$userNicName = $_POST["userNicName"];
	
	$response["state"] = 0;

	/*$userID = "aa";
    $userPassword = "aa";
    $userName = "aa";
    $userNicName = "aa";*/

    $sql = "SELECT * from user where ID = '$userID'";
    $result = mysqli_query($conn, $sql);
	//$row = mysqli_fetch_array($result);
	$count = mysqli_num_rows($result);

	$sql = "SELECT * from user where NicName = '$userNicName'";
    $result = mysqli_query($conn, $sql);
	$count2 = mysqli_num_rows($result);

	if($count == 1) {
		$response["state"] = 2;
	}
	else if(strlen($userID) < 6 || strlen($userID) > 15) {
		$response["state"] = 3;
	}
	else if($count2 == 1) {
		$response["state"] = 4;
	}
	else {
		$statement = mysqli_prepare($conn, "INSERT INTO user VALUES (?,?,?,?)");
	    mysqli_stmt_bind_param($statement, "ssss", $userID, $password_hash, $userName, $userNicName);
	    mysqli_stmt_execute($statement);

	    $response = array();
		$response["state"] = 1;
	}
	echo json_encode($response);    
?>