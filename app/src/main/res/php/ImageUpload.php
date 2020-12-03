<?php
	header("Content-Type:text/html; charset=UTF-8");

	// 파일 형식
	/*$type1 = $_POST['type1'];
	$type1 = $_POST['type2'];
	$type1 = $_POST['type3'];
	$type1 = $_POST['type4'];*/

	// 이미지
	$file1 = $_FILES['img1'];
	$file2 = $_FILES['img2'];
	$file3 = $_FILES['img3'];
	$file4 = $_FILES['img4'];

	// 게시글 번호
	$index = $_POST['index'];

	// 파일 이름
	$fileName1 = $_POST['name1'];
	$fileName2 = $_POST['name2'];
	$fileName3 = $_POST['name3'];
	$fileName4 = $_POST['name4'];

	//$srcName1 = $file1['name'];
	/*$srcName2 = $file2['name'];
	$srcName3 = $file3['name'];
	$srcName4 = $file4['name'];*/

	// 파일 임시 이름
	$tmpName1 = $file1['tmp_name'];
	$tmpName2 = $file2['tmp_name'];
	$tmpName3 = $file3['tmp_name'];
	$tmpName4 = $file4['tmp_name'];

	$error1 = "";
	$error2 = "";
	$error3 = "";
	$error4 = "";

	// 1번 이미지
	if($file1 != "") {
		$dstName = "images/".$fileName1;
		$result = move_uploaded_file($tmpName1, $dstName);
		if($result) $error1 = "X";
		else $error1 = "O";
	}
	else $error1 = "-";

	// 2번 이미지
	if($file2 != "") {
		$dstName = "images/".$fileName2;
		$result = move_uploaded_file($tmpName2, $dstName);
		if($result) $error2 = "X";
		else $error2 = "O";
	}
	else $error2 = "-";

	// 3번 이미지
	if($file3 != "") {
		$dstName = "images/".$fileName3;
		$result = move_uploaded_file($tmpName3, $dstName);
		if($result) $error3 = "X";
		else $error3 = "O";
	}else $error3 = "-";

	// 4번 이미지
	if($file4 != "") {
		$dstName = "images/".$fileName4;
		$result = move_uploaded_file($tmpName4, $dstName);
		if($result) $error4 = "X";
		else $error4= "O";
	}
	else $error4 = "-";

	echo $error1.$error2.$error3.$error4;
	//ehco $fileName1.$fileName2.$fileName3.$fileName4;
	//$dstName = "images/".date('Ymd_his').$index."_".$srcName;
	/*$dstName = "images/".$index."_".$srcName;
	$result = move_uploaded_file($tmpName, $dstName);
	
	if($result) {
		echo $index;
	}
	else {
		echo "fail-".$tmpName;
		//echo "upload fail\n";
	}*/

?>