import axios from 'axios';
import { elements, APIs } from '../views/base';

export default class User {
  constructor(name, email, password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  // *Add user to database
  async registerUser() {
    const serviceURL = APIs.addUser;
    try {
      const result = await axios({
        method: 'POST',
        url: serviceURL,
        headers: { 'Access-Control-Allow-Origin': '*' },
        data: {
          name: this.name,
          email: this.email,
          password: this.password,
        },
      });
    } catch (error) {
      console.log(`User.js addUser error: ${error}`);
    }
  }
}
