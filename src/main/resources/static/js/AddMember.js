const selection = document.getElementById("type");
const fecha = document.getElementById("fecha_vencimiento")
selection.addEventListener('change', () => {
    if (selection.value === "Socio"){
        fecha.disabled = true
        fecha.value = null
    }
    else{
        fecha.disabled = false
    }
})