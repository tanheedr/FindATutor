<?php

class Operations{
    private $connection;

    function __construct(){
        require_once dirname(__FILE__).'/connection.php';

        $db = new Connect();

        $this -> connection = $db -> connect();
    }

    public function registerUser($accountType, $firstName, $surname, $email, $password, $confirmPassword){

        $hashedPassword = hash('sha256', $password);
    
        if ($accountType == "Parent"){
            $accountType = 1;
        }
        elseif($accountType == "Tutor"){
            $accountType = 2;
        }else{
            return "Invalid Account Type";
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
                $verificationKey = hash('sha256', time().$email);
                $statement = $this -> connection -> prepare("INSERT INTO accounts (`AccountType`,`FirstName`,`Surname`,`Email`,`Password`, `VerificationKey`, `ExpiryDate`) VALUES (?,?,?,?,?,?, UTC_TIMESTAMP() + INTERVAL 30 MINUTE)");
                $statement -> bind_param("isssss", $accountType, $firstName, $surname, $email, $hashedPassword, $verificationKey);
                if($statement -> execute()){
                    echo "Successfully Registered\n";
                    $this -> emailVerification($email, $verificationKey);
                    if($accountType == 2){
                        $sqlGetTutorID = $this -> getTutorID($email);
                        $TutorID = substr(json_encode($sqlGetTutorID), 6, -1);
                        $statementTwo = $this -> connection -> prepare("INSERT INTO `tutordescription` (`TutorID`, `Subjects`, `HourlyCost`, `Qualifications`, `Description`, `Verified`) VALUES (?, NULL, NULL, NULL, NULL, '0')");
                        $statementTwo -> bind_param("i", $TutorID);
                        $statementTwo -> execute();
                    }
                }else{
                    echo "Registration Error";
                }
            }
        }
    }

    private function doesUserExist($email){
        $statement = $this -> connection -> prepare("SELECT ID FROM accounts WHERE Email = ?");
        $statement -> bind_param("s", $email);
        $statement -> execute();
        $statement -> store_result();
        return $statement -> num_rows > 0;
    }

    private function getTutorID($email){
        $statement = $this -> connection -> prepare("SELECT ID FROM accounts WHERE Email = ?");
        $statement -> bind_param("s", $email);
        $statement -> execute();
        return $statement -> get_result() -> fetch_assoc();
    }

    private function emailVerification($email, $verificationKey){
        $to = $email;
        $subject = 'Email Verification';
        $message = "<a href = 'http://localhost/FindATutor/verify.php?VerificationKey=$verificationKey'>Verify Email Address</a>";
        $headers = 'From: findatutor7@gmail.com'."\r\n".
                    'MIME-Version: 1.0'."\r\n".
                    'Content-type: text/html; charset=utf-8';

        if(mail($to, $subject, $message, $headers)){
            echo "Please head to your email to verify your account";
        }else{
            echo "Error sending email";
        }
    }

    public function verify($verificationKey){
        $statement = $this -> connection -> prepare("SELECT DISTINCT VerificationKey, Verified FROM accounts WHERE VerificationKey = ? AND UTC_TIMESTAMP() < ExpiryDate AND Verified = 0");
        $statement -> bind_param("s", $verificationKey);
        $statement -> execute();
        $statement -> store_result();
        $result = $statement -> num_rows == 1;
        if($result){
            $statement = $this -> connection -> prepare("UPDATE accounts SET Verified = 1, ExpiryDate = NULL WHERE VerificationKey = ? LIMIT 1");
            $statement -> bind_param("s", $verificationKey);
            if($statement -> execute()){
                return "Your account has been verified. You may now login.";
            }else{
                return "Verification Error";
            }
        }else{
            return "This account is already verified or invalid verification key";
        }
    }

    public function forgetPassword($email){
        $to = $email;
        $subject = 'Reset Password';
        $message = "<a href = 'http://localhost/FindATutor/changePassword.php?Email=$email'>Reset your password here</a>";
        $headers = 'From: findatutor7@gmail.com'."\r\n".
                    'MIME-Version: 1.0'."\r\n".
                    'Content-type: text/html; charset=utf-8';

        if(mail($to, $subject, $message, $headers)){
            echo "Please head to your email to reset your password";
            $statement = $this -> connection -> prepare("UPDATE accounts SET ExpiryDate = UTC_TIMESTAMP() + INTERVAL 30 MINUTE WHERE Email = ? LIMIT 1");
            $statement -> bind_param("s", $email);
            $statement -> execute();
        }else{
            echo "Error sending email";
            ini_set('display_errors', 1);
            ini_set('display_startup_errors', 1);
            error_reporting(E_ALL);
        }
    }

    public function changePassword($email, $password, $confirmPassword){
        $statement = $this -> connection -> prepare("SELECT DISTINCT ExpiryDate FROM accounts WHERE Email = ? AND UTC_TIMESTAMP() < ExpiryDate");
        $statement -> bind_param("s", $email);
        $statement -> execute();
        $statement -> store_result();
        $result = $statement -> num_rows == 1;
        if($result){
            if(strlen($password) > 64 || strlen($password) < 6){
                echo "Password must be between 6-64 characters";
            }
            else if($password !== $confirmPassword){
                echo "Passwords do not match";
            }else{
                $hashedPassword = hash('sha256', $password);
                $statement = $this -> connection -> prepare("UPDATE accounts SET Password = ? WHERE Email = ?");
                $statement -> bind_param("ss", $hashedPassword, $email);
                if($statement -> execute()){
                    header("Location: changedPassword.php");
                    $statement = $this -> connection -> prepare("UPDATE accounts SET ExpiryDate = NULL WHERE Email = ? LIMIT 1");
                    $statement -> bind_param("s", $email);
                    $statement -> execute();
                }else{
                    echo "Error Changing Password";
                }
            }
        }else{
            $statement = $this -> connection -> prepare("UPDATE accounts SET ExpiryDate = NULL WHERE Email = ? LIMIT 1");
            $statement -> bind_param("s", $email);
            $statement -> execute();
            header("Location: linkExpired.php");
        }
    }

    public function loginUser($email, $password){
        $hashedPassword = hash('sha256', $password);
        $statement = $this -> connection -> prepare("SELECT ID FROM accounts WHERE Email = ? AND Password = ? AND accounts.Verified = 1");
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
        $statement = $this -> connection -> prepare("SELECT ID, FirstName, Surname, Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE Subjects LIKE CONCAT('%', ?, '%') AND TutorID = ID AND TutorID != ? AND tutordescription.Verified = 1");
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
        $statement = $this -> connection -> prepare("SELECT ID, FirstName, Surname, Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE TutorID = ID AND TutorID != ? AND Verified = 1");
        $statement -> bind_param("i", $ID);
        $statement -> execute();
        $result = $statement -> get_result();
        $tutors = array();
        while($row = $result -> fetch_assoc()){
            array_push($tutors, $row);
        }
        return $tutors;
    }

    public function getUsers($SenderID){
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

    public function getNames($SenderID){
        $statement = $this -> connection -> prepare("SELECT a.ID, a.FirstName, a.Surname
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

    public function sendMessage($SenderID, $ReceiverID, $message){
        $statement = $this -> connection -> prepare("INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `Message`, `Timestamp`) VALUES (NULL, ?, ?, ?, UTC_TIMESTAMP())");
        $statement -> bind_param("iis", $SenderID, $ReceiverID, $message);
        if($statement -> execute()){
            echo "Message Sent";
        }else{
            echo "Error Sending Message";
        }
    }

    public function setSession($TutorID, $ParentID, $startTime, $endTime){
        $statement = $this -> connection -> prepare("INSERT INTO `calendar` (`SessionID`, `TutorID`, `ParentID`, `StartTime`, `EndTime`) VALUES (NULL, ?, ?, ?, ?)");
        $statement -> bind_param("iiss", $TutorID, $ParentID, $startTime, $endTime);
        if($statement -> execute()){
            echo "Session Saved";
        }else{
            echo "Error Saving Session";
        }
    }

    public function getSessions($ID, $date){
        $statement = $this -> connection -> prepare("SELECT ID, FirstName, Surname, StartTime, EndTime FROM accounts a, calendar c WHERE StartTime LIKE CONCAT('%', ?, '%') AND ((c.TutorID = ? AND a.ID = c.ParentID) OR (c.ParentID = ? AND a.ID = c.TutorID))");
        $statement -> bind_param("sii", $date, $ID, $ID);
        $statement -> execute();
        $result = $statement -> get_result();
        $sessions = array();
        while($row = $result -> fetch_assoc()){
            array_push($sessions, $row);
        }
        return $sessions;
    }
}

?>