import { elements, clearSpinner } from './views/base';

const state = {};

const loggedIn = localStorage.getItem('logged-in');

if (loggedIn == 0 || !loggedIn) {
  console.log('redirecting...');
  window.location.replace('login.html');
}

// !Temporary handle sign out
elements.signOutBtn.addEventListener('click', e => {
  e.preventDefault();
  localStorage.setItem('logged-in', '0');
  window.location.replace('login.html');
});

setTimeout(clearSpinner(), 3000);


