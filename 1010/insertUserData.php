<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$phone = $_POST['phone'];
$name = $_POST['name'];
$sex = $_POST['sex'];
$rating = $_POST['rating'];


$qurey = " INSERT INTO `userdata`(`phone`, `name`, `sex`, `rating`) VALUES ('$phone','$name','$sex','$rating')";

mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>