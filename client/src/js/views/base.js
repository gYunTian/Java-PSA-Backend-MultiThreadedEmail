export const elements = {
  signInEmailInput: document.querySelector('.sign-in-email-input'),
  signInPasswordInput: document.querySelector('.sign-in-password-input'),
  signInBtn: document.querySelector('#sign-in'),
  resetPwButton: document.querySelector('#reset-password'),
  signUpDisplayNameInput: document.querySelector('.sign-up-display-name-input'),
  signUpEmailInput: document.querySelector('.sign-up-email-input'),
  signUpPasswordInput: document.querySelector('.sign-up-password-input'),
  signUpPasswordCfmInput: document.querySelector('.sign-up-password-cfm-input'),
  signUpBtn: document.querySelector('#sign-up'),
  signOutBtn: document.querySelector('#sign-out'),
  spinner: document.querySelector('.spinner-wrapper')
};

// *Full screen spinner clear
export const clearSpinner = () => {
  // *Only clear if spinner exists
  if (elements.spinner && elements.spinner.parentElement) {
    elements.spinner.parentElement.removeChild(elements.spinner);
  }
};
