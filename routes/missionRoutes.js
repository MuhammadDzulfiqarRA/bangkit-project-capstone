const express = require('express');
const router = express.Router();
const missionController = require('../controllers/missionController');

router.get('/:user_id', missionController.getMissionsByUserId);
router.get('/ranked/:user_id', missionController.rankedMissions);
router.post('/claim', missionController.claimMission);

module.exports = router;
