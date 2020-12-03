<?php
    $conn = mysqli_connect("localhost", "ighook", "wlsqja4292!", "ighook");
    mysqli_query($conn,'SET NAMES utf8');
    
    $name = $_POST["name"];
    //$name = "Cosy kitchen";

    $type = array();
    $menu = array();
    //$sql = "SELECT COUNT(distinct type) FROM menu WHERE name='$name'";
    $sql = "SELECT DISTINCT type FROM menu WHERE name='$name'";
    $result = mysqli_query($conn, $sql);
    $row = mysqli_num_rows($result);

    while ($list = mysqli_fetch_array($result)) {
        $type[] = $list['type'];
        //echo $list['type']."<br>";
    }
    //echo json_encode($type, JSON_UNESCAPED_UNICODE). "<br>";
    

    for($i = 0; $i < $row; $i++) {
        $sql2 = "SELECT menu, price FROM menu WHERE name='$name' AND type='$type[$i]'";
        $result2 = mysqli_query($conn, $sql2);
        //$row2 = mysqli_num_rows($result2);
        $arr = array();
        while($row2 = mysqli_fetch_array($result2)) {
            array_push($arr, array('menu'=>$row2[0],'price'=>$row2[1]));
            //$menu[$i] = array($row[0], $row[1]);
            //echo json_encode($arr, JSON_UNESCAPED_UNICODE);
        }
        //echo json_encode(array($type[$i] => $arr), JSON_UNESCAPED_UNICODE). "<br>";
        $menu[$type[$i]] = $arr;
        
    }
    echo json_encode($menu, JSON_UNESCAPED_UNICODE);
    mysqli_close($conn);

    //echo $type;
	//$res = mysqli_query($conn, $sql);
   	//$result = array();

    //echo $res;

    
   	/*while($row = mysqli_fetch_array($res)) { 
        array_push($result, array('type1'=>$row[0],'type1'=>$row[1],'type1'=>$row[2], 'eval1'=>$row[3],
        'eval2'=>$row[4], 'eval3'=>$row[5], 'eval4'=>$row[6], 'image1'=>$row[7], 'image2'=>$row[8], 'image3'=>$row[9], 'image4'=>$row[10])); 
   	}*/

    //echo json_encode(array($result), JSON_UNESCAPED_UNICODE);
    /*if($name == "") {
        array_push($result, array('code'=>"error"));
    }
    echo json_encode($result);*/
?>