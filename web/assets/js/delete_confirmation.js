function sendForm(personId) {
    document.getElementById("delete" + personId).submit();
}

function confirmDelete(personName, personId) {
    if (confirm("Você tem certeza de que quer deletar a pessoa de nome \"" + personName + "\" e ID " + personId + "?")) {
        sendForm(personId);
    }
}