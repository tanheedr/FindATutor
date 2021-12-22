<?php

//require "login.php";
require "connection.php";
header('content-type: application/json');

session_start();

//echo $_SESSION['ID'][0];
$TutorID = $_POST["ID"];

$query = "SELECT Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE TutorID = '$TutorID' AND TutorID = ID";
$res = mysqli_query($connection, $query);
$json_data = array();

while($row = mysqli_fetch_assoc($res)){
    $json_data[] = $row;
}
echo json_encode($json_data);

?>