import { elements, clearSpinner } from './views/base';

import { data, processData } from './data/data';

import * as navView from './views/navView';

const state = {};
console.log({ state });

// *Perform tasks after page loads
window.addEventListener('load', () => {
  controlLogin();
  // !Data for testing
  state.data = processData(data);
  
  // *Get current date into state
  state.date = new Date();
});

// *Control login (note that state.user.id = null for sign up)
const controlLogin = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  if (!user) {
    window.location.replace('login.html');
  } else {
    state.user = user;
    // *This is to handle the case of login, user's display name is in user.state.dataFromServer
    if (!state.user.name) {
      state.user.name = state.user.dataFromServer.name;
    }

    navView.updateGreeting(state.user.name);
  }
};

// *Event Listener for sign out button
elements.signOutBtn.addEventListener('click', e => {
  e.preventDefault();
  localStorage.setItem('user', 'null');
  window.location.replace('login.html');
});

setTimeout(clearSpinner(), 10000);
