import { elements, clearSpinner } from './views/base';

import { data, processData } from './data/data';

import * as navView from './views/navView';
import * as selectionView from './views/selectionView';
import * as tableView from './views/tableView';
import Vessel from './models/Vessel';
import Favourite from './models/Favourite';
import Subscription from './models/Subscription';

const state = {};
console.log({ state });

// *Perform tasks after page loads
window.addEventListener('load', async () => {
    controlTime();
    selectionView.renderDateSelection(state.time.dateRange.slice(0, -1));
    controlLogin();
    // *Get all data into state and format it
    await controlVessel();
    await controlFavs();
    await controlSubs();
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
    for (let i = 1; i <= 7; i++) {
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

// *Control vessel (Get data into state and format it)
const controlVessel = async () => {
    console.log('Refetching vessel data...');
    // *Get date range from state
    const startDate = state.time.dateRange[0][0];
    const endDate = state.time.dateRange[7][0];
    state.vessel = new Vessel(startDate, endDate);
    try {
        await state.vessel.getNext7Days();
    } catch (err) {
        console.log(`Error at controlVessel getNext7Days(): ${err}`);
    }
};

// *Control table (determines what table is displayed & sorting order)
const controlTable = async (sortReq = { by: 'berthingTime', order: 'asc' }) => {
    const hash = window.location.hash.replace('#', '');
    if (!hash) {
        // *Highlight today in selection
        selectionView.highlightSelectedDate(state.time.dateRange[0][0]);
        // *Render today's data
        tableView.renderByDate(
            state.vessel.niceData[state.time.dateRange[0][0]],
            sortReq,
            state.favourite.favsArr,
            state.subscription.subsArr
        );
    } else {
        if (hash == 'favourites') {
            // *Render favourites
            console.log('load favourites');
        } else if (hash == 'subscriptions') {
            // *Render subscriptions
            console.log('load subscriptions');
        } else {
            selectionView.highlightSelectedDate(hash);
            controlTime();
            await controlVessel();
            await controlFavs();
            await controlSubs();
            // *Render by date
            tableView.renderByDate(
                state.vessel.niceData[hash],
                sortReq,
                state.favourite.favsArr,
                state.subscription.subsArr
            );
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

// *Control favourites
const controlFavs = async (uniqueID = null, btn = null) => {
    if (!state.favourite) {
        state.favourite = new Favourite(state.user.userID, uniqueID);
    } else {
        state.favourite.setVoyageID(uniqueID);
    }
    if (uniqueID && btn) {
        if (btn.classList.contains('btn-favourite')) {
            try {
                tableView.FSBtnToggle(btn);
                await state.favourite.addFav();
            } catch (err) {
                console.log(`Error at controlFavs addFav: ${err}`);
            }
        } else if (btn.classList.contains('btn-favourited')) {
            //* Remove favourite
            try {
                tableView.FSBtnToggle(btn);
                await state.favourite.deleteFav();
            } catch (err) {
                console.log(`Error at controlFavs deleteFav: ${err}`);
            }
        }
    } else {
        // *Get favs (done after awaiting get all vessels)
        try {
            await state.favourite.getFavs();
        } catch (err) {
            console.log(`Error at controlFavs getFavs: ${err}`);
        }
    }
};

// *Control Subscriptions
const controlSubs = async (uniqueID = null, btn = null) => {
    if (!state.subscription) {
        state.subscription = new Subscription(state.user.userID, uniqueID);
    } else {
        state.subscription.setVoyageID(uniqueID);
    }
    if (uniqueID && btn) {
        if (btn.classList.contains('btn-subscribe')) {
            try {
                tableView.FSBtnToggle(btn);
                await state.subscription.addSub();
            } catch (err) {
                console.log(`Error at controlSubs addSub: ${err}`);
            }
        } else if (btn.classList.contains('btn-subscribed')) {
            //* Remove sub
            try {
                tableView.FSBtnToggle(btn);
                await state.subscription.deleteSub();
            } catch (err) {
                console.log(`Error at controlSubs deleteSub: ${err}`);
            }
        }
    } else {
        // *Get subs (done after awaiting get all vessels)
        try {
            await state.subscription.getSubs();
        } catch (err) {
            console.log(`Error at controlSubs getSubs: ${err}`);
        }
    }
};

// *Event Listener for fav/sub
elements.dataTableBody.addEventListener('click', e => {
    const favBtnClicked = e.target.closest('.btn-favourite');
    const unFavBtnClicked = e.target.closest('.btn-favourited');
    const subBtnClicked = e.target.closest('.btn-subscribe');
    const unSubBtnClicked = e.target.closest('.btn-subscribed');
    if (favBtnClicked) {
        const uniqueID = favBtnClicked.getAttribute('uniqueID');
        controlFavs(uniqueID, favBtnClicked);
    }

    if (unFavBtnClicked) {
        const uniqueID = unFavBtnClicked.getAttribute('uniqueID');
        controlFavs(uniqueID, unFavBtnClicked);
    }

    if (subBtnClicked) {
        const uniqueID = subBtnClicked.getAttribute('uniqueID');
        controlSubs(uniqueID, subBtnClicked);
    }

    if (unSubBtnClicked) {
        const uniqueID = unSubBtnClicked.getAttribute('uniqueID');
        controlSubs(uniqueID, unSubBtnClicked);
    }
});
