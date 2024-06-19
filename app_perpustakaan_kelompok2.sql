-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Bulan Mei 2023 pada 22.30
-- Versi server: 10.4.21-MariaDB
-- Versi PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `app_perpustakaan_kelompok2`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `buku`
--

CREATE TABLE `buku` (
  `kd_buku` int(11) NOT NULL,
  `image` text NOT NULL,
  `judul` varchar(200) NOT NULL,
  `genre` text NOT NULL,
  `penerbit` varchar(200) NOT NULL,
  `pengarang` varchar(200) NOT NULL,
  `tahun_terbit` int(4) NOT NULL,
  `no_rak` int(11) NOT NULL,
  `isbn` varchar(30) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `buku`
--

INSERT INTO `buku` (`kd_buku`, `image`, `judul`, `genre`, `penerbit`, `pengarang`, `tahun_terbit`, `no_rak`, `isbn`, `status`) VALUES
(4, 'images/17-05-2023-105357-1146.jpg', 'Uzumaki', 'Horror', 'Gramedia', 'Junji Ito', 2021, 3, '123456789123', 'Not Available'),
(5, 'images/17-05-2023-110835-6116.jpg', 'Meong : The Descent', 'Fantasi', 'Gramedia', 'Winsen Wiradinata', 2023, 3, '129383589384', 'Available'),
(7, 'images/18-05-2023-013450-405.jpg', 'The Real and the fakes', 'Fantasi', 'Mizan Pustaka', 'Koji Shimazu', 2019, 3, '9282839427282', 'Available'),
(8, 'images/18-05-2023-015020-922.jpg', 'Who will fix me now', 'Horror', 'BMTH', 'Oliver Sykes', 2017, 5, '3424234', 'Available'),
(10, 'images/18-05-2023-090324-5043.jpg', 'Avalanche', 'Horror', 'BMTH', 'Mat Nicholls', 2017, 6, '9839483', 'Available');

-- --------------------------------------------------------

--
-- Struktur dari tabel `peminjaman`
--

CREATE TABLE `peminjaman` (
  `kd_peminjaman` int(11) NOT NULL,
  `nama_peminjam` varchar(50) NOT NULL,
  `no_telp` int(11) NOT NULL,
  `alamat` text NOT NULL,
  `tgl_pinjam` date NOT NULL DEFAULT current_timestamp(),
  `kd_buku` int(11) NOT NULL,
  `tgl_kembali` date DEFAULT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `peminjaman`
--

INSERT INTO `peminjaman` (`kd_peminjaman`, `nama_peminjam`, `no_telp`, `alamat`, `tgl_pinjam`, `kd_buku`, `tgl_kembali`, `status`) VALUES
(6, 'Lee Malia', 90876, 'JL kebo', '2023-05-18', 5, '2023-05-19', 'Returned'),
(7, 'WInsen', 878655, 'JL oke', '2023-05-18', 4, '2023-05-18', 'Returned'),
(8, 'Agnes', 87675, 'JL hehe', '2023-05-18', 8, NULL, 'Returned'),
(9, 'Jordan Fish', 837484, 'JL kaki', '2023-05-18', 8, '2023-05-18', 'Returned'),
(12, 'Oliver Sykes', 839834, 'JL bahagia', '2023-05-18', 4, '2018-05-23', 'Returned'),
(14, 'Naruto', 3243434, 'konoha', '2023-05-18', 4, '2023-05-18', 'Returned'),
(16, 'Chelsea Smile', 3478374, 'not your business', '2023-05-18', 8, '2023-05-18', 'Returned'),
(17, 'Sogeking', 887878, 'Soge Shima', '2023-05-18', 10, '2023-05-18', 'Returned'),
(18, 'Luffy', 8347374, 'Grand line', '2023-05-18', 8, '2023-05-18', 'Returned'),
(19, 'Oliver Sykes', 76544578, 'JL batu', '2023-05-18', 10, '2023-05-19', 'Returned'),
(20, 'Agnes', 87676, 'JL hehe', '2023-05-18', 5, '2023-05-18', 'Returned'),
(21, 'Jordan Fish', 8347834, 'sheffield', '2023-05-18', 4, '2023-05-18', 'Returned'),
(22, 'Gerry', 384387483, 'JL apa ya', '2023-05-19', 4, '2023-05-19', 'Returned'),
(23, 'Winsen', 12312313, 'asdasd', '2023-05-19', 5, '2023-05-19', 'Returned'),
(24, 'Winsen', 123123123, 'asdasd', '2023-05-18', 4, '2023-05-19', 'Returned'),
(25, 'Agnes', 2147483647, 'asdasd', '2023-05-18', 7, '2023-05-19', 'Returned'),
(26, 'Janes', 135, 'asdasdas', '2023-05-18', 5, '2023-05-20', 'Returned'),
(29, 'Agnes', 2147483647, 'JL. Grahahaha', '2023-05-19', 4, '2023-05-20', 'Returned'),
(30, 'Winsen', 2147483647, 'JL. Regensihihi', '2023-05-18', 5, '2023-05-20', 'Returned'),
(31, 'Janes', 2147483647, 'JL. Paslamahaha', '2023-05-19', 7, '2023-05-20', 'Returned'),
(32, 'Robert', 2147483647, 'JL. Keramat Raya', '2023-05-17', 10, '2023-05-20', 'Returned'),
(33, 'Jason', 812371273, 'JL. Woro wiri', '2023-05-20', 4, '2023-05-20', 'Returned'),
(34, 'Naruto', 812938123, 'JL. Melati Perak', '2023-05-14', 4, '2023-05-20', 'Returned'),
(35, 'Janes', 912731283, 'aksdkaksd', '2023-05-20', 4, '2023-05-20', 'Returned'),
(36, 'Winsen', 12313213, 'aasdasd', '2023-05-19', 4, '2023-05-20', 'Returned'),
(37, 'Janes', 2147483647, 'Keramat', '2023-05-18', 4, NULL, 'Borrowing');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `kd_user` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `nama_lengkap` text NOT NULL,
  `email` varchar(300) NOT NULL,
  `password` text NOT NULL,
  `status_login` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`kd_user`, `username`, `nama_lengkap`, `email`, `password`, `status_login`) VALUES
(7, 'Janes', 'ya itu itu', 'janes@gmail.com', '$2y$10$IMBkVWCA/eNVEr/Wn3DiH.metZz5as38eL/jqmzwiHV3559pQ0f1G', 'login');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`kd_buku`);

--
-- Indeks untuk tabel `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`kd_peminjaman`),
  ADD KEY `FK_BUKU` (`kd_buku`) USING BTREE;

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`kd_user`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `buku`
--
ALTER TABLE `buku`
  MODIFY `kd_buku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `peminjaman`
--
ALTER TABLE `peminjaman`
  MODIFY `kd_peminjaman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `kd_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `FK_BUKU` FOREIGN KEY (`kd_buku`) REFERENCES `buku` (`kd_buku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
