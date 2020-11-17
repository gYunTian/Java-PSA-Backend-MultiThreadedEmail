import axios from 'axios';
import { elements, APIs, headers } from '../views/base';

export default class Subscription {
  constructor(userID, voyageID) {
    this.userID = userID;
    this.voyageID = voyageID;
  }

  setVoyageID(id) {
    this.voyageID = id;
  }

  async addSub() {
    const serviceURL = `${APIs.addSub}`;
    try {
      const result = await axios({
        method: 'POST',
        url: serviceURL,
        data: {
          userId: this.userID,
          voyageId: this.voyageID,
        },
        headers,
      });
      this.addSubStatus = result.status;
      this.addSubMessage = result.data;
      alert(this.addSubMessage);
    } catch (err) {
      console.log(`Subscription.js addSub error: ${err}`);
    }
  }

  async getSubs() {
    const serviceURL = `${APIs.getSubs}/${this.userID}`;
    try {
      const result = await axios({
        method: 'GET',
        url: serviceURL,
        headers,
      });
      this.subsArr = [];
      result.data.forEach(e => {
        const { voyageId } = e;
        this.subsArr.push(voyageId);
      });
    } catch (err) {
      console.log(`Subscription.js getSubs error: ${err}`);
    }
  }

  async deleteSub() {
    const serviceURL = `${APIs.deleteSub}`;
    try {
      const result = await axios({
        method: 'DELETE',
        url: serviceURL,
        data: {
          userId: this.userID,
          voyageId: this.voyageID,
        },
        headers,
      });
      this.deleteSubStatus = result.status;
      this.deleteSubMessage = result.data;
      alert(this.deleteSubMessage);
    } catch (err) {
      console.log(`Subscription.js deleteSub error: ${err}`);
    }
  }
}
