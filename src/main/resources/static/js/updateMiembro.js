$(document).ready(() => {
    const fecha = document.getElementById("fecha_vencimiento")
    const tipo = document.getElementById("type")
    const tipoOriginal = document.getElementById("type").value
    const id = document.getElementById("id_member")
    const valorId = id.value;
    function validateType(){
        if (tipo.value === "Socio" && tipoOriginal === "Socio") {
            id.value = valorId;
            id.readOnly = true;
            fecha.required = false;
            fecha.disabled = true;
            fecha.value = "";
            id.required = true;
            id.disabled = true;
        }
        if (tipo.value === "Socio" && tipoOriginal !== "Socio")
        {
            id.disabled = false;
            id.required = true;
            id.readOnly = false;
            id.value = "";
            fecha.required = false;
            fecha.value = "";
            fecha.disabled = true;
        }
        if (tipo.value !== "Socio"){
            id.readOnly = true;
            id.value = "";
            id.required = false;
            id.disabled = true;
            fecha.disabled = false;
            fecha.required = true;
        }
    }
    validateType()
    fecha.min =  new Date().toISOString().split("T")[0];
    tipo.addEventListener('change', function(){
        validateType();
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
