const mysql = require('mysql');
const util = require('util');
const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

const dbConfig = {
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
};

// Buat koneksi ke database
const connection = mysql.createConnection(dbConfig);

// Koneksi ke database
connection.connect((err) => {
    if (err) {
        console.error('Error connecting to database:', err);
        throw err;
    }
    console.log('Connected to MySQL database');
});

// Mengubah method query menjadi promise-based
const query = util.promisify(connection.query).bind(connection);

module.exports = { connection, query };
