function getMessage(type, message, action) {
    switch (type) {
        case -2:
            sudoNotify.warning(message);
            break;
        case -1:
            sudoNotify.error(message);
            break;
        case 0:
            sudoNotify.success(message);
            break;
        case 1:
            sudoNotify.success('Record ' + action + ' successfully');
            break;
    }
};