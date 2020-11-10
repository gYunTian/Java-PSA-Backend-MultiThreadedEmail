import axios from 'axios';
import { elements, APIs, headers } from '../views/base';

export default class User {
    constructor(name = null, email = null, password = null) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // *Login user
    async loginUser() {
        const serviceURL = `${APIs.loginUser}`;
        try {
            const result = await axios({
                method: 'POST',
                url: serviceURL,
                data: {
                    email: this.email,
                    password: this.password,
                },
                headers,
            });
            this.name = result.data.split("'")[0].split(' ')[2];
            this.name = this.name.charAt(0).toUpperCase() + this.name.slice(1);
            this.userID = parseInt(result.data.split(',')[0].split(' ')[1]);
            this.loginStatus = result.status;
            this.password = null;
        } catch (error) {
            console.log(`User.js loginUser error: ${error}`);
        }
    }

    // *Add user to database
    async registerUser() {
        const serviceURL = APIs.addUser;
        try {
            const result = await axios({
                method: 'POST',
                url: serviceURL,
                data: {
                    name: this.name,
                    email: this.email,
                    password: this.password,
                },
                headers,
            });
            this.registerStatus = result.status;
            this.userID = parseInt(result.data.split(' ').pop());
        } catch (error) {
            console.log(`User.js addUser error: ${error}`);
        }
    }

    // *Request token
    async requestToken(email) {
        const serviceURL = `${APIs.requestToken}?email=${email}`;
        console.log({ serviceURL });
        try {
            const result = await axios({
                method: 'POST',
                url: serviceURL,
                headers,
            });
            alert(result.status);
        } catch (err) {
            console.log(`User.js requestToken error: ${err}`);
        }
    }

    // *Reset password
    async resetPassword(token, pw) {
        const serviceURL = `${APIs.resetPassword}`;
        console.log(serviceURL);
        try {
            const result = await axios({
                method: 'PUT',
                url: serviceURL,
                data: {
                    identifier: token,
                    new_password: pw,
                },
            });
            alert(result.data);
        } catch (err) {
            console.log(`User.js resetPassword error: ${err}`);
        }
    }
}
