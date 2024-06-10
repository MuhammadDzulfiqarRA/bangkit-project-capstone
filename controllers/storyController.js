const story = require('../models/story');
const storyThemeLink = require('../models/storyThemeLink');
const userStoryLink = require('../models/userStoryLink');
const theme = require('../models/theme');
const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

exports.getStories = async (req, res) => {
    try {
        const stories = await story.findMany();

        if (stories.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No stories found',
                data: null
            });
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Stories fetched successfully',
            data: stories
        });
    } catch (error) {
        console.error('Error fetching stories:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}

exports.getStory = async (req, res) => {
    const { id } = req.params;
    try {
        const storyData = await story.findOne(id); // Ubah nama variabel di sini

        if (!storyData) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'Story not found',
                data: null
            });
        }

        // Mengambil data themes dari tabel story_theme_links
        const storyThemeLinks = await storyThemeLink.findManyByStoryId(storyData.id);

        let themeIds = [];

        if (storyThemeLinks.length > 0) {
            // Looping through storyThemeLinks and extracting theme_ids
            themeIds = storyThemeLinks.map(theme => theme.theme_id).join(',');
        } else {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No themes found',
                data: null
            });
        }

        if (themeIds.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No themes found',
                data: null
            });
        }

        const themes = await theme.findManyById(themeIds);

        if (themes.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No themes found',
                data: null
            });
        }

        storyData.themes = themes;

        res.json({
            code: 200,
            status: 'success',
            message: 'Story fetched successfully',
            data: storyData // Ubah nama variabel di sini
        });
    } catch (error) {
        console.error('Error fetching story:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}

exports.getStoriesByTheme = async (req, res) => {
    const { themeId } = req.params;
    try {
        const themes = await storyThemeLink.findManyByThemeId(themeId);

        if (!themes) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'Theme not found',
                data: null
            });
        }

        let storyIds = [];

        if (themes.length > 0) {
            // Looping through themes and extracting story_ids
            storyIds = themes.map(theme => theme.story_id).join(',');
        } else {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No stories found',
                data: null
            });
        }

        if (storyIds.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No themes found',
                data: null
            });
        }

        const stories = await story.findManyById(storyIds);

        if (stories.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No stories found',
                data: null
            });
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Themes fetched successfully',
            data: stories
        });
    } catch (error) {
        console.error('Error fetching theme:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}

exports.searchStories = async (req, res) => {
    const { title } = req.params;
    try {
        const stories = await story.findManyByTitle(title);

        if (!stories) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'Story not found',
                data: null
            });
        }

        if (stories.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No stories found',
                data: null
            });
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Stories fetched successfully',
            data: stories
        });

    } catch (error) {
        console.error('Error fetching story:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}

exports.getHistoriesByUserId = async (req, res) => {
    const { user_id } = req.params;
    try {
        const userStoryLinks = await userStoryLink.findManyByUserId(user_id);

        if (!userStoryLinks) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'User story link not found',
                data: null
            });
        }
        if (userStoryLinks.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No user story links found',
                data: null
            });
        }
        const storyIds = userStoryLinks.map(userStoryLink => userStoryLink.story_id).join(',');
        const stories = await story.findManyById(storyIds);
        if (!stories) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'Stories not found',
                data: null
            });
        }
        if (stories.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No stories found',
                data: null
            });
        }
        res.json({
            code: 200,
            status: 'success',
            message: 'Stories fetched successfully',
            data: stories
        });
    } catch (error) {
        console.error('Error fetching story:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}

exports.getRecommendedStories = async (req, res) => {
    const { user_id } = req.params;
    try {
        const userStoryLinks = await userStoryLink.getStoryIdCounts();

        if (!userStoryLinks) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'User story link not found',
                data: null
            });
        }
        if (userStoryLinks.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No user story links found',
                data: null
            });
        }
        const storyIds = userStoryLinks.map(userStoryLink => userStoryLink.story_id).join(',');
        const stories = await story.findManyById(storyIds);
        if (!stories) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'Stories not found',
                data: null
            });
        }
        if (stories.length === 0) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'No stories found',
                data: null
            });
        }
        res.json({
            code: 200,
            status: 'success',
            message: 'Stories fetched successfully',
            data: stories
        });
    } catch (error) {
        console.error('Error fetching story:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}