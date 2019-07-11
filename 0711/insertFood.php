<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$food_type = $_POST['food_type'];
$food_name = $_POST['food_name'];
$food_area = $_POST['food_area'];
$food_address = $_POST['food_address'];

$expiry_date = $_POST['expiry_date'];
$pickup_time = $_POST['pickup_time'];
$detail = $_POST['detail'];
$phone = $_POST['phone'];


$qurey = "INSERT INTO `food1`(`food_type`, `food_name`, `food_area`, `food_address`, `expiry_date`, `pickup_time`, `detail`, `phone`) VALUES ('$food_type','$food_name','$food_area','$food_address','$expiry_date','$pickup_time','$detail','$phone')";

mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>