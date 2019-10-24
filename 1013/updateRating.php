<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$phone = $_POST['phone'];
$rating = $_POST['rating'];
$totalRating = $_POST['totalRating'];
$numberOfVoters = $_POST['numberOfVoters'];





$qurey = " UPDATE userdata SET rating = $rating , numberOfVoters = $numberOfVoters , totalRating = $totalRating WHERE userdata.phone = $phone";


mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>













UPDATE `userdata` SET `rating` = '5' WHERE `userdata`.`phone` = '0988777666';