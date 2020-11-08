import { elements } from '../views/base';

// *Date range comes in [[date, day]...]
export const renderDateSelection = dateRange => {
    elements.dateSelectionContainer.innerHTML = '';
    dateRange.forEach(e => {
        const [date, day] = e;
        const markup = `
        <a id="${date}" class="btn-date" href="#${date}">
            ${date} ${day.toUpperCase()}
        </a>
        `;
        elements.dateSelectionContainer.insertAdjacentHTML('beforeend', markup);
    });
};

export const highlightSelectedDate = date => {
    console.log("done?");
    document.getElementById(date).classList.add('btn-selected');
};
