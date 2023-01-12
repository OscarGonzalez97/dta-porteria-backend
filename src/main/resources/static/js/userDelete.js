function deleteUser(id) {
    window.location.href = "/users/delete/" + id;
}

$('#exampleModal').on('show.bs.modal', (e) => {
    var id= $(e.relatedTarget).data('id');    
    $('#deleteButton').click(() => {
        deleteUser(id);
    });
}); 


