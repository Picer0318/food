<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$phone = $_POST['phone'];
$passward = $_POST['password'];

$sql = "SELECT * FROM `user` WHERE phone = '$phone'";

$check = mysqli_fetch_array(mysqli_query($con,$sql));
$response = array();

if(isset($check)){
	
	echo '1';
	
}else{
	$qurey = " INSERT INTO `user`(`phone`, `password`) VALUES ('$phone','$passward')";
	mysqli_query($con,$qurey)or die(mysqli_error($con));

}


mysqli_close($con);
?>