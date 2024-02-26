$(document).ready(() => {
    const type = document.getElementById("type")
    const id = document.getElementById("id_member")
    const fecha = document.getElementById("fecha_vencimiento")
    fecha.min =  new Date().toISOString().split("T")[0];
    function seleccion(){
        if (type.value !== "Socio"){
            id.value = ""
            id.required = false
            id.disabled = true
            fecha.readOnly = false
            fecha.disabled = false
            fecha.required = true
        }
        else{
            id.required = true
            id.disabled = false
            id.readOnly = false
            fecha.required = false
            fecha.readOnly= true
            fecha.disabled = true
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
    })})