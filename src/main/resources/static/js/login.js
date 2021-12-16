Kakao.init('e579019570113b02bbb34d1f9519df83'); //발급받은 키 중 javascript키를 사용해준다.
console.log(Kakao.isInitialized()); // sdk초기화여부판단
//카카오로그인
function kakaoLogin() {
    Kakao.Auth.login({
    	scope:'profile_nickname, account_email, gender, age_range',
      success: function (response) {
    	  console.log(response)
        Kakao.API.request({
          url: '/v2/user/me',
          success: res =>{
				
				
				var userID = res.id;      //유저의 카카오톡 고유 id
			       var email = res.kakao_account.email;   //유저의 이메일
			       var nickname = res.properties.nickname; //유저가 등록한 별명
			       var name = res.properties.nickname; //유저가 등록한 별명
			       var gender = res.kakao_account.gender; //유저가 등록한 
			       
			       console.log(userID);
			       console.log(email);
			       console.log(nickname);
			       console.log(gender);
			       $.ajax({
			    		url : '/klogin',
			    		type : 'post',
			    		data : {
			    			name : name,
			    			email : email,
			    			gender : gender
			    		},
			    		success : function(data) {
			    			 location.href="../home";
			    	     },
			    		error : function() {
			    			alert("error");
			    		}
			    	});
	
				
          },
          fail: function (error) {
            console.log(error)
          },
        })
      },
      fail: function (error) {
        console.log(error)
      },
    })
  }
//카카오로그아웃  
function kakaoLogout() {
    if (Kakao.Auth.getAccessToken()) {
      Kakao.API.request({
        url: '/v1/user/unlink',
        success: function (response) {
        	console.log(response)
        },
        fail: function (error) {
          console.log(error)
        },
      })
      Kakao.Auth.setAccessToken(undefined)
    }
  }  