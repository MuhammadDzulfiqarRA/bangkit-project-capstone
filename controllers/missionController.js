const mission = require('../models/mission');
const userStoryLink = require('../models/userStoryLink');
const userMissionLink = require('../models/userMissionLink');
const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

exports.getMissionsByUserId = async (req, res) => {
    const { user_id } = req.params;

    try {
        const missions = await mission.findMany();

        const userStoryLinks = await userStoryLink.findManyByUserId(user_id);

        if (userStoryLinks.length === 0) {
            return res.json({
                code: 404,
                status: 'error',
                message: 'No story links found',
                data: null
            });
        }

        // Membuat salinan misi untuk dimodifikasi
        const userMissionsCopy = JSON.parse(JSON.stringify(missions));

        // Menentukan misi yang sesuai berdasarkan jumlah cerita yang telah dibaca
        userMissionsCopy.forEach((userMission, index) => {
            const storySlice = userStoryLinks.slice(0, userMission.condition);
            userMission.data = storySlice;
            userStoryLinks.splice(0, storySlice.length); // Menghapus cerita yang telah ditambahkan
        });

        // Mengirimkan respons dengan data misi yang sesuai
        return res.json({
            code: 200,
            status: 'success',
            message: 'Missions fetched successfully',
            data: userMissionsCopy
        });

    } catch (error) {
        console.error('Error fetching missions:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}

exports.claimMission = async (req, res) => {
    const { user_id, mission_id } = req.body;
    try {
        const entry = await userMissionLink.create(user_id, mission_id);
        if (entry === 0) {
            return res.status(400).json({
                code: 400,
                status: 'error',
                message: 'Mission already claimed',
                data: null
            });
        }
        res.json({
            code: 200,
            status: 'success',
            message: 'Mission claimed successfully',
            data: null
        });
    } catch (error) {
        console.error('Error fetching missions:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}

exports.rankedMissions = async (req, res) => {
    try {
        const user_id = req.params.user_id;

        const count = await userStoryLink.countByUserId(user_id);

        let rank = "";
        if (count >= 34 && count <= 50) {
            rank = "Diamond";
        } else if (count >= 17 && count <= 33) {
            rank = "Gold";
        } else if (count >= 0 && count <= 16) {
            rank = "Silver";
        } else {
            res.status(404).json({
                code: 404,
                status: 'error',
                message: 'Invalid user story count',
                data: null
            });
            return;
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Ranked missions fetched successfully',
            data: {
                count,
                rank
            }
        });
    } catch (error) {
        console.error('Error fetching ranked missions:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}
