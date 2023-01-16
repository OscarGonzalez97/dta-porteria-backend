const inputs = document.querySelectorAll(".input");
function addcl(){
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}
function remcl(){
    let parent = this.parentNode.parentNode;
    if(this.value == ""){
        parent.classList.remove("focus");
    }
}
inputs.forEach(input => {
    input.addEventListener("focus", addcl);
    input.addEventListener("blur", remcl);
});
    const esconder = document.querySelector(".olvide-cont");
const btnolvide = document.querySelector(".forget");
const primer_form= document.querySelector(".form-primero");
const btnrecorde = document.querySelector(".recorde");
const recu= document.querySelector(".recu");
const msg_recu= document.querySelector(".msg-recu")
btnolvide.addEventListener("click",()=>{
    esconder.classList.remove("esconder");
    esconder.classList.add("mostrar");
    primer_form.classList.add("esconder");
})
btnrecorde.addEventListener("click",()=>{
    esconder.classList.remove("mostrar");
    esconder.classList.add("esconder");
    primer_form.classList.remove("esconder");
})

recu.addEventListener("click",()=>{
    btnolvide.classList.add("esconder");
    msg_recu.style.display=block
})