import axios from 'axios';
import { elements, APIs, headers } from '../views/base';

export default class Favourite {
  constructor(userID, voyageID) {
    this.userID = userID;
    this.voyageID = voyageID;
  }

  setVoyageID(id) {
    this.voyageID = id;
  }

  async addFav() {
    const serviceURL = `${APIs.addFav}`;
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
      this.addFavStatus = result.status;
      this.addFavMessage = result.data;
      alert(this.addFavMessage);
    } catch (err) {
      console.log(`Favourite.js addFav error: ${err}`);
    }
  }

  async getFavs() {
    const serviceURL = `${APIs.getFavs}/${this.userID}`;
    try {
      const result = await axios({
        method: 'GET',
        url: serviceURL,
        headers,
      });
      this.favsArr = [];
      result.data.forEach(e => {
        const { voyageId } = e;
        this.favsArr.push(voyageId);
      });
    } catch (err) {
      console.log(`Favourite.js getFavs error: ${err}`);
    }
  }

  async deleteFav() {
    const serviceURL = `${APIs.deleteFav}`;
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
      this.deleteFavStatus = result.status;
      this.deleteFavMessage = result.data;
      alert(this.deleteFavMessage);
    } catch (err) {
      console.log(`Favourite.js deleteFav error: ${err}`);
    }
  }
}
