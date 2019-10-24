<?php

//圖片位置
$target_dir = "/storage/ssd4/010/9737010/public_html/connect/images";


if(!file_exists($target_dir)){
	mkdir($target_dir,0777,true);
}
$tr = "/". rand() . "_". time() . ".jpeg";
$tar = $target_dir . $tr;



$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");



$food_type = $_POST['food_type'];
$food_name = $_POST['food_name'];
$food_area = $_POST['food_area'];
$food_address = $_POST['food_address'];

$expiry_date = $_POST['expiry_date'];
$pickup_time = $_POST['pickup_time'];
$detail = $_POST['detail'];
$phone = $_POST['phone'];
$image = $_POST["image"];
$boolean = $_POST["boolean"];
$bln_collect = $_POST["bln_collect"];

file_put_contents($tar,base64_decode($image));

$image = $tr;



$qurey = "INSERT INTO `food1`(`food_type`, `food_name`, `food_area`, `food_address`, `expiry_date`, `pickup_time`, `detail`, `phone`,`image`,`boolean`,`bln_collect`,`bln_complete`) VALUES ('$food_type','$food_name','$food_area','$food_address','$expiry_date','$pickup_time','$detail','$phone','$image','$boolean','$bln_collect','0')";

mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>