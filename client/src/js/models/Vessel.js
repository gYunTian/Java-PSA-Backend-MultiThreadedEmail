import axios from 'axios';
import { elements, APIs, headers, processData } from '../views/base';

export default class Vessel {
  constructor(startDate, endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  async getNext7Days() {
    const serviceURL = `${APIs.getVessels}?startDate=${this.startDate}&endDate=${this.endDate}`;
    console.log(
      `Getting vessel data from ${this.startDate} to ${this.endDate} using serviceURL:`
    );
    console.log({ serviceURL });
    try {
      const result = await axios({
        method: 'GET',
        url: serviceURL,
        headers,
      });
      this.allData = result.data;
      this.niceData = processData(result.data);
    } catch (error) {
      console.log(`Vessel.js getNext7Days error: ${error}`);
    }
  }
}
