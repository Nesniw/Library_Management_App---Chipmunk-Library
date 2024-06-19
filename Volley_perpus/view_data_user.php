<?php
    include 'koneksi.php';


    $result = mysqli_query($conn, "SELECT * FROM user WHERE status_login = 'login'");
    if(mysqli_num_rows($result) > 0) {
        $items = array();
        while($row = mysqli_fetch_object($result)){
            array_push($items, $row);
        }

        $response['message'] = "Data User";
        $response['datauser'] = $items;
    } else {
        $response['message'] = "Data User tidak ditemukan";
    }

    echo json_encode($response);
?>