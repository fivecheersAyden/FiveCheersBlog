export const timeCorrect = (wrongTime)=>{
    {
        const utcDate = new Date(wrongTime);
        const options = {
            timeZone: 'Asia/Shanghai',
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
        };
        const formattedDate = new Intl.DateTimeFormat('zh-CN', options)
            .formatToParts(utcDate)
            .map(({ type, value }) => (type === 'literal' && value === '-' ? ' ' : value))
            .join('');
        return formattedDate;
    }
}

export const checkAmIDead = (getTime)=>{
    const targetDate = new Date(getTime);
    const currentDate = new Date();
    const timeDifference = currentDate.getTime() - targetDate.getTime();
    const daysDifference = timeDifference / (1000 * 3600 * 24);
    if (daysDifference <= 30) {
        return false;
    } else {
        return true;
    }
}

export const checkManager = ()=>{
    return localStorage.getItem('managerSecret') && localStorage.getItem('managerSecret') === 'nzt100years'
}

// utils/debounce.js
export function debounce(func, wait) {
    let timeout;
    return function(...args) {
        const context = this;
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(context, args), wait);
    };
}

