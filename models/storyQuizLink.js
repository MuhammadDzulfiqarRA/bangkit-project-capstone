const { connection, query } = require('../config/database');

class StoryQuizLink {
    constructor(storyId, quizId) {
        this.storyId = storyId;
        this.quizId = quizId;
    }

    static async findManyByStoryId(storyId) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM story_quiz_links WHERE story_id = ?', [storyId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }
}

module.exports = StoryQuizLink;