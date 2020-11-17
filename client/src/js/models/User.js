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
      this.response = error.response.data;
      console.log(`User.js loginUser error: ${error.response.data}`);
    }
  }

  // *Add user to database
  async registerUser() {
    const serviceURL = APIs.registerUser;
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
      this.response = error.response.data;
      console.log(`User.js registerUser error: ${error.response.data}`);
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
      this.requestTokenStatus = result.status;
      this.requestTokenResponse = result.data;
    } catch (err) {
      this.requestTokenErrMsg = err.response.data;
      console.log(`User.js requestToken error: ${err.response.data}`);
    }
  }

  // *Reset password
  async resetPassword(email, token, new_password) {
    const serviceURL = `${APIs.resetPassword}`;
    console.log(serviceURL);
    try {
      const result = await axios({
        method: 'PUT',
        url: serviceURL,
        data: {
          email,
          token,
          new_password,
        },
        headers,
      });
      console.log(result.data);
      this.changePwStatus = result.status;
      this.changePwResponse = result.data;
    } catch (err) {
      this.changePwErrMsg = err.response.data;
      console.log(`User.js resetPassword error: ${err.reponse.data}`);
    }
  }
}
