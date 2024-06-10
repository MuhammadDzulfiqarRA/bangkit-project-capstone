const { connection, query } = require('../config/database');

class User {
    constructor(firstname, lastname, username, email, password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    static async findByUsername(username) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM users WHERE username = ?', [username], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    if (results.length > 0) {
                        const { firstname, lastname, username, email, password } = results[0];
                        const user = new User(firstname, lastname, username, email, password);
                        resolve(user);
                    } else {
                        resolve(null);
                    }
                }
            });
        });
    }

    static async create(firstname, lastname, username, email, password) {
        return new Promise((resolve, reject) => {
            query('INSERT INTO users (firstname, lastname, username, email, password) VALUES (?, ?, ?, ?, ?)', [firstname, lastname, username, email, password], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    const { insertId } = results;
                    resolve(insertId);
                }
            });
        });
    }
    

    static async findByEmail(email) {
        return new Promise((resolve, reject) => {
            query('SELECT * FROM users WHERE email = ?', [email], (error, results) => {
                if (error) {
                    reject(error);
                } else {
                    if (results.length > 0) {
                        const { firstname, lastname, username, email, password } = results[0];
                        const user = new User(firstname, lastname, username, email, password);
                        resolve(user);
                    } else {
                        resolve(null);
                    }
                }
            });
        });
    }
}

module.exports = User;
