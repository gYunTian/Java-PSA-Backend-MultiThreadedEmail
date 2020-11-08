export const shrinkLabels = () => {
    const inputArr = Array.from(document.querySelectorAll('.form-input'));
    inputArr.forEach(el => {
        el.addEventListener('change', () => {
            console.log(el.value);
            if (el.value.length > 0) {
                el.nextElementSibling.classList.add('shrink');
            } else {
                el.nextElementSibling.classList.remove('shrink');
            }
        });
    });
};
