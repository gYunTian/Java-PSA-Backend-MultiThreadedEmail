import { elements } from './base';

export const getResetEmail = () => elements.resetEmailInput.value;

export const clearResetEmail = () => {
  elements.resetEmailInput.value = '';
};

export const getResetEmail2 = () => elements.resetEmailInput2.value;
export const getToken = () => elements.resetTokenInput.value;
export const getNewPw = () => elements.newPwInput.value;

export const addSpinner = () => {
  elements.modalBox.innerHTML = `
    <h2 class="reset-title">Sending email, please wait...</h2>
    <div class="spinner"></div>
  `;
};

export const removeSpinnerAddMsg = msg => {
  elements.modalBox.innerHTML = `
  <h2 class="response-msg">${msg}</h2>
  <span class="modal-close" id="modal-close-btn">X</span>
  `;

  document.querySelector('.modal-close').addEventListener('click', e => {
    elements.modalBg.classList.remove('bg-active');

    location.reload();
  });
};

export const addEmailTokenValues = (email, token) => {
  elements.resetEmailInput2.value = email;
  elements.resetTokenInput.value = token;
};

export const addSpinner2 = () => {
  elements.modalBox2.innerHTML = `
    <h2 class="load-title">Changing Password, please wait...</h2>
    <div class="spinner-2"></div>
  `;
};

export const removeSpinnerAddMsg2 = msg => {
  // elements.modalBox2.classList.remove('modal-2');
  elements.modalBox2.classList.add('modal-2-sm');
  elements.modalBox2.innerHTML = `
  <h2 class="response-msg-2">${msg}</h2>
  <span class="modal-close-2" id="modal-close-btn-2">X</span>
  `;

  document.querySelector('.modal-close-2').addEventListener('click', e => {
    elements.modalBg2.classList.remove('bg-active-2');
  });
};
