<?php
    $con = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPW = $_POST["userPW"];
    //$userID = "asdasd";
    //$userPW = "asd";
    $password_hash = hash("sha256", $userPW);
    //echo $password_hash. "<br>";
    
    $response["stats"] = 0;
    $response["nicName"] = "";
    /*$statement = mysqli_prepare($con, "SELECT * FROM user WHERE ID = ? AND PW = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $password_hash);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword);*/

    $sql = "select * from user where ID='$userID' and PW='$password_hash'";
    $result = mysqli_query($con, $sql);
    $row = mysqli_fetch_array($result);
    $count = mysqli_num_rows($result);

    //echo $password_hash. "<br>";
    //echo $count. "<br>";
    //$response = array();
    //$response["success"] = 1;
    //if($userID==$row['ID'] && $password_hash==$row['PW'])

    if($count == 1)
    {
    	$sql = "select NicName from user where ID='$userID'";
    	$result = mysqli_query($con, $sql);
    	$row = mysqli_fetch_array($result);

        $response["stats"] = 1;
        $response["nicName"] = $row["NicName"];
    }
    else
    {
    	$response["stats"] = 2;
    }

    /*$response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        //$response["userID"] = $userID;
        //$response["userPassword"] = $userPassword;
    }*/
    //echo mysqli_stmt_fetch($statement). "<br>";
    echo json_encode($response);
?>