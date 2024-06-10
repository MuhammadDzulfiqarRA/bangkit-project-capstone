const theme = require('../models/theme');
const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

exports.getThemes = async (req, res) => {
    try {
        const themes = await theme.findMany();
        res.json({
            code: 200,
            status: 'success',
            message: 'Themes fetched successfully',
            data: themes
        });
    } catch (error) {
        console.error('Error fetching themes:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}