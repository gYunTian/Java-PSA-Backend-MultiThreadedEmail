import { elements, compare } from '../views/base';

export const clearTable = () => {
    elements.dataTableBody.innerHTML = '';
};

export const renderByDate = (data, sortReq, favsArr, subsArr) => {
    elements.dataTableBody.innerHTML = '';
    const { by, order } = sortReq;
    data.sort(compare(by, order));

    data.forEach(e => {
        let {
            vesselName,
            inVoyN,
            outVoyN,
            berthingTime,
            degreeOfChange,
            changeCount,
            departureTime,
            berthN,
            status,
            uniqueID,
        } = e;
        berthN = !berthN ? '-' : berthN;
        const isFav = favsArr.includes(uniqueID);
        const isSub = subsArr.includes(uniqueID);
        const markup = `
        <tr>
            <td>${vesselName}</td>
            <td>${inVoyN}</td>
            <td>${outVoyN}</td>
            <td class=${
                degreeOfChange == 0
                    ? ''
                    : degreeOfChange >= 1
                    ? 'big-change'
                    : 'small-change'
            }>${berthingTime}</td>
            <td>${changeCount}</td>
            <td>${departureTime}</td>
            <td>${berthN}</td>
            <td>${status}</td>
            <td>
                <div class=${
                    isFav ? 'btn-favourited' : 'btn-favourite'
                } uniqueID="${uniqueID}">${
            isFav ? 'Unfavourite' : 'Favourite'
        }</div>
            </td>
            <td>
                <div class=${
                    isSub ? 'btn-subscribed' : 'btn-subscribe'
                } uniqueID="${uniqueID}">${
            isSub ? 'Unsubscribe' : 'Subscribe'
        }</div>
            </td>
        </tr>
        `;
        elements.dataTableBody.insertAdjacentHTML('beforeend', markup);
    });
};

export const renderFavOrSub = (data, sortReq, favsArr, subsArr, type) => {
    elements.dataTableBody.innerHTML = '';

    const filteredData = [];

    for (const [date, arr] of Object.entries(data)) {
        arr.forEach(item => {
            const { uniqueID } = item;
            if (type == 'favourites') {
                if (favsArr.includes(uniqueID)) {
                    filteredData.push({
                        ...item,
                        berthingTime: `${date}, ${item.berthingTime}`,
                        departureTime: `${date}, ${item.departureTime}`,
                    });
                }
            } else if (type == 'subscriptions') {
                if (subsArr.includes(uniqueID)) {
                    filteredData.push({
                        ...item,
                        berthingTime: `${date}, ${item.berthingTime}`,
                    });
                }
            }
        });
    }

    const { by, order } = sortReq;
    filteredData.sort(compare(by, order));

    if (filteredData.length) {
        filteredData.forEach(e => {
            let {
                vesselName,
                inVoyN,
                outVoyN,
                berthingTime,
                degreeOfChange,
                changeCount,
                departureTime,
                berthN,
                status,
                uniqueID,
            } = e;
            berthN = !berthN ? '-' : berthN;
            const isFav = favsArr.includes(uniqueID);
            const isSub = subsArr.includes(uniqueID);
            const markup = `
            <tr>
                <td>${vesselName}</td>
                <td>${inVoyN}</td>
                <td>${outVoyN}</td>
                <td class=${
                    degreeOfChange == 0
                        ? ''
                        : degreeOfChange >= 1
                        ? 'big-change'
                        : 'small-change'
                }>${berthingTime}</td>
                <td>${changeCount}</td>
                <td>${departureTime}</td>
                <td>${berthN}</td>
                <td>${status}</td>
                <td>
                    <div class=${
                        isFav ? 'btn-favourited' : 'btn-favourite'
                    } uniqueID="${uniqueID}">${
                isFav ? 'Unfavourite' : 'Favourite'
            }</div>
                </td>
                <td>
                    <div class=${
                        isSub ? 'btn-subscribed' : 'btn-subscribe'
                    } uniqueID="${uniqueID}">${
                isSub ? 'Unsubscribe' : 'Subscribe'
            }</div>
                </td>
            </tr>
            `;
            elements.dataTableBody.insertAdjacentHTML('beforeend', markup);
        });
    } else {
        if (type == 'favourites') {
            const markup = `
            <tr>
                <td colspan='10'>No Favourites Found</td>
            </tr>
            `;
            elements.dataTableBody.insertAdjacentHTML('beforeend', markup);
        } else if (type == 'subscriptions') {
            const markup = `
            <tr>
                <td colspan='10'>No Subscriptions Found</td>
            </tr>
            `;
            elements.dataTableBody.insertAdjacentHTML('beforeend', markup);
        }
    }
};

// *Favourite/Subscription button toggle
export const FSBtnToggle = btn => {
    if (btn.classList.contains('btn-favourite')) {
        btn.classList.remove('btn-favourite');
        btn.classList.add('btn-favourited');
        btn.innerHTML = 'Unfavourite';
    } else if (btn.classList.contains('btn-favourited')) {
        btn.classList.remove('btn-favourited');
        btn.classList.add('btn-favourite');
        btn.innerHTML = 'Favourite';
    } else if (btn.classList.contains('btn-subscribe')) {
        btn.classList.remove('btn-subscribe');
        btn.classList.add('btn-subscribed');
        btn.innerHTML = 'Unsubscribe';
    } else if (btn.classList.contains('btn-subscribed')) {
        btn.classList.remove('btn-subscribed');
        btn.classList.add('btn-subscribe');
        btn.innerHTML = 'Subscribe';
    }
};

// // ?Probably not needed since sort works fine
// export const resetSort = excludeName => {
//     const sortBtnList = Array.from(document.querySelectorAll('.btn-sort'));
//     sortBtnList.forEach(btn => {
//         const namingArr = btn.innerHTML.split(' ');
//         const naming = namingArr.slice(0, namingArr.length - 1).join(' ');
//         if (naming != excludeName) {
//             btn.innerHTML = `${naming} &#9650`;
//         }
//     });
// };
