<?php


// connect to the server
$conn = new mysqli("localhost","boshencu_tester","mysqlpassword","boshencu_test1");
//$mysqlpassword= "fuckyousql1!"

// Check connection
if ($conn->connect_error) {

    die("Connection failed: " . $conn->connect_error);

} 

//after connection, retrieve data
// array for JSON response
$response = array();

// check for post data
if (isset($_POST["user"]) && isset($_POST["id"]) && isset($_POST["lat"]) && isset($_POST["long"]) && isset($_POST["ispub"])) {

    $user = $_POST['user'];
    $id = $_POST['id'];
    $lat = $_POST['lat'];
    $long = $_POST['long'];
    $ispub = $_POST['ispub'];

    $result = mysqli_query($conn,"INSERT INTO LobsterTraps(UserId, TrapId, Latitute, Longitute, IsPublic)
     VALUES('$user', '$id', '$lat', '$long', '$ispub')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Successfully Logged.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

$conn->close();

?>