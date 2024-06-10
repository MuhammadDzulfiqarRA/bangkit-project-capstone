const { connection, query } = require('../config/database');

class Story {
    constructor(title, overview, origin, author) {
        this.title = title;
        this.overview = overview;
        this.origin = origin;
        this.author = author;
    }

    static async findMany() {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM stories', (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }

    static async findOne(id) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM stories WHERE id = ?', [id], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results[0]);
                }
            });
        });
    }

    static async findManyById(ids) {
        // Pastikan ids adalah array
        const idArray = Array.isArray(ids) ? ids : ids.split(',').map(id => parseInt(id.trim()));

        return new Promise((resolve, reject) => {
            query('SELECT * FROM stories WHERE id IN (?)', [idArray], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }

    static async findManyByTitle(title) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM stories WHERE title LIKE ?', [`%${title}%`], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }
}

module.exports = Story;