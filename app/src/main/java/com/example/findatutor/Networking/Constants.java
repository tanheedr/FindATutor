package com.example.findatutor.Networking;

public class Constants {

    /*
    Stores required URL's as attributes in one class
    BASE_URL specifies the path file
    The other URL's specify the file
    */

    public static final String BASE_URL = "http://192.168.0.19/FindATutor/";
    public static final String LOGIN_URL = BASE_URL + "login.php";
    public static final String REGISTER_URL = BASE_URL + "register.php";
    public static final String FORGET_PASSWORD_URL = BASE_URL + "forgetPassword.php";
    public static final String TUTOR_PROFILE_URL = BASE_URL + "tutorData.php";
    public static final String EDIT_TUTOR_PROFILE_URL = BASE_URL + "editTutorData.php";
    public static final String PARENT_PROFILE_URL = BASE_URL + "parentData.php";
    public static final String EDIT_PARENT_PROFILE_URL = BASE_URL + "editParentData.php";
    public static final String SEND_MESSAGE_URL = BASE_URL + "sendMessage.php";
    public static final String SET_SESSION_URL = BASE_URL + "setSession.php";

}
