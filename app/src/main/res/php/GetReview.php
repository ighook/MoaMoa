<?php
    $conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
    mysqli_query($conn,'SET NAMES utf8');
    
    //$response["stats"] = 1;
    
    $name = $_POST["name"];
    //$name = "Cosy kitchen";
    

    $sql = "SELECT num, writer, content, date, eval1, eval2, eval3, eval4, image1, image2, image3, image4 FROM review WHERE name='$name'";
	$res = mysqli_query($conn, $sql);
   	$result = array();

   	while($row = mysqli_fetch_array($res)) { 
        array_push($result, array('num'=>$row[0], 'writer'=>$row[1],'content'=>$row[2],'date'=>$row[3], 'eval1'=>$row[4],
        'eval2'=>$row[5], 'eval3'=>$row[6], 'eval4'=>$row[7], 'image1'=>$row[8], 'image2'=>$row[9], 'image3'=>$row[10], 'image4'=>$row[11])); 
   	}

    //echo json_encode(array($result), JSON_UNESCAPED_UNICODE);
    if($name == "") {
        array_push($result, array('code'=>"error"));
        //echo json_encode($result);
    }
    echo json_encode($result);
    //echo json_encode($response);
?>