import { elements } from './base';

export const addLoadBlock = () => {
    const markup = `
        <div id="loadingmsg">Processing your request, please wait...</div>
        <div id="blockDiv"></div>
    `;
    document
        .querySelector('#login-wrapper')
        .insertAdjacentHTML('beforebegin', markup);
};

export const removeLoadBlock = () => {
    document.getElementById('loadingmsg').remove();
    document.getElementById('blockDiv').remove();
};
