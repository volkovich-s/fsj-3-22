const header = document.getElementsByTagName("header")[0];
header.getChildById("sign_out").addEventListener("click",()=>{
  fetch("/logout");
});
