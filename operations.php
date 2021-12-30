<?php

class Operations{
    private $connection;

    function __construct(){
        require_once dirname(__FILE__).'/connection.php';

        $db = new Connect();

        $this -> connection = $db -> connect();
    }

    public function registerUser($accountType, $firstName, $surname, $email, $password, $confirmPassword){

        $hashedPassword = hash('sha1', $password);
    
        if ($accountType == "Parent"){
            $accountType = 1;
        }
        else{
            $accountType = 2;
        }

        if(strlen($password) > 64 || strlen($password) < 6){
            echo "Password must be between 6-64 characters";
        }
        else if($password !== $confirmPassword){
            echo "Passwords do not match";
        }else if(filter_var($email, FILTER_VALIDATE_EMAIL) == false){
            echo "Invalid email";
        }
        else if (strlen($firstName) > 64 || strlen($firstName) < 2 || !ctype_alpha($firstName)){
            echo "Enter valid first name";
        }
        else if (strlen($surname) > 64 || strlen($surname) < 2 || !ctype_alpha($surname)){
            echo "Enter valid surname";
        }else{
            if($this -> doesUserExist($email)){
                echo "This email is already registered";
            }else{
                $statement = $this -> connection -> prepare("INSERT INTO accounts (`AccountType`,`FirstName`,`Surname`,`Email`,`Password`) VALUES (?,?,?,?,?)");
                $statement -> bind_param("issss", $accountType, $firstName, $surname, $email, $hashedPassword);
                if($statement -> execute()){
                    echo "Successfully Registered";
                }else{
                    echo "Registration Error";
                }
            }
        }
    }

    private function doesUserExist($email){
        $statement = $this -> connection -> prepare("SELECT ID FROM accounts WHERE email = ?");
        $statement -> bind_param("s", $email);
        $statement -> execute();
        $statement -> store_result();
        return $statement -> num_rows > 0;
    }

    public function loginUser($email, $password){
        $hashedPassword = hash('sha1', $password);
        $statement = $this -> connection -> prepare("SELECT ID FROM accounts WHERE Email = ? AND Password = ?");
        $statement -> bind_param("ss", $email, $hashedPassword);
        $statement -> execute();
        $statement -> store_result();
        return $statement -> num_rows > 0;
    }

    public function getUserData($email){
        $statement = $this -> connection -> prepare("SELECT ID, AccountType FROM accounts WHERE Email = ?");
        $statement -> bind_param("s", $email);
        $statement -> execute();
        return $statement -> get_result() -> fetch_assoc();
    }

    public function getTutorData($ID){
        $statement = $this -> connection -> prepare("SELECT Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE TutorID = ? AND TutorID = ID");
        $statement -> bind_param("i", $ID);
        $statement -> execute();
        return $statement -> get_result() -> fetch_assoc();
    }

    public function editTutorData($ID, $photo, $subjects, $hourlyCost, $qualifications, $description){
        if(preg_match("/^[0-9]+(?:\.[0-9]{1,2})?$/", $hourlyCost) !== 1){
            echo "Not a valid price";
        }else{
            $statement = $this -> connection -> prepare("UPDATE accounts, tutordescription SET Photo = ?, Subjects = ?, HourlyCost = ?, Qualifications = ?, Description = ? WHERE TutorID = ? AND TutorID = ID");
            $statement -> bind_param("sssssi", $photo, $subjects, $hourlyCost, $qualifications, $description, $ID);
            if($statement -> execute()){
                echo "Successfully Updated";
            }else{
                echo "Update Error";
            }
        }
    }

    public function getParentData($ID){
        $statement = $this -> connection -> prepare("SELECT Photo, FirstName, Surname FROM accounts WHERE ID = ?");
        $statement -> bind_param("i", $ID);
        $statement -> execute();
        return $statement -> get_result() -> fetch_assoc();
    }

    public function editParentData($ID, $photo){
        $statement = $this -> connection -> prepare("UPDATE accounts SET Photo = ? WHERE ID = ?");
        $statement -> bind_param("si", $photo, $ID);
        if($statement -> execute()){
            echo "Successfully Updated";
        }else{
            echo "Update Error";
        }
    }

    public function getTutorsWithSearch($ID, $subject){
        $statement = $this -> connection -> prepare("SELECT ID, FirstName, Surname, Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE Subjects LIKE CONCAT('%', ?, '%') AND TutorID = ID AND TutorID != ?");
        $statement -> bind_param("si", $subject, $ID);
        $statement -> execute();
        $result = $statement -> get_result();
        $tutors = array();
        while($row = $result -> fetch_assoc()){
            array_push($tutors, $row);
        }
        return $tutors;
    }

    public function getTutorsWithoutSearch($ID){
        $statement = $this -> connection -> prepare("SELECT ID, FirstName, Surname, Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE TutorID = ID AND TutorID != ?");
        $statement -> bind_param("i", $ID);
        $statement -> execute();
        $result = $statement -> get_result();
        $tutors = array();
        while($row = $result -> fetch_assoc()){
            array_push($tutors, $row);
        }
        return $tutors;
    }

    public function getAllMessages($SenderID){
        $statement = $this -> connection -> prepare("SELECT a.ID, a.FirstName, a.Surname, a.Photo, m.Message, m.Timestamp
        FROM accounts a, messages m
        WHERE ((SenderID = ? AND ReceiverID = ID) OR (ReceiverID = ? AND SenderID = ID)) AND (LEAST(SenderID, ReceiverID), GREATEST(SenderID, ReceiverID), Timestamp)
        IN (SELECT LEAST(SenderID, ReceiverID) AS x, GREATEST(SenderID, ReceiverID) as y,
            MAX(Timestamp) AS Timestamp
            FROM messages
            GROUP BY x, y)
        ORDER BY Timestamp DESC");
        $statement -> bind_param("ii", $SenderID, $SenderID);
        $statement -> execute();
        $result = $statement -> get_result();
        $users = array();
        while($row = $result -> fetch_assoc()){
            array_push($users, $row);
        }
        return $users;
    }

    public function getChat($SenderID, $ReceiverID){
        $statement = $this -> connection -> prepare("SELECT SenderID, Message, Timestamp FROM messages WHERE (SenderID = ? AND ReceiverID = ?) OR (SenderID = ? AND ReceiverID = ?)");
        $statement -> bind_param("iiii", $SenderID, $ReceiverID, $ReceiverID, $SenderID);
        $statement -> execute();
        $result = $statement -> get_result();
        $messages = array();
        while($row = $result -> fetch_assoc()){
            array_push($messages, $row);
        }
        return $messages;
    }

    public function sendMessage($SenderID, $ReceiverID, $message){
        $statement = $this -> connection -> prepare("INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `Message`, `Timestamp`) VALUES (NULL, ?, ?, ?, UTC_TIMESTAMP())");
        $statement -> bind_param("iis", $SenderID, $ReceiverID, $message);
        if($statement -> execute()){
            echo "Message Sent";
        }else{
            echo "Error Sending Message";
        }
    }
}

?>