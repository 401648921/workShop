$('#button-submit').click(function(){
    let userName = $('#userName').val();
    let telNumber = $('#telNumber').val();
    let password = $('#password').val();
    let testUserName = /^[A-Za-z0-9]{6,12}$/;
    let testTelNumber = /^[0-9]{11}$/;
    let testPassword = /^(?=.*[0-9].*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#&*?@]).{9,15}/;
    if(testUserName.test(userName)&&testPassword.test(password)&&testTelNumber.test(telNumber)){
        alert("修改成功");
    }else{
        alert("格式错误");
    }
})