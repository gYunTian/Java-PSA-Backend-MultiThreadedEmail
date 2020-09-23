import axios from 'axios';
import { elements, APIs } from '../views/base';

export default class User {
  constructor(name = null, email, password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  // *Authenticate user
  async getUserByEmail() {
    const serviceURL = `${APIs.getUserByEmail}/${this.email}`;
    try {
      const result = await axios({
        method: 'GET',
        url: serviceURL,
      });
      this.dataFromServer = result.data;
    } catch (error) {
      console.log(`User.js getUserByEmail error: ${error}`);
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
      });
      this.registerStatus = result.status;
    } catch (error) {
      console.log(`User.js addUser error: ${error}`);
    }
  }
}
