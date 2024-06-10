const { connection, query } = require('../config/database');

class Choice {
    constructor(option) {
        this.option = option;
    }

    static findManyById(ids) {
        // Pastikan ids adalah array
        const idArray = Array.isArray(ids) ? ids : ids.split(',').map(id => parseInt(id.trim()));

        return new Promise((resolve, reject) => {
            query('SELECT * FROM choices WHERE id IN (?)', [idArray], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        })
    }
}

module.exports = Choice