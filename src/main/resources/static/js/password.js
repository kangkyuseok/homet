

  function checkId(){
      var id = $('#id').val();
  $.ajax({
      url:'/user/idCheck',
      type:'post',
      data:{id:id},
      success:function(cnt){ //컨트롤러에서 넘어온 cnt값을 받는다 
          if(cnt != 1){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디 
              $('.id_ok').css("display","block"); 
              $('.id_ok').css("color","red"); 
              $('.id_already').css("display", "none");
          } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
              $('.id_already').css("display","block");
              $('.id_already').css("color","blue");
              $('.id_ok').css("display", "none");
          }
      },
      error:function(){
          alert("에러입니다");
      }
  });
  };


$(function(){ 
$("#alert-success").hide(); 
$("#alert-danger").hide();
$("input").keyup(function(){ 
	var pwd1=$("#password").val(); 
	var pwd2=$("#password2").val(); 
	if(pwd1 != "" || pwd2 != ""){ 
		if(pwd1 == pwd2){ 
		$("#alert-success").show(); 
		$("#alert-danger").hide();
		$("#submit").removeAttr("disabled"); 
		}else{ 
		$("#alert-success").hide(); 
		$("#alert-danger").show(); 
		$("#submit").attr("disabled", "disabled"); 
		} } }); });

 $(function(){
	 $("#password_ck2").hide();
	$("input").keyup(function(){
		
		var password_ck =$("#password").val();
		var email_ck =$("#id").val();
		
		if(password_ck !=""){
			$("#password_ck").hide();
			if(password_ck.length <6 ){
				$("#password_ck2").show();
				$("#submit").attr("disabled", "disabled"); }
			else{
				$("#password_ck2").hide();
			}
		}else {
			$("#password_ck").show();
			
			$("#password_ck2").hide();
			$("#submit").attr("disabled", "disabled"); 
		}
		
		if(email_ck !=""){
			$("#email_ck").hide();
		}else {
			$("#email_ck").show();
			$(".id_ok").hide();
		}
	}); 
});
 $(function(){
		sendMail();
		
	});
function sendMail(){
	var isCertification = false;
	var key1 = "";
 $('#sendMail').on('click',function() {// 메일 입력 유효성 검사
		var email = $(".email").val(); //사용자의 이메일 입력값. 
	 console.log(email);
		
		if (email == "") {
			alert("메일 주소가 입력되지 않았습니다.");
		} else {
			
			$.ajax({
				type : 'post',
				url : '/user/CheckMail',
				data : {
					email:email
					},
					success : function(key) {
						console.log(key);
						key1 = key;
						console.log(key1);
					}
				});
			console.log(key1);
				alert("이메일을 확인하시기 바랍니다.");
				$(".compare").css("display", "block");
				$(".compare-text").css("display", "block");
		}
	});	
 $(".compare").on("propertychange change keyup paste input", function() {
	 console.log(key1);
		if ($(".compare").val() == key1) {   //인증 키 값을 비교를 위해 텍스트인풋과 벨류를 비교
			$(".compare-text").text("인증 성공!").css("color", "blue");
			$("#password_change").show();
			$("#password_change2").show();
			isCertification = true;  //인증 성공여부 check
		} else {
			$(".compare-text").text("불일치!").css("color", "red");
			isCertification = false; //인증 실패
		}
	});
 }

 