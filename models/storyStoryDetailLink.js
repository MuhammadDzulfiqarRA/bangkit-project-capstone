const { connection, query } = require('../config/database');

class StoryThemeLink {
    constructor(storyId, storyDetailId) {
        this.storyId = storyId;
        this.storyDetailId = storyDetailId;
    }

    static async findManyByStoryId(storyId) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM story_story_detail_links WHERE story_id = ?', [storyId], (error, results) => {
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