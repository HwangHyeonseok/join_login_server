// android studio(OKHTTP3) -> flask -> mysql DB
// android studio 에서 http을 요청해 MySQL에 데이터를 전송 : https://coreeny.tistory.com/77

package com.example.go.androidstudioconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String urls = "http://192.168.0.4:5000/userJoin"; // [★] Flask 서버 호출 URL
    private TextView input_id, input_pwd, input_nick; // 아이디, 비밀번호, 이름을 받아오기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        input_id = findViewById(R.id.input_id);
        input_pwd = findViewById(R.id.input_pwd);
        input_nick = findViewById(R.id.input_nick);
    }

    public void ClickButton1(View v){ // 버튼 클릭 리스너
        // 버튼 클릭 시 서버로 데이터 전송
        Log.d("ClickButton1", "Button clicked");  // Log 추가
        sendServer();
    }

    public void sendServer(){ // 서버로 데이터 전송하기 위한 함수
        class sendData extends AsyncTask<Void, Void, String> { // 백그라운드 쓰레드 생성

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // 백그라운드 작업 전에 실행되는 부분
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // 백그라운드 작업이 끝난 후에 실행되는 부분
                // LoginActivity로 이동
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                // 백그라운드 작업 중 갱신이 필요한 경우 실행되는 부분
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
                // 백그라운드 작업이 취소된 경우 실행되는 부분
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                // 백그라운드 작업이 취소된 경우 실행되는 부분
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient(); // OkHttp를 사용하도록 OkHttpClient 객체를 생성

                    JSONObject jsonInput = new JSONObject();  // JSON 객체 생성
                    jsonInput.put("userID",  input_id.getText().toString()); // [vscode-app.py] JSON 객체에 데이터 추가 (id)
                    jsonInput.put("userPassword",  input_pwd.getText().toString()); // [vscode-app.py] JSON 객체에 데이터 추가 (pw)
                    jsonInput.put("userName", input_nick.getText().toString());  // [vscode-app.py] JSON 객체에 데이터 추가 (nickname)

                    RequestBody reqBody = RequestBody.create(
                            jsonInput.toString(),
                            MediaType.parse("application/json; charset=utf-8")
                    );

                    Request request = new Request.Builder()
                            .post(reqBody)
                            .url(urls)
                            .build();

                    Response responses = client.newCall(request).execute(); // 요청을 실행 (동기 처리 : execute(), 비동기 처리 : enqueue())
                    System.out.println(responses.body().string());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        // AsyncTask 실행
        sendData sendData = new sendData();
        sendData.execute(); // 웹 서버에 데이터 전송
    }
}
