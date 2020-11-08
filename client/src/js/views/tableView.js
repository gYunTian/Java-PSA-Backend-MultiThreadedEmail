import { elements } from '../views/base';

export const renderByDate = data => {
    elements.dataTableBody.innerHTML = '';

    data.forEach(e => {
        const {
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
                <div class="btn-favorite" uniqueID="${uniqueID}">Favorite</div>
            </td>
            <td>
                <div class="btn-subscribe" uniqueID="${uniqueID}">Subscribe</div>
            </td>
        </tr>
        `;
        elements.dataTableBody.insertAdjacentHTML('beforeend', markup);
    });
};
