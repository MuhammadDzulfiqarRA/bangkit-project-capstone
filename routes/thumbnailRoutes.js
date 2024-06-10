const express = require('express');
const router = express.Router();
const thumbnailController = require('../controllers/thumbnailController');

router.get('/display/:filename', thumbnailController.getThumbnails);

module.exports = router;
