const { connection, query } = require('../config/database');

class Theme {
    constructor(name) {
        this.name = name;
    }

    static async findMany() {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM themes', (error, results) => {
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
            query('SELECT * FROM themes WHERE id = ?', [id], (error, results) => {
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
            query('SELECT * FROM themes WHERE id IN (?)', [idArray], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }

}

module.exports = Theme;