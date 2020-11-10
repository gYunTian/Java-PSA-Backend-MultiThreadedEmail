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
    const btnlist = Array.from(document.querySelectorAll('.btn-date'));
    btnlist.forEach(btn => {
        if (btn.classList.contains('btn-selected')) {
            btn.classList.remove('btn-selected');
        }
    });
    document.getElementById(date).classList.add('btn-selected');
};

export const renderHeading = type => {
    const heading = type.charAt(0).toUpperCase() + type.slice(1);
    elements.dateSelectionContainer.innerHTML = `<div class="special-heading">${heading}</div>`;
};
