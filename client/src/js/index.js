import { elements, clearSpinner } from './views/base';

const state = {};
console.log({ state });

// *Perform tasks after page loads
window.addEventListener('load', () => {
  controlLogin();
});

const controlLogin = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  if (!user) {
    window.location.replace('login.html');
  } else {
    state.user = user;
    console.log(state.user);
  }
};

// !Temporary handle sign out
elements.signOutBtn.addEventListener('click', e => {
  e.preventDefault();
  localStorage.setItem('user', 'null');
  window.location.replace('login.html');
});

setTimeout(clearSpinner(), 10000);
