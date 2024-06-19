<?php
    include 'koneksi.php';

    if (isset($_POST['tgl_pinjam'])) {
        $tgl_pinjam = $_POST['tgl_pinjam'];

        $result = mysqli_query($conn, "SELECT * FROM peminjaman WHERE tgl_pinjam='$tgl_pinjam'");

        if(mysqli_num_rows($result) > 0) {
            $items = array();
            while($row = mysqli_fetch_object($result)){
                array_push($items, $row);
            }
            $response['message'] = "Berhasil search berdasarkan tanggal";
            $response['data'] = $items;
        } else {
            $response['message'] = "Gagal search";
        }
    } else {
        $response['message'] = "Parameter 'tgl_pinjam' tidak ditemukan";
    }

    echo json_encode($response);
?>