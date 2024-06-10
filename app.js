const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const dotenv = require('dotenv'); // Jika Anda menggunakan dotenv untuk mengakses variabel lingkungan
const authRoutes = require('./routes/authRoutes');
const storyRoutes = require('./routes/storyRoutes');
const themeRoutes = require('./routes/themeRoutes');
const storyDetailRoutes = require('./routes/storyDetailRoutes');
const thumbnailRoutes = require('./routes/thumbnailRoutes');
const missionRoutes = require('./routes/missionRoutes');
const quizRoutes = require('./routes/quizRoutes');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

// Inisialisasi Express
const app = express();

// Menggunakan middleware
app.use(bodyParser.json());
app.use(cors());

// Definisikan rute-rute API
app.use('/api/v1/auth', authRoutes);
app.use('/api/v1/stories', storyRoutes);
app.use('/api/v1/themes', themeRoutes);
app.use('/api/v1/story-details', storyDetailRoutes);
app.use('/api/v1/thumbnails', thumbnailRoutes);
app.use('/api/v1/missions', missionRoutes);
app.use('/api/v1/quizzes', quizRoutes);

// Menjalankan server Express
const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});