# join_login_server
회원가입 + 로그인 간단한 프론트와 flask 서버 연동을 위한 코드

# android studio와 flask 통신을 위한 변수명 규칙
** android studio와 flask 서버에서 json을 만들고 가져오는 코드의 변수명은 반드시 통일해주어야 한다.
ex)     회원가입 아이디 값을 android studio 코드에서 flask 코드로 가져오는 예시
        android studio 코드 :  jsonInput.put("userID",  input_id.getText().toString()); // id가 input_id인 텍스트 박스의 값을 가져와서 userID json 값을 만든다. 
        flask 코드 :         user_id = request.json['userID'] // (android studio에서 userID json 값을 가져온다.)

[회원가입 페이지]
회원가입 아이디 : userID
회원가입 비밀번호 : userPassword
회원가입 이름 : userName

[로그인 페이지]
로그인 아이디 : login_id
로그인 비밀번호 : login_pw
