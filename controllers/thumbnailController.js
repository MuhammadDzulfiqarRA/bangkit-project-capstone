const path = require('path'); // Tambahkan baris ini

const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

exports.getThumbnails = async (req, res) => {
    try {
        const { filename } = req.params;
        if (!filename) {
            return res.status(400).json({
                code: 400,
                status: 'error',
                message: 'Missing filename',
                data: null
            })
        }

        const filePath = path.join(__dirname, '../uploads', filename);

        res.sendFile(filePath, (err) => {
            if (err) {
                console.error('Error sending file:', err);
                res.status(404).send('File not found');
            }
        });
    } catch (error) {
        console.error('Error fetching thumbnails:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}
