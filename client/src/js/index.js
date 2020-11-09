import { elements, clearSpinner } from './views/base';

import { data, processData } from './data/data';

import * as navView from './views/navView';
import * as selectionView from './views/selectionView';
import * as tableView from './views/tableView';
import Vessel from './models/Vessel';

const state = {};
console.log({ state });

// *Perform tasks after page loads
window.addEventListener('load', async () => {
    controlTime();
    controlLogin();
    // *Get all data into state and format it
    await controlVessels();
    controlTable();
});

// *Control date
const controlTime = () => {
    const days = [
        'Sunday',
        'Monday',
        'Tuesday',
        'Wednesday',
        'Thursday',
        'Friday',
        'Saturday',
    ];
    let today = new Date();
    state.time = {};
    let range = [today];
    for (let i = 1; i <= 6; i++) {
        const nextDay = new Date(today);
        nextDay.setDate(today.getDate() + 1);
        range.push(nextDay);
        today = nextDay;
    }
    state.time.dateRange = [];
    range.forEach(e => {
        const year = e.getFullYear().toString(10);
        const month = ('0' + (e.getMonth() + 1).toString(10)).slice(-2);
        const date = ('0' + e.getDate().toString(10)).slice(-2);
        const day = days[e.getDay()];
        state.time.dateRange.push([`${year}-${month}-${date}`, day]);
    });
};

// *Control login
const controlLogin = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.replace('login.html');
    } else {
        clearSpinner();
        state.user = user;
        // *Update greeting
        navView.updateGreeting(state.user.name);
    }
};

// *Event Listener for sign out button
elements.signOutBtn.addEventListener('click', e => {
    e.preventDefault();
    localStorage.setItem('user', 'null');
    window.location.replace('login.html');
});

// *Control vessels (Get data into state and format it)
const controlVessels = async () => {
    console.log('refetching..');
    // *Get date range from state
    const startDate = state.time.dateRange[0][0];
    const endDate = state.time.dateRange[6][0];
    state.vessel = new Vessel(startDate, endDate);
    selectionView.renderDateSelection(state.time.dateRange);
    try {
        await state.vessel.getNext7Days();
    } catch (err) {
        console.log(`Error getting next 7 days data: ${err}`);
    }
};

// *Control table
const controlTable = (sortReq = { by: 'berthingTime', order: 'asc' }) => {
    const hash = window.location.hash.replace('#', '');
    if (!hash) {
        // *Highlight today in selection
        selectionView.highlightSelectedDate(state.time.dateRange[0][0]);
        // *Load data for today
        tableView.renderByDate(
            state.vessel.niceData[state.time.dateRange[0][0]],
            sortReq
        );
    } else {
        if (hash == 'favorites') {
            console.log('load favorites');
        } else if (hash == 'subscriptions') {
            console.log('load subscriptions');
        } else {
            selectionView.highlightSelectedDate(hash);
            tableView.renderByDate(state.vessel.niceData[hash], sortReq);
        }
    }
};

// *Event Listener for hash change (to trigger different tables)
window.addEventListener('hashchange', e => {
    controlTable();
});

// *Event Listener for sorting
elements.dataTableHead.addEventListener('click', e => {
    const sortBtnClicked = e.target.closest('.btn-sort');
    if (sortBtnClicked) {
        console.log(sortBtnClicked);
        const by = sortBtnClicked.getAttribute('sortby');
        const order = sortBtnClicked.getAttribute('order');
        const namingArr = sortBtnClicked.innerHTML.split(' ');
        const naming = namingArr.slice(0, namingArr.length - 1).join(' ');
        const sortReq = { by, order };
        controlTable(sortReq);
        sortBtnClicked.setAttribute('order', order == 'asc' ? 'desc' : 'asc');
        // tableView.resetSort(naming);
        sortBtnClicked.innerHTML = `${naming} ${
            order == 'desc' ? '&#9660' : '&#9650'
        }`;
    }
});


// *Event Listener for add fav
