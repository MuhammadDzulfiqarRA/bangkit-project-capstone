const { connection, query } = require('../config/database');

class UserStoryLink {
    constructor(userId, storyId) {
        this.userId = userId;
        this.storyId = storyId;
    }

    static async create(userId, storyId) {
        return new Promise((resolve, reject) => {
            query('INSERT IGNORE INTO user_story_links (user_id, story_id) VALUES (?, ?)', [userId, storyId], (error, results) => {
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
            query('SELECT * FROM user_story_links WHERE user_id = ?', [userId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }

    static async countByUserId(userId) {
        return new Promise((resolve, reject) => {
            query('SELECT COUNT(*) AS count FROM user_story_links WHERE user_id = ?', [userId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results[0].count);
                }
            });
        });
    }

    static async getStoryIdCounts() {
        return new Promise((resolve, reject) => {
            query('SELECT story_id, COUNT(*) AS count FROM user_story_links GROUP BY story_id ORDER BY count DESC', (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }
}

module.exports = UserStoryLink;