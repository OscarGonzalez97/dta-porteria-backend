$(document).ready(() => {
    const fecha = document.getElementById("fecha_vencimiento")
    const tipo = document.getElementById("type")
    const id = document.getElementById("id_member")
    tipo.addEventListener('change', () => {
        if (tipo.value === "Socio") {
            fecha.required = false;
            fecha.value = "";
            fecha.disabled = true;
        }
        else {

            fecha.disabled = false;
        }
    })  

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')
        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
});
