<?php

require "connection.php";

//INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `Message`, `Timestamp`) VALUES (NULL, '1', '3', 'this is a new message', UTC_TIMESTAMP());

session_start();

//echo $_SESSION['ID'][0];
$SenderID = 1;

//Fix the query
$query = "SELECT a.FirstName, a.Surname, a.Photo, m.Message, m.Timestamp FROM accounts a, messages m WHERE a.ID = m.ReceiverID AND m.Timestamp = (SELECT m2.Message FROM messages m2 WHERE m2.ReceiverID = a.ID ORDER BY Timestamp DESC LIMIT 1)";
$result = mysqli_query($connection, $query);
$response = array();
while($row = mysqli_fetch_assoc($result)){
    array_push($response,
    array(
        'FirstName' => $row['FirstName'],
        'Surname' => $row['Surname'],
        'Photo' => $row['Photo'],
        'Message' => $row['Message'],
        'Timestamp' => $row['Timestamp'])
    );
}
echo json_encode($response);

mysqli_close($connection);

?>