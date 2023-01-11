const selection = document.getElementById("type");
const fecha = document.getElementById("fecha_vencimiento")
const id_member = document.getElementById("id_member")
function seleccion(){
    if (selection.value === "Socio"){
        id_member.readOnly = false
        id_member.disabled = false
        fecha.value = ""
        fecha.readOnly = true
        fecha.disabled = true
    }
    else{
        id_member.readOnly = true
        id_member.disabled = true
        id_member.value = selection.value
        fecha.readOnly = false
        fecha.disabled = false
    }
}
seleccion()
selection.addEventListener('change', seleccion)
