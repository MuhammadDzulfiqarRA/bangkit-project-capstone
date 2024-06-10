const { connection, query } = require('../config/database');

class Mission {
    constructor(title, condition) {
        this.title = title;
        this.condition = condition;
    }

    static async findMany() {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM missions', (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }

    static async findManyById(ids) {
        // Pastikan ids adalah array
        const idArray = Array.isArray(ids) ? ids : ids.split(',').map(id => parseInt(id.trim()));

        return new Promise((resolve, reject) => {
            query('SELECT * FROM missions WHERE id IN (?)', [idArray], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }
}

module.exports = Mission;