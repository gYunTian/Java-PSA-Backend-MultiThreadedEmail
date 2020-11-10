import * as formsView from './views/formsView';
import * as signUpView from './views/signUpView';
import * as signInView from './views/signInView';
import * as resetView from './views/resetView';
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
            await state.user.loginUser();
            if (state.user.loginStatus == 200) {
                localStorage.setItem('user', JSON.stringify(state.user));
                window.location.replace('index.html');
            } else {
                signInView.clearInputs();
                elements.signInEmailInput.focus();
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
            } else {
                // !Connect with backend handling better
                alert('Email domain not allowed.');
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

// *Event listener for reset password
elements.resetPwBtn.addEventListener('click', e => {
    e.preventDefault();
    const hasToken = confirm('Do you have a reset token?');
    if (hasToken) {
        controlReset();
    } else {
        controlToken();
    }
});
