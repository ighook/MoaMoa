<?php

    $servername = "localhost";
    $username = "ighook";
    $password = "wlsqja4292!";
    $dbname = "ighook";

    $region = $_GET['region'];
    if($region == NULL) echo "검색 키워드 없음", "<br>";
    else echo "[", $region, "] 검색 결과", "<br>";

    // 접속 생성
    $conn = new mysqli($servername, $username, $password, $dbname);
    if ($conn->connect_error) {
        die("Connection failed : " . $conn->connect_error);
        mysqli_close($conn);
    }
    
    mysqli_set_charset($conn, "utf8");

    $sql = "SELECT license, name, address FROM store WHERE address LIKE '%$region%'";
    $res = mysqli_query($conn, $sql);

    $result = array();
    
    while($row = mysqli_fetch_array($res))
        array_push($result, array('license'=>$row[0], 'name'=>$row[1], 'address'=>$row[2]));

    echo json_encode(array("result"=>$result), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);

    mysqli_close($conn);

?>

