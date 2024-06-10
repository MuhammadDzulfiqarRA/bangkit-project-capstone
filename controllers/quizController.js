const quiz = require('../models/Quiz');
const choice = require('../models/choice');
const storyQuizLink = require('../models/storyQuizLink');
const quizChoiceLink = require('../models/quizChoiceLink');
const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

exports.getQuizByStoryId = async (req, res) => {
    const { story_id } = req.params;
    try {
        const storyQuizLinks = await storyQuizLink.findManyByStoryId(story_id);

        if (storyQuizLinks.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No quiz found',
                data: null
            });
        }

        const quizIds = storyQuizLinks.map(storyQuizLink => storyQuizLink.quiz_id).join(',');

        const quizzes = await quiz.findManyById(quizIds);

        // Use a for loop to ensure async/await works correctly
        for (const quiz of quizzes) {
            const quizChoiceLinks = await quizChoiceLink.findManyByQuizId(quiz.id);
            const choiceIds = quizChoiceLinks.map(quizChoiceLink => quizChoiceLink.choice_id).join(',');

            quiz.choices = await choice.findManyById(choiceIds);
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Quiz fetched successfully',
            data: quizzes
        });
    } catch (error) {
        console.error('Error fetching quiz:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}
