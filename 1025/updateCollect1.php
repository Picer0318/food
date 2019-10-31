<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$id = $_POST['id'];
$collect_phone = $_POST['collect_phone'];


$sql = "SELECT * FROM `collect` WHERE collect_phone = '$collect_phone' AND collect_id = '$id'";
$check = mysqli_fetch_array(mysqli_query($con,$sql));

if(isset($check)){
	
	echo '1';
	
}else{
	$qurey = " UPDATE food1 SET bln_collect = '1' WHERE food1.id = $id";
	$qurey1 = "INSERT INTO `collect`(`collect_phone`, `collect_id`) VALUES ('$collect_phone','$id')";
	mysqli_query($con,$qurey)or die(mysqli_error($con));
	mysqli_query($con,$qurey1)or die(mysqli_error($con));

	
}


mysqli_close($con);
?>