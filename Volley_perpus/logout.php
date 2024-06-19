<?php
include 'koneksi.php';
require "Database.php";
$db = new Database();

if ($db->dbConnect()) {
    $status = "login"; // Status to search for and update
    $updateStatus = "logout"; // New status to update
    
    $result = $db->getUserIDByStatus($status); // Retrieve kd_user with the specified status
    if ($result) {
        $row = mysqli_fetch_assoc($result);
        $kd_user = $row['kd_user'];

        // Update status_login for the kd_user to "logout"
        $updateQuery = "UPDATE user SET status_login='".$updateStatus."' WHERE kd_user='".$kd_user."'";
        $updateResult = mysqli_query($db->connect, $updateQuery);
        if ($updateResult) {
            $response = array("message" => "Logout Success");
        } else {
            $response = array("message" => "Error: Failed to update status_login");
        }
    } else {
        $response = array("message" => "Error: No user found with the specified status");
    }
} else {
    $response = array("message" => "Error: Database Connection");
}

echo json_encode($response);
mysqli_close($conn);
?>
