<?php
	$conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
	mysqli_query($conn,'SET NAMES utf8');

	$sql = "SELECT postNumber from board order by postNumber desc limit 1";
    $result = mysqli_query($conn, $sql);
    $row = mysqli_fetch_array($result);

    $response = array();
    if($row[0] == null) $row[0] = 1;
    else {
        $row[0] += 1; 
    }

    array_push($response, array('index'=>$row[0]));
    echo json_encode($response);
    mysqli_close($conn);
?>