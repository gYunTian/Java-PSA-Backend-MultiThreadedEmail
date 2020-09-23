import * as formsView from './views/formsView';
import * as signUpView from './views/signUpView';
import * as signInView from './views/signInView';
import { elements } from './views/base';

import User from './models/User';

const state = {};
console.log({ state });

// *Perform tasks after page loads
window.addEventListener('load', () => {
  controlForms();
});

// *Control forms
const controlForms = () => {
  // *Labels stay shrinked if there is form input in that field
  formsView.shrinkLabels();
};

// *Control login
const controlLogin = async () => {
  const email = signInView.getEmail();
  const password = signInView.getPassword();
  if (signInView.formValidation({ email, password })) {
    state.user = new User(null, email, password);
    try {
      await state.user.getUserByEmail();
      if (state.user.password == state.user.dataFromDB.password) {
        localStorage.setItem('user', JSON.stringify(state.user));
        window.location.replace('index.html');
      } else {
        // *Wrong password
        signInView.clearPassword();
        elements.signInPasswordInput.focus();
        alert('Wrong email or password! Please try again!');
      }
    } catch (error) {
      console.log(`Error signing in user: ${error}`);
    }
  }
};
elements.signInBtn.addEventListener('click', e => {
  e.preventDefault();
  controlLogin();
});

// *Control signup
const controlSignUp = async () => {
  const displayName = signUpView.getDisplayName();
  const email = signUpView.getEmail();
  const password = signUpView.getPassword();
  const passwordCfm = signUpView.getPasswordCfm();
  if (
    signUpView.signUpFormValidation({
      displayName,
      email,
      password,
      passwordCfm,
    })
  ) {
    state.user = new User(displayName, email, password);
    try {
      await state.user.registerUser();
      // *If registration is successful
      if (state.user.registerStatus == 200) {
        localStorage.setItem('user', JSON.stringify(state.user));
        window.location.replace('index.html');
      }
    } catch (error) {
      console.log(`Error registering user: ${error}`);
    }
  }
};
elements.signUpBtn.addEventListener('click', e => {
  e.preventDefault();
  controlSignUp();
});
