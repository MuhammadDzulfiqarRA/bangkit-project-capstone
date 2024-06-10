const { connection, query } = require('../config/database');

class QuizChoiceLink {
    constructor(quizId, choiceId) {
        this.quizId = quizId;
        this.choiceId = choiceId;
    }

    static async findManyByQuizId(quizId) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM quiz_choice_links WHERE quiz_id = ?', [quizId], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    resolve(results);
                }
            });
        });
    }
}

module.exports = QuizChoiceLink