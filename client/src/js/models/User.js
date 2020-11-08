import axios from 'axios';
import { elements, APIs, headers } from '../views/base';

export default class User {
    constructor(name = null, email, password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // // *Get user by email (deprecated method to login)
    // async getUserByEmail() {
    //     const serviceURL = `${APIs.getUserByEmail}/${this.email}`;
    //     try {
    //         const result = await axios({
    //             method: 'GET',
    //             url: serviceURL,
    //         });
    //         this.dataFromServer = result.data;
    //     } catch (error) {
    //         console.log(`User.js getUserByEmail error: ${error}`);
    //     }
    // }

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
            console.log(result.data);
            this.name = result.data.split('\'')[0];
            this.loginStatus = result.status;
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
        } catch (error) {
            console.log(`User.js addUser error: ${error}`);
        }
    }
}
