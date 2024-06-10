const bcrypt = require('bcrypt');
const User = require('../models/user');
const dotenv = require('dotenv');

// Mengakses variabel lingkungan jika menggunakan dotenv
dotenv.config();

exports.login = async (req, res) => {
    const { username, password } = req.body;

    try {
        // Find user by username
        const user = await User.findByUsername(username);

        if (!user) {
            return res.status(404).json({
                code: 404,
                status: 'error',
                message: 'User not found'
            });
        }

        // Perform password comparison
        const passwordMatch = await bcrypt.compare(password, user.password);

        if (!passwordMatch) {
            return res.status(401).json({
                code: 401,
                status: 'error',
                message: 'Invalid credentials'
            });
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Login successful',
            data: {
                id: user.id,
                firstname: user.firstname,
                lastname: user.lastname,
                username: user.username,
                email: user.email
            }
        });
    } catch (error) {
        console.error('Login error:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
};

exports.register = async (req, res) => {
    const { firstname, lastname, username, email, password } = req.body;

    try {
        // Validasi email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            return res.status(400).json({
                code: 400,
                status: 'error',
                message: 'Invalid email address'
            });
        }

        // Validasi username
        if (username.length < 3 || username.length > 20) {
            return res.status(400).json({
                code: 400,
                status: 'error',
                message: 'Username must be between 3 and 20 characters'
            });
        }

        // Validasi spasi di username
        if (username.includes(' ')) {
            return res.status(400).json({
                code: 400,
                status: 'error',
                message: 'Username cannot contain spaces'
            });
        }

        // Memeriksa apakah username sudah ada
        const existingUser = await User.findByUsername(username);
        if (existingUser) {
            return res.status(409).json({
                code: 409,
                status: 'error',
                message: 'Username already exists'
            });
        }

        // Memeriksa apakah email sudah ada
        const existingEmail = await User.findByEmail(email);
        if (existingEmail) {
            return res.status(409).json({
                code: 409,
                status: 'error',
                message: 'Email already exists'
            });
        }

        // Validasi password
        if (password.length < 8) {
            return res.status(400).json({
                code: 400,
                status: 'error',
                message: 'Password must be at least 8 characters long'
            });
        }

        // Membuat ekspresi reguler untuk memvalidasi password
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        // Memeriksa apakah password sesuai dengan aturan
        if (!passwordRegex.test(password)) {
            return res.status(400).json({
                code: 400,
                status: 'error',
                message: 'Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&)'
            });
        }

        // Hash the password
        const hashedPassword = await bcrypt.hash(password, 10);

        // Create new user
        const user = await User.create(firstname, lastname, username, email, hashedPassword);

        res.json({
            code: 200,
            status: 'success',
            message: 'User created successfully',
            user
        });
    } catch (error) {
        console.error('Register error:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
};

exports.loginByEmail = async (req, res) => {
    const { email } = req.body;

    try {
        const user = await User.findByEmail(email);
        if (!user) {
            return res.status(401).json({
                code: 401,
                status: 'error',
                message: 'Email not found'
            });
        }

        res.json({
            code: 200,
            status: 'success',
            message: 'Login successful',
            data: {
                id: user.id,
                firstname: user.firstname,
                lastname: user.lastname,
                username: user.username,
                email: user.email
            }
        });
    } catch (error) {
        console.error('Login error:', error);
        res.status(500).json({
            code: 500,
            status: 'error',
            message: 'Internal server error'
        });
    }
}