const type = document.getElementById("type")
const ci = document.getElementById("ci")
const id = document.getElementById("id")
function seleccion(){
    if (type.value !== "Socio"){
        ci.disabled = true
        ci.readOnly = true
        fecha.readOnly = false
    }
    else{
    id.readOnly = true
    fecha.readOnly= true
    fecha.value = ""
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