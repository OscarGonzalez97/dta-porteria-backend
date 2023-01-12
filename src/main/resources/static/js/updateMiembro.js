$(document).ready(() => {
    const type = document.getElementById("type")
    const ci = document.getElementById("ci")
    const id = document.getElementById("id_member")
    const fecha = document.getElementById("fecha_vencimiento")
    function seleccion(){
        type.disabled = true
        if (type.value === "Socio")
        {
            fecha.required = false
            fecha.disabled = true
            id.disabled = true
            ci.disabled = false
            ci.readOnly = false
            ci.required = true
        }
        else {
            ci.disabled = true
            id.disabled = true
        }
    }
    
    seleccion()
    type.addEventListener('change', seleccion)
    
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
