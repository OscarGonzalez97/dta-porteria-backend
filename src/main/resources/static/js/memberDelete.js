function deleteMember(id) {
    window.location.href = "/members/delete/" + id;
}

$('#exampleModal').on('show.bs.modal', (e) => {
    var id= $(e.relatedTarget).data('id');    
    $('#deleteButton').click(() => {
        deleteMember(id);
    });
});
