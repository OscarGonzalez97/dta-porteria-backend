const selection = document.getElementById("type");
const fecha = document.getElementById("fecha_vencimiento")
function seleccion(){
    if (selection.value === "Socio"){
        fecha.value = ""
        fecha.readOnly = true
    }
    else{
        fecha.readOnly = false
    }
}
seleccion()
selection.addEventListener('change', seleccion)