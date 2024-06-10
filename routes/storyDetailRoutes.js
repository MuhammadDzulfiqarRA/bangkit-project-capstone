const express = require('express');
const router = express.Router();
const storyDetailController = require('../controllers/storyDetailController');

router.get('/:id/:user_id', storyDetailController.getStoryDetailsByStoryId);

module.exports = router;