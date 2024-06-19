<?php
include 'koneksi.php';

$kode = $_GET["kd_peminjaman"];

$result = mysqli_query($conn, "SELECT tgl_pinjam, tgl_kembali FROM peminjaman WHERE kd_peminjaman = '$kode'");
if (mysqli_num_rows($result) > 0) {
    $items = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $items[] = $row;
    }

    $response['message'] = "History pengembalian tersedia";
    $response['data'] = $items;
} else {
    $response['message'] = "Tidak ada data";
}

echo json_encode($response);
?>