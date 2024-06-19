<?php
    include 'koneksi.php';

    $result = mysqli_query($conn, "SELECT * FROM peminjaman");
    //SELECT * FROM peminjaman LEFT JOIN buku ON peminjaman.kd_peminjaman = buku.kd_buku
    if(mysqli_num_rows($result) > 0) {
        $items = array();
        while($row = mysqli_fetch_object($result)){
            array_push($items, $row);
        }

        $response['message'] = "History pengembalian tersedia";
        $response['data'] = $items;
    } else {
        $response['message'] = "Tidak ada data";
    }

    echo json_encode($response);
?>