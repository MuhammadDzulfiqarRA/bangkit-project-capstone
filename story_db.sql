-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 08, 2024 at 07:18 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `story_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `choices`
--

CREATE TABLE `choices` (
  `id` bigint(20) NOT NULL,
  `option` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `choices`
--

INSERT INTO `choices` (`id`, `option`, `created_at`, `updated_at`) VALUES
(1, 'Dewi', '2024-06-02 15:32:47', '2024-06-02 15:32:47'),
(2, 'Tumbai', '2024-06-02 15:32:47', '2024-06-02 15:32:47'),
(3, 'Sinta', '2024-06-02 15:32:47', '2024-06-02 15:32:47'),
(4, 'Melati', '2024-06-02 15:32:47', '2024-06-02 15:32:47'),
(5, 'Seorang pemuda dari desa sebelah', '2024-06-02 15:34:42', '2024-06-02 15:34:42'),
(6, 'Seorang pemuda yang sakti dari daerah aliran Sungai Barito\n', '2024-06-02 15:34:42', '2024-06-02 15:34:42'),
(7, 'Seorang pedagang yang kaya\n', '2024-06-02 15:34:42', '2024-06-02 15:34:42'),
(8, 'Seorang petani yang rajin \n', '2024-06-02 15:34:42', '2024-06-02 15:34:42'),
(9, 'Pemuda harus tampan\n', '2024-06-02 15:36:03', '2024-06-02 15:36:03'),
(10, 'Pemuda harus kaya\n', '2024-06-02 15:36:03', '2024-06-02 15:36:03'),
(11, 'Pemuda harus bisa mengubah air tawar menjadi air asin\n', '2024-06-02 15:36:03', '2024-06-02 15:36:03'),
(12, 'Pemuda harus berasal dari desa yang sama dengan mereka\n', '2024-06-02 15:36:03', '2024-06-02 15:36:03'),
(13, 'Sangkuriang', '2024-06-08 03:16:16', '2024-06-08 03:16:21'),
(14, 'Tumang', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(15, 'Sumbi', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(16, 'Dayang', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(17, 'Dia tertawa\n', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(18, 'Dia terluka di kepala\n', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(19, 'Dia menangkap sendok itu', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(20, 'Dia pergi bermain Jawaban yang benar:', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(21, 'Sangkuriang', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(22, 'Dayang Sumbi', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(23, 'Tumang', '2024-06-08 03:16:16', '2024-06-08 03:16:16'),
(24, 'Kancil', '2024-06-08 03:16:16', '2024-06-08 03:16:16');

-- --------------------------------------------------------

--
-- Table structure for table `missions`
--

CREATE TABLE `missions` (
  `id` bigint(20) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `condition` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `missions`
--

INSERT INTO `missions` (`id`, `title`, `condition`, `created_at`, `updated_at`) VALUES
(1, 'Baca 5 Cerita', 5, '2024-06-08 00:29:39', '2024-06-08 00:46:28'),
(2, 'Baca 10 Cerita', 5, '2024-06-08 00:29:39', '2024-06-08 00:46:28'),
(3, 'Baca 15 cerita', 5, '2024-06-08 00:29:39', '2024-06-08 00:46:28'),
(4, 'Baca 25 cerita', 5, '2024-06-08 00:29:39', '2024-06-08 00:46:28'),
(5, 'Baca 35 Cerita', 5, '2024-06-08 00:29:39', '2024-06-08 00:46:28'),
(6, 'Baca 50 Cerita', 5, '2024-06-08 00:29:39', '2024-06-08 00:46:28');

-- --------------------------------------------------------

--
-- Table structure for table `quizzes`
--

CREATE TABLE `quizzes` (
  `id` bigint(20) NOT NULL,
  `question` text DEFAULT NULL,
  `answer` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `quizzes`
--

INSERT INTO `quizzes` (`id`, `question`, `answer`, `created_at`, `updated_at`) VALUES
(1, 'Siapakah nama putri yang cantik jelita dalam cerita ini?\n', 'Tumbai', '2024-06-02 15:20:15', '2024-06-02 15:20:15'),
(2, 'Siapakah yang berhasil memenuhi syarat Tumbai?\n', 'Seorang pemuda yang sakti dari daerah aliran Sungai Barito\n', '2024-06-02 15:21:38', '2024-06-02 15:21:38'),
(3, 'Apa yang menjadi syarat Tumbai untuk menerima lamaran pemuda?\n', 'Pemuda harus bisa mengubah air tawar menjadi air asin\n', '2024-06-02 15:22:10', '2024-06-02 15:22:10'),
(4, 'Siapa nama anjing yang menemani Dayang Sumbi di hutan?\n', 'Tumang', '2024-06-08 03:13:17', '2024-06-08 03:13:17'),
(5, 'Apa yang terjadi pada Sangkuriang ketika ibunya melemparkan sendoknya?\n', 'Dia terluka di kepala', '2024-06-08 03:13:17', '2024-06-08 03:13:17'),
(6, 'Siapa tokoh utama dalam cerita ini?\n', 'Sangkuriang', '2024-06-08 03:13:17', '2024-06-08 03:13:17');

-- --------------------------------------------------------

--
-- Table structure for table `quiz_choice_links`
--

CREATE TABLE `quiz_choice_links` (
  `quiz_id` bigint(20) NOT NULL,
  `choice_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `quiz_choice_links`
--

INSERT INTO `quiz_choice_links` (`quiz_id`, `choice_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(3, 9),
(3, 10),
(3, 11),
(3, 12),
(4, 13),
(4, 14),
(4, 15),
(4, 16),
(5, 17),
(5, 18),
(5, 19),
(5, 20),
(6, 21),
(6, 22),
(6, 23),
(6, 24);

-- --------------------------------------------------------

--
-- Table structure for table `stories`
--

CREATE TABLE `stories` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `overview` text DEFAULT NULL,
  `origin` varchar(100) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stories`
--

INSERT INTO `stories` (`id`, `title`, `overview`, `origin`, `author`, `genre`, `thumbnail`, `created_at`, `updated_at`) VALUES
(1, 'Legenda Sumber Garam Sepang', 'Permintaan Tumbai kepada pemuda yang ingin menikahinya untuk mengubah sumber air tawar Sepang menjadi sumber air asin. ', 'Kalimantan Tengah', 'Tenas Effendy', 'Legenda', 'Legenda-Sumber-Garam-Sepang-1.png', '2024-06-07 04:30:16', '2024-06-07 04:33:29'),
(2, 'Sangkuriang & Dayang Sumbi', 'Cerita tentang Dayang Sumbi dan putranya, Sangkuriang, yang tanpa sadar hampir menikahi ibunya sendiri yang akhirnya gagal karena luka yang ada dikepala Sangkuriang.', 'Jawa Barat', 'D. H. Sunjaya', 'Legenda', NULL, '2024-06-08 01:57:18', '2024-06-08 01:57:18'),
(3, 'Asal Mula Kota Cianjur', ' Cerita tentang Pak Kikir yang pelit dan putranya yang baik hati, yang mengajarkan pentingnya berbagi dan bijaksana dalam keputusan, membawa perubahan besar pada sebuah desa hingga menjadi Cianjur, sebuah daerah penghasil beras terkenal.', 'Jawa Barat', 'Tim S.M.I.L.E', 'Sage', NULL, '2024-06-08 03:20:57', '2024-06-08 03:20:57'),
(4, 'Lutung Kasarung', 'Purbasari, putri yang diasingkan karena penyakit misterius, sembuh berkat bantuan Lutung Kasarung yang merupakan cinta sejatinya dalam sosok pangeran yang terkutuk.', 'Jawa Barat', 'Lilis Hu', 'Mitos', NULL, '2024-06-08 03:20:57', '2024-06-08 03:20:57'),
(5, 'Keong Mas', 'Dewi Galuh yang pendengki menyihir adiknya, Candra Kirana, menjadi Keong Mas untuk merebut Pangeran Inu Kertapati, namun berkat bantuan seorang kakek sakti dan kegigihan pangeran, Candra Kirana kembali ke wujud semula, memaafkan kakaknya, dan menikah dengan Pangeran Inu Kertapati.', 'Jawa Timur', 'Tira Ikranegara', 'Mitos', NULL, '2024-06-08 03:20:57', '2024-06-08 03:20:57'),
(6, 'Kisah Lembu Sura', 'Lembu Sura, seorang pemuda berkepala lembu yang memenangkan sayembara Raja Brawijaya, mengutuk Gunung Kelud untuk meletus setiap dua windu setelah tipu daya Raja dan Putri Pusparani menggagalkan upayanya memenuhi mas kawin yang mustahil.', 'Jawa Timur', 'Nowo Beny Harjito', 'Mitos', NULL, '2024-06-08 03:24:04', '2024-06-08 03:24:04'),
(7, 'Roro Jonggrang', 'Roro Jonggrang mengelabui Bandung Bondowoso untuk gagal memenuhi syarat membuat seribu candi dalam semalam, namun akhirnya dia dikutuk menjadi patung batu untuk melengkapi jumlah candi, yang kini dikenal sebagai Candi Sewu di Prambanan.', 'DI Yogyakarta', 'Tira Ikranegara', 'Legenda', NULL, '2024-06-08 03:30:19', '2024-06-08 03:30:19'),
(8, 'Suri Ikun dan Dua Ekor Burung', 'Suri Ikun, anak bungsu dari tujuh bersaudara yang memiliki sifat baik, ketika dia terjebak di gua dengan banyak hantu dia dibantu oleh dua burung yang kemudian membawanya ke sebuah kerajaan kecil tempat dia menjadi seorang raja yang adil.', 'Nusa Tenggara Barat', 'Dian Kristiani', 'Fabel', NULL, '2024-06-08 03:30:19', '2024-06-08 03:30:19'),
(9, 'Batu Golog', 'Kisah asal usul Desa Gembong, Batu, Montong Teker yang konon katanya berasal dari pecahan Batu Golog yang terlempar.', 'Nusa Tenggara Barat', 'Dian Kristiani', 'Legenda', NULL, '2024-06-08 03:30:19', '2024-06-08 03:30:19'),
(10, 'Buaya Perompak', 'Aminah terjebak di gua Buaya Perompak, namun dengan cerdik dia berhasil memanfaatkan kesempatan saat Buaya Perompak tidur untuk melarikan diri dan kembali ke keluarganya.', 'Lampung', 'Dian Kristiani', 'Mitos', NULL, '2024-06-08 03:30:19', '2024-06-08 03:30:19'),
(11, 'Si Pahit Lidah', 'Cerita ini mengisahkan perjalanan Serunting dari kehidupan sombong dan semena-mena menuju pemahaman yang lebih dalam tentang kebaikan dan penggunaan kekuatan dengan bijaksana.', 'Sumatera Selatan', 'Dian Kristiani', 'Mitos', NULL, '2024-06-08 03:32:00', '2024-06-08 03:32:00');

-- --------------------------------------------------------

--
-- Table structure for table `story_details`
--

CREATE TABLE `story_details` (
  `id` bigint(20) NOT NULL,
  `story` longtext DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `story_details`
--

INSERT INTO `story_details` (`id`, `story`, `thumbnail`, `created_at`, `updated_at`) VALUES
(1, 'Pada zaman dahulu, di Desa Sepang, Kalimantan Tengah, hiduplah seorang janda bernama Emas. Dia memiliki seorang putri yang cantik jelita, namanya Tumbai. Kecantikan Tumbai terkenal sampai ke luar desa. Apalagi, Tumbai juga gadis yang ramah dan baik hatinya. Karena itu, banyak pemuda yang ingin melamarnya. Namun aneh, Tumbai selalu menolak lamaran tiap pemuda. Hal ini membuat Emas, ibunya, merasa heran. \"Tumbai anakku, bukankah mereka pemuda yang baik? Kenapa kau menolak mereka? Pasti ada satu di antara mereka yang pantas untuk menjadi Suamimu, kan?\"\', Tumbai menggeleng. \"Aku tidak mau sembarangan, Bu. Aku berdoa pada Tuhan, agar mengirimkan suami yang mampu memajukan desa kita ini,\" sahut Tumbai. ', 'Legenda-Sumber-Garam-Sepang-1.png', '2024-06-07 04:31:00', '2024-06-07 04:34:00'),
(2, 'Ibu Tumbai semakin tak mengerti. \"Apa maksudmu?\" Tumbai menjelaskan, bahwa ia hanya mau menikah dengan Pria yang dapat mengubah sumber air tawar Sepang menjadi air asin. \"Tapi itu tak mungkin!\" teriak ibunya. \"Dengan pertolongan Tuhan, semuanya mungkin, Bu,\" Tumbai tetap bersikukuh. Hari demi hari berganti, tak ada pemuda yang sanggup memenuhi syarat dari Tumbai. Sampai akhirnya pada suatu hari, ada pemuda asing yang datang ke desa Sepang. Pemuda itu berasal dari daerah aliran Sungai Barito, dan ia adalah pemuda yang sakti. \"Bolehkah aku melamar anak Ibu?\" tanyanya pada ibu Tumbai. Ibu Tumbai lalu menjelaskan syarat yang diminta oleh Tumbai. Pemuda itu tercenung sejenak.', 'Legenda-Sumber-Garam-Sepang-2.png', '2024-06-07 04:31:00', '2024-06-07 04:34:00'),
(3, 'Lalu, dia memejamkan mata dan meminta bantuan pada para leluhurnya. Dia juga tak henti-hentinya berdoa pada Yang Kuasa. Sampai akhirnya, keajaiban pun terjadi. Sumber air Sepang yang tadinya berasa tawar itu akhirnya berubah menjadi asin. Tumbai amat senang melihat keberhasilan pemuda itu. Ibunya pun tak kalah senang. Pemuda itu akhirnya menikahi Tumbai. Mereka lalu berusaha mengolah sumber air asin itu menjadi garam. Ternyata usaha mereka berhasil, dan mereka mendapatkan banyak uang. Mereka lalu mengajak penduduk desa lainnya untuk mengolah Sumber air asin itu bersama-sama. Sekarang, desa mereka jauh lebih makmur daripada sebelumnya. Keinginan Tumbai untuk memajukan desanya pun tercapai.', 'Legenda-Sumber-Garam-Sepang-3.png', '2024-06-07 04:31:00', '2024-06-07 04:34:00'),
(4, 'Dayang Sumbi adalah seorang putri yang mengasingkan diri di hutan. Di sana, dia hanya ditemani oleh anjing jantannya yang bernama Tumang. Untuk memenuhi kebutuhan sehari-hari, Dayang Sumbi menenun kain yang kemudian dijualnya ke pasar. Suatu hari, alat tenunnya terjatuh dan menggelinding ke bawah bukit. Karena panik, Dayang Sumbi mengucap sumpah, ”Siapa yang bisa mengambilkan alat tenunku, jika perempuan akan kujadikan saudaraku, dan jika lelaki akan kujadikan suamiku.” Tak diduga, Tumang berlari kencang menuruni bukit dan mengambil alat tenun Dayang Sumbi. Dayang Sumbi akhirnya menikah dengan Tumang. ”Aku adalah jelmaan manusia. Tiap malam, aku berubah menjadi manusia. Namun, jangan sampai ada yang mengetahui rahasiaku ini, termasuk anak kita nanti,” kata Tumang di hari pernikahan mereka. Mereka lalu dikaruniai anak yang bernama Sangkuriang. Suatu hari, Sangkuriang kecil marah pada Tumang. Dia mengusir Tumang karena Tumang telah gagal membantunya menangkap rusa. Sangkuriang lalu bercerita pada ibunya, ”Tumang sudah tua. Dia tak berguna lagi.', NULL, '2024-06-08 01:58:14', '2024-06-08 01:58:14'),
(5, 'Aku sudah mengusirnya.” Dayang Sumbi yang sedang mengaduk nasi, terkejut. Dia melemparkan sendoknya begitu saja dan berlari ke halaman. Sendoknya mengenai kepala Sangkuriang yang langsung terluka. Sangkuriang pun marah. Dia mengira ibunya sengaja memukulnya. Dia lalu pergi meninggalkan rumah, padahal ibunya sudah mencegahnya.  Tak terasa, Sangkuriang sudah dewasa. Dia telah menjelajah banyak desa. Suatu hari, tanpa sadar dia telah kembali ke hutan tempat dia dulu tinggal bersama Dayang Sumbi. Hutan itu sudah berubah menjadi perkampungan penduduk. Di sana, Sangkuriang bertemu dengan Dayang Sumbi dan jatuh cinta. Demikian juga dengan Dayang Sumbi, dia tak tahu bahwa itu Sangkuriang. Mereka memutuskan untuk menikah. Untunglah, saat Sangkuriang melepas tutup kepalanya, Dayang Sumbi melihat ada bekas luka di kepala Sangkuriang. Saat mendengar cerita Sangkuriang tentang asal luka itu, sadarlah Dayang Sumbi bahwa pemuda yang hendak dinikahinya adalah Sangkuriang. Dayang Sumbi langsung membatalkan pernikahan mereka dan menjelaskan bahwa dia adalah ibu Sangkuriang. Namun Sangkuriang tak percaya, apalagi Dayang Sumbi terlihat masih muda. Sangkuriang tetap ingin menikahi Dayang Sumbi.', NULL, '2024-06-08 01:58:14', '2024-06-08 01:58:14'),
(6, 'Dayang Sumbi mencari akal. Dia meminta Sangkuriang untuk membendung Sungai Citarum. Selain itu, Sangkuriang juga harus membuat perahu besar untuk menyeberangi sungai itu. Keduanya harus selesai sebelum fajar menyingsing.  Ternyata Sangkuriang menyanggupi kedua syarat tersebut. Sebelum pekerjaannya selesai, Dayang Sumbi mengajak penduduk untuk menggelar kain sutra merah di sebelah timur Sungai Citarum. Sebagian penduduk membuat suara gaduh seolah-olah kegiatan pagi telah dilakukan. Sangkuriang mengira, pagi telah tiba. Dia marah dan kecewa. Dengan segala kekuatan yang dimilikinya, dia menjebol bendungan yang sudah dibuatnya. Air pun meluber ke mana- mana. Dia juga menendang perahu besar yang terbuat dari kayu, sampai terlempar jauh ke utara dan jatuh tertelungkup. Konon, perahu yang jatuh tertelungkup itulah yang kemudian menjadi Gunung Tangkuban Perahu. Tangkuban Perahu sendiri berarti ”perahu yang menelungkup”.', NULL, '2024-06-08 01:58:14', '2024-06-08 01:58:14');

-- --------------------------------------------------------

--
-- Table structure for table `story_quiz_links`
--

CREATE TABLE `story_quiz_links` (
  `story_id` bigint(20) NOT NULL,
  `quiz_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `story_quiz_links`
--

INSERT INTO `story_quiz_links` (`story_id`, `quiz_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(2, 6);

-- --------------------------------------------------------

--
-- Table structure for table `story_story_detail_links`
--

CREATE TABLE `story_story_detail_links` (
  `story_id` bigint(20) NOT NULL,
  `story_detail_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `story_story_detail_links`
--

INSERT INTO `story_story_detail_links` (`story_id`, `story_detail_id`) VALUES
(1, 1),
(1, 2),
(1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `story_theme_links`
--

CREATE TABLE `story_theme_links` (
  `story_id` bigint(20) NOT NULL,
  `theme_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `story_theme_links`
--

INSERT INTO `story_theme_links` (`story_id`, `theme_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `themes`
--

CREATE TABLE `themes` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `themes`
--

INSERT INTO `themes` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'Permintaan', '2024-06-02 15:16:53', '2024-06-02 15:16:53'),
(2, 'Asal-usul', '2024-06-02 15:16:53', '2024-06-02 15:16:53'),
(3, 'Pernikahan', '2024-06-02 15:16:53', '2024-06-02 15:16:53'),
(4, 'Cinta Terlarang', '2024-06-08 01:58:56', '2024-06-08 01:58:56');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `username`, `email`, `password`, `created_at`, `updated_at`) VALUES
(1, 'Super', 'Admin', 'superadmin', 'superadmin@yopmail.com', '$2b$10$WmLdFwnenIJEBEuwnMafweae/ls325U85YitvdCRXLfbWzxTyoSle', '2024-06-02 06:01:14', '2024-06-02 06:01:14');

-- --------------------------------------------------------

--
-- Table structure for table `user_mission_links`
--

CREATE TABLE `user_mission_links` (
  `user_id` bigint(20) NOT NULL,
  `mission_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_mission_links`
--

INSERT INTO `user_mission_links` (`user_id`, `mission_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_story_links`
--

CREATE TABLE `user_story_links` (
  `user_id` bigint(20) NOT NULL,
  `story_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_story_links`
--

INSERT INTO `user_story_links` (`user_id`, `story_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `choices`
--
ALTER TABLE `choices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `missions`
--
ALTER TABLE `missions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quizzes`
--
ALTER TABLE `quizzes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quiz_choice_links`
--
ALTER TABLE `quiz_choice_links`
  ADD PRIMARY KEY (`quiz_id`,`choice_id`),
  ADD KEY `choice_id` (`choice_id`);

--
-- Indexes for table `stories`
--
ALTER TABLE `stories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `story_details`
--
ALTER TABLE `story_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `story_quiz_links`
--
ALTER TABLE `story_quiz_links`
  ADD PRIMARY KEY (`story_id`,`quiz_id`),
  ADD KEY `quiz_id` (`quiz_id`);

--
-- Indexes for table `story_story_detail_links`
--
ALTER TABLE `story_story_detail_links`
  ADD PRIMARY KEY (`story_id`,`story_detail_id`),
  ADD KEY `story_detail_id` (`story_detail_id`);

--
-- Indexes for table `story_theme_links`
--
ALTER TABLE `story_theme_links`
  ADD PRIMARY KEY (`story_id`,`theme_id`),
  ADD KEY `theme_id` (`theme_id`);

--
-- Indexes for table `themes`
--
ALTER TABLE `themes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_mission_links`
--
ALTER TABLE `user_mission_links`
  ADD PRIMARY KEY (`user_id`,`mission_id`),
  ADD KEY `mission_id` (`mission_id`);

--
-- Indexes for table `user_story_links`
--
ALTER TABLE `user_story_links`
  ADD PRIMARY KEY (`user_id`,`story_id`),
  ADD KEY `story_id` (`story_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `choices`
--
ALTER TABLE `choices`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `missions`
--
ALTER TABLE `missions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `quizzes`
--
ALTER TABLE `quizzes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `stories`
--
ALTER TABLE `stories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `story_details`
--
ALTER TABLE `story_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `themes`
--
ALTER TABLE `themes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `quiz_choice_links`
--
ALTER TABLE `quiz_choice_links`
  ADD CONSTRAINT `quiz_choice_links_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `quiz_choice_links_ibfk_2` FOREIGN KEY (`choice_id`) REFERENCES `choices` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `story_quiz_links`
--
ALTER TABLE `story_quiz_links`
  ADD CONSTRAINT `story_quiz_links_ibfk_1` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `story_quiz_links_ibfk_2` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `story_story_detail_links`
--
ALTER TABLE `story_story_detail_links`
  ADD CONSTRAINT `story_story_detail_links_ibfk_1` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `story_story_detail_links_ibfk_2` FOREIGN KEY (`story_detail_id`) REFERENCES `story_details` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `story_theme_links`
--
ALTER TABLE `story_theme_links`
  ADD CONSTRAINT `story_theme_links_ibfk_1` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `story_theme_links_ibfk_2` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_mission_links`
--
ALTER TABLE `user_mission_links`
  ADD CONSTRAINT `user_mission_links_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_mission_links_ibfk_2` FOREIGN KEY (`mission_id`) REFERENCES `missions` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_story_links`
--
ALTER TABLE `user_story_links`
  ADD CONSTRAINT `user_story_links_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_story_links_ibfk_2` FOREIGN KEY (`story_id`) REFERENCES `stories` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
