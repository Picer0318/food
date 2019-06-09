<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$phone = $_POST['phone'];
$passward = $_POST['password'];


$qurey = " INSERT INTO `user`(`phone`, `password`) VALUES ('$phone','$passward')";

mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>