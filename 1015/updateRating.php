<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$id = $_POST['id'];
$phone = $_POST['phone'];
$rating = $_POST['rating'];
$totalRating = $_POST['totalRating'];
$numberOfVoters = $_POST['numberOfVoters'];





$qurey = " UPDATE userdata SET rating = $rating , numberOfVoters = $numberOfVoters , totalRating = $totalRating WHERE userdata.phone = $phone";
$qurey1 = " UPDATE food1 SET bln_complete = '2'  WHERE food1.id = $id";



mysqli_query($con,$qurey)or die(mysqli_error($con));
mysqli_query($con,$qurey1)or die(mysqli_error($con));


mysqli_close($con);
?>













UPDATE `userdata` SET `rating` = '5' WHERE `userdata`.`phone` = '0988777666';