<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$id = $_POST['id'];
$collect_phone = $_POST['collect_phone'];



$qurey = " UPDATE food1 SET bln_collect = '1' WHERE food1.id = $id";
$qurey1 = "INSERT INTO `collect`(`collect_phone`, `collect_id`) VALUES ('$collect_phone','$id')";


mysqli_query($con,$qurey)or die(mysqli_error($con));
mysqli_query($con,$qurey1)or die(mysqli_error($con));

mysqli_close($con);
?>