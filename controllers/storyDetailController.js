const story = require('../models/story');
const storyDetail = require('../models/storyDetail');
const storyStoryDetailLink = require('../models/storyStoryDetailLink');
const userStoryLink = require('../models/userStoryLink');
const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

exports.getStoryDetailsByStoryId = async (req, res) => {
    const { id, user_id } = req.params;
    try {
        const storyDetailLinks = await storyStoryDetailLink.findManyByStoryId(id);

        if (storyDetailLinks.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No story details found',
                data: null
            });
        }

        await userStoryLink.create(user_id, id);

        let storyDetailIds = [];

        if (storyDetailLinks.length > 0) {
            // Looping through storyDetailLinks and extracting story_detail_ids
            storyDetailIds = storyDetailLinks.map(storyDetail => storyDetail.story_detail_id).join(',');
        } else {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No story details found',
                data: null
            });
        }

        if (storyDetailIds.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No story details found',
                data: null
            });
        }

        const storyDetails = await storyDetail.findManyById(storyDetailIds);

        if (storyDetails.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No story details found',
                data: null
            });
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Story details fetched successfully',
            data: storyDetails
        });

    } catch (error) {
        console.error('Error fetching story details:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}