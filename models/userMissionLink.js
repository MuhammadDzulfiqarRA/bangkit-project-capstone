const { connection, query } = require('../config/database');

class UserMissionLink {
    constructor(userId, missionId) {
        this.userId = userId;
        this.missionId = missionId;
    }

    static async create(userId, missionId) {
        return new Promise((resolve, reject) => {
            query('INSERT IGNORE INTO user_mission_links (user_id, mission_id) VALUES (?, ?)', [userId, missionId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    const { affectedRows } = results;
                    resolve(affectedRows);
                }
            });
        });
    }

    static async findManyByUserId(userId) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM user_mission_links WHERE user_id = ?', [userId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }
}

module.exports = UserMissionLink;