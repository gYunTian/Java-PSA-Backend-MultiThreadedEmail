import * as formsView from './views/formsView';
import * as signUpView from './views/signUpView';
import * as signInView from './views/signInView';
import { elements } from './views/base';

const state = {};

// *Perform tasks after page loads
window.addEventListener('load', () => {
  controlForms();
})

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
    // ! Temporary redirect to index.html & keep session using LS
    localStorage.setItem("logged-in", "1");
    window.location.replace('index.html');
  }
}
elements.signInBtn.addEventListener('click', e => {
  e.preventDefault();
  controlLogin();
})

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
    // ! Temporary redirect to index.html & keep session using LS
    localStorage.setItem("logged-in", "1");
    window.location.replace('index.html');
  }
}
elements.signUpBtn.addEventListener('click', e => {
  e.preventDefault();
  controlSignUp();
});


