//Foolproof
function checker(){
    $.get('api/user/getbyname/'+$('#username').val(),function(data,status){
       $xml = $(data);
       $user = $xml.find('username').text();
       $title = $xml.find('title').text();
       console.log($title);
       if($user == $('#username').val()){
           document.cookie = "uname="+$user+";path=/;";
       }
    });
     return true;
}
