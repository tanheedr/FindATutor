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

    private function doesUserExist($email){
        $statement = $this -> connection -> prepare("SELECT ID FROM accounts WHERE email = ?");
        $statement -> bind_param("s", $email);
        $statement -> execute();
        $statement -> store_result();
        return $statement -> num_rows > 0;
    }

}

?>