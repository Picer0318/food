<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$id = $_POST['id'];
$collect_phone = $_POST['collect_phone'];

$sql = "SELECT * FROM `food1` WHERE id='$id' AND bln_complete = '2'";
$check = mysqli_fetch_array(mysqli_query($con,$sql));

if(isset($check)){
	
	echo '1';
	
}else{
	$qurey = " UPDATE food1 SET bln_collect = '0' WHERE food1.id = $id";
	$qurey1 = " DELETE FROM collect WHERE collect_id = $id AND collect_phone = $collect_phone";
	mysqli_query($con,$qurey)or die(mysqli_error($con));
	mysqli_query($con,$qurey1)or die(mysqli_error($con));

}




mysqli_close($con);
?>