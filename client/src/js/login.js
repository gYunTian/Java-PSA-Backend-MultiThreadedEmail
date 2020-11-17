import * as formsView from './views/formsView';
import * as signUpView from './views/signUpView';
import * as signInView from './views/signInView';
import * as resetView from './views/resetView';
import * as loginView from './views/loginView';
import { elements } from './views/base';

import User from './models/User';

const state = {};
console.log({ state });

// *Perform tasks after page loads
window.addEventListener('load', () => {
  controlForms();
  // *Check if this is a change password redirect
  if (window.location.search.substring(1, 6) == 'email') {
    controlNewPwFields(window.location.search.substring(1));
  }
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
      await state.user.loginUser();
      if (state.user.loginStatus == 200) {
        localStorage.setItem('user', JSON.stringify(state.user));
        window.location.replace('index.html');
      } else {
        signInView.clearInputs();
        elements.signInEmailInput.focus();
        alert(state.user.response);
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
      } else {
        alert(state.user.response);
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

// *Control reset
const controlToken = async () => {
  const email = prompt('Please enter your login email:');
  if (email) {
    state.user = new User();
    resetView.addLoadBlock();
    try {
      await state.user.requestToken(email);
    } catch (err) {
      console.log(`Error at controlToken requestToken(): ${err}`);
    }
    resetView.removeLoadBlock();
  } else {
    alert('Email cannot be empty');
  }
};

// *Control reset
const controlReset = async () => {
  const token = prompt('Please enter your token:');
  const newPw = prompt('Please enter your new password:');
  const confirmNewPw = prompt('please confirm your new passowrd');
  if (!token || !newPw || !confirmNewPw) {
    alert('Fields cannot be empty.');
  } else if (newPw != confirmNewPw) {
    alert("New passwords don't match.");
  } else {
    state.user = new User();
    resetView.addLoadBlock();
    try {
      await state.user.resetPassword(token, newPw);
    } catch (err) {
      console.log(`Error at controlReset resetPassword(): ${err}`);
    }
    resetView.removeLoadBlock();
  }
};

// *controlRequestResetEmail
const controlRequestResetEmail = async () => {
  const resetEmail = loginView.getResetEmail();
  state.user = new User();
  loginView.addSpinner();
  try {
    await state.user.requestToken(resetEmail);
    if (state.user.requestTokenStatus == 200) {
      loginView.removeSpinnerAddMsg(state.user.requestTokenResponse);
    } else {
      loginView.removeSpinnerAddMsg(state.user.requestTokenErrMsg);

      console.log(state.user.requestTokenErrMsg);
    }
  } catch (err) {
    console.log(`Error at controlRequestResetEmail: ${err}`);
  }
};

// *Event listener for reset password
elements.resetPwBtn.addEventListener('click', e => {
  e.preventDefault();
  elements.modalBg.classList.add('bg-active');
  elements.resetEmailInput.focus();
});

elements.requestResetBtn.addEventListener('click', e => {
  e.preventDefault();
  controlRequestResetEmail();
});

elements.modalCloseBtn.addEventListener('click', e => {
  elements.modalBg.classList.remove('bg-active');
  loginView.clearResetEmail();
});

elements.modalCloseBtn2.addEventListener('click', e => {
  elements.modalBg2.classList.remove('bg-active-2');
});

// *controlNewPwFields
const controlNewPwFields = urlString => {
  const email = urlString.split('&')[0].split('=')[1];
  console.log(email);
  const token = urlString.split('&')[1].split('=')[1];
  console.log(token);

  elements.modalBg2.classList.add('bg-active-2');

  loginView.addEmailTokenValues(email, token);

  elements.newPwInput.focus();
};

const controlChangePw = async () => {
  const email = loginView.getResetEmail2();
  const token = loginView.getToken();
  const newPw = loginView.getNewPw();
  if (email && token && newPw) {
    state.user = new User();
    loginView.addSpinner2();
    try {
      await state.user.resetPassword(email, token, newPw);
      if (state.user.changePwStatus == 200) {
        loginView.removeSpinnerAddMsg2(state.user.changePwResponse);
      } else {
        loginView.removeSpinnerAddMsg2(state.user.changePwErrMsg);

        console.log(state.user.requestTokenErrMsg);
      }
    } catch (err) {
      loginView.removeSpinnerAddMsg2(
        'Error Changing Password, please try again'
      );
      console.log(`Error at controlChangePw: ${err}`);
    }
  } else {
    alert('Field(s) cannot be empty, please check.');
  }
};

elements.confirmNewPwBtn.addEventListener('click', e => {
  e.preventDefault();
  controlChangePw();
});
