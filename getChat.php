<?php

require "connection.php";
header('content-type: application/json');

session_start();

//echo $_SESSION['ID'][0];
$SenderID = 1;
$ReceiverID = 3;

$query = "SELECT Message, Timestamp FROM messages WHERE (SenderID = '$SenderID' AND ReceiverID = '$ReceiverID') OR (SenderID = '$ReceiverID' AND ReceiverID = '$SenderID')";
$res = mysqli_query($connection, $query);
$json_data = array();

while($row = mysqli_fetch_assoc($res)){
    $json_data[] = $row;
}
echo json_encode($json_data);

?>