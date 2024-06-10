const express = require('express');
const router = express.Router();
const storyController = require('../controllers/storyController');

router.get('/', storyController.getStories);
router.get('/:id', storyController.getStory);
router.get('/recommended/:user_id', storyController.getRecommendedStories);
router.get('/themes/:themeId', storyController.getStoriesByTheme);
router.get('/search/:title', storyController.searchStories);
router.get('/history/:user_id', storyController.getHistoriesByUserId);

module.exports = router;
