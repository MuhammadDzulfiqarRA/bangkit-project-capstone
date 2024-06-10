const express = require('express');
const router = express.Router();
const quizController = require('../controllers/quizController');

router.get('/:story_id', quizController.getQuizByStoryId);

module.exports = router;