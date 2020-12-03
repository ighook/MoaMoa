<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$sql = "select * from review";
    $result = mysqli_query($conn, $sql);
    $index = mysqli_num_rows($result);

    $response = array();
    $response["index"] = $index;
    echo json_encode($response);
    mysqli_close($conn);
?>