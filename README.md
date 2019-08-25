- [x] MarkDownエディタが欲しい - Typoraのインストール

- [x] Webサービスを作成する - Jessyで。slack認証を行うpathを決めておく
  OpenJDK入手: openjdk-12.0.2_windows-x64_bin.zip
  Maven入手: apache-maven-3.6.1-bin.zip
jax-rs(jersey)プロジェクトの作成...
  
  ```dosbatch
  C:\Users\teruk\Documents>call %MVN_HOME%\bin\mvn archetype:generate ^
  More? -DarchetypeArtifactId=jersey-heroku-webapp ^
  More? -DarchetypeGroupId=org.glassfish.jersey.archetypes ^
  More? -DinteractiveMode=false ^
  More? -DgroupId=jp.example ^
  More? -DartifactId=pbstudy-slack-webservice ^
  More? -Dpackage=jp.example ^
  More? -DarchetypeVersion=2.29
  ```
  
  https://github.com/atsuteru/pbstudy-slack-webservice
  
- [x] Webサービスを公開する - heroku
  https://pbstudy-slack-webservice.herokuapp.com/oauth/redirect?code=1234&state=abc

- [x] Slackアプリを作成する - OAuthのredirect先にherokuでのslack認証のpathを指定
  Create App - https://api.slack.com/apps

- [x] Webサービス開発 - redirectを受けたらアクセストークンをメモリ保存＆表示するコードを書く

- [x] Slack認証のURLをブラウザで投げる→Webサービスがアクセストークンをメモリ保存＆表示する

- [x] Webサービス開発 - アクセストークン受信時、Slackにメッセージを投げる

- [x] SlackのEventyAPIを使ってみる

