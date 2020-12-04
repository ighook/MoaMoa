<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$reviewIndex = 1;//$_POST['index'];

	$sql = "SELECT * FROM review where index = '$reviewIndex'";
	$row = mysqli_fetch_array($result);

	ehco json_encode($row);
?>