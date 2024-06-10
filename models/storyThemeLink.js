const { connection, query } = require('../config/database');

class StoryThemeLink {
    constructor(storyId, themeId) {
        this.storyId = storyId;
        this.themeId = themeId;
    }

    static async findManyByThemeId(themeId) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM story_theme_links WHERE theme_id = ?', [themeId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }

    static async findManyByStoryId(storyId) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM story_theme_links WHERE story_id = ?', [storyId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }
}

module.exports = StoryThemeLink;