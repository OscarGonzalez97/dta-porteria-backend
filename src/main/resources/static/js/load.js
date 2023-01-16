$(window).on("load", () =>{
    $('.showbox').fadeOut("slow");
})
$(window).on("beforeunload", () =>{
    $('.showbox').fadeIn("slow");
})

