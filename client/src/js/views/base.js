export const elements = {
    signInEmailInput: document.querySelector('.sign-in-email-input'),
    signInPasswordInput: document.querySelector('.sign-in-password-input'),
    signInBtn: document.querySelector('#sign-in'),
    resetPwButton: document.querySelector('#reset-password'),
    signUpDisplayNameInput: document.querySelector(
        '.sign-up-display-name-input'
    ),
    signUpEmailInput: document.querySelector('.sign-up-email-input'),
    signUpPasswordInput: document.querySelector('.sign-up-password-input'),
    signUpPasswordCfmInput: document.querySelector(
        '.sign-up-password-cfm-input'
    ),
    signUpBtn: document.querySelector('#sign-up'),
    signOutBtn: document.querySelector('#sign-out'),
    spinner: document.querySelector('.spinner-wrapper'),
    greeting: document.querySelector('.greeting'),
    dateSelectionContainer: document.querySelector('.date-selection-container'),
    dataTableBody: document.querySelector('.data-table-body'),
};

const ENDPOINT = 'http://localhost:8080/api';

const CORS = 'https://cors-anywhere.herokuapp.com';

export const APIs = {
    addUser: `${ENDPOINT}/addUser`,
    getUserByEmail: `${ENDPOINT}/userByEmail`, // ?Not needed anymore
    loginUser: `${ENDPOINT}/loginUser`, // *Using this to authenticate users
    getVessels: `${ENDPOINT}/vessels`,
};

// *Full screen spinner clear
export const clearSpinner = () => {
    // *Only clear if spinner exists
    if (elements.spinner && elements.spinner.parentElement) {
        elements.spinner.parentElement.removeChild(elements.spinner);
    }
};

// *API Authentication
const USER = 'g1t9';
const PASSWORD = '999000';

export const headers = {
    authorization: 'Basic ' + window.btoa(USER + ':' + PASSWORD),
};

// *Data processor (turn data from API to usable stuff)
export const processData = data => {
    const mp = {};
    data.forEach(e => {
        const {
            first_arrival,
            abbrVslM,
            inVoyN,
            outVoyN,
            bthgDt,
            bthgDt_change_count,
            unbthgDt,
            berthN,
            status,
            uniqueId,
        } = e;
        const berthingDateTime = timeToString(new Date(bthgDt));
        const { date: berthingDate, time: berthingTime } = berthingDateTime;
        // *Find degree of change between first_arrival & bthgDt
        const berthTemp = new Date(bthgDt);
        const firstTemp = new Date(first_arrival);
        const degreeOfChange = Math.abs(firstTemp - berthTemp) / 36e5;
        const { date: departureDate, time: departureTime } = timeToString(
            new Date(unbthgDt)
        );
        const miniMap = {
            vesselName: abbrVslM,
            inVoyN,
            outVoyN,
            berthingTime,
            degreeOfChange,
            changeCount: bthgDt_change_count,
            departureTime,
            berthN,
            status,
            uniqueID: uniqueId,
        };
        if (mp[berthingDate]) {
            mp[berthingDate].push(miniMap);
        } else {
            mp[berthingDate] = [miniMap];
        }
    });
    return mp;
};

// *Convert ISO time to usable strings {date (YYYY-MM-DD), time(HH:MM)}
export const timeToString = time => {
    const toReturn = {};
    const year = time.getFullYear().toString(10);
    const month = ('0' + (time.getMonth() + 1).toString(10)).slice(-2);
    const date = ('0' + time.getDate().toString(10)).slice(-2);
    toReturn.date = `${year}-${month}-${date}`;
    toReturn.time = time.toLocaleTimeString('en', {
        timeStyle: 'short',
        hour12: false,
    });
    return toReturn;
};
