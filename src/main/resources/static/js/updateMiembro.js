const type = document.getElementById("type")
const ci = document.getElementById("ci")
const id = document.getElementById("id")
if (type.value !== "Socio"){
    ci.disabled = true
    ci.readOnly = true
}
type.readOnly = true
id.readOnly = true
id.disabled = true
fecha.readOnly= true
fecha.value = ""
fecha.disabled = true






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