const url = new URL(window.location.href);

// window

window.addEventListener("scroll", ()=>{
  const header = document.getElementsByTagName("header")[0];
  if (window.pageYOffset > header.offsetTop) {
    header.classList.add("sticky");
  } else {
    header.classList.remove("sticky");
  }
})
