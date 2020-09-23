import { elements, clearSpinner } from './views/base';

const state = {};
console.log({state});

// *Perform tasks after page loads
window.addEventListener('load', () => {
  controlLogin();
});

const controlLogin = () => {
  const user =  JSON.parse(localStorage.getItem("user"));
  
  console.log(user);
  
}

// !Temporary handle sign out
elements.signOutBtn.addEventListener('click', e => {
  e.preventDefault();
  localStorage.setItem('user', 'null');
  window.location.replace('login.html');
});

setTimeout(clearSpinner(), 3000);


